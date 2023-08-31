'use strict';

const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const messageArea = document.querySelector('#messageArea');
const connectingElement = document.querySelector('.connecting');

let stompClient = null;
let username = null;

const colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if (username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function onConnected(options) {
    // receive previous messages
    const subscription = stompClient.subscribe("/topic/previous-messages", function (message) {
        const previousMessages = JSON.parse(message.body);

        // first load all previous messages
        previousMessages.forEach(msg => {
            createMessage(msg);
        })

        // then announce your username to the server
        stompClient.send("/app/groupchat.addUser",
            {},
            JSON.stringify({ sender: username, type: 'JOIN' })
        )

        // unsubscribe because we only need to do it once per user. otherwise, new users joining in would
        // result in unwanted "previous messages"
        subscription.unsubscribe();
    });

    stompClient.subscribe('/topic/public', onMessageReceived);
    stompClient.send("/app/get-previous-messages", {}, {});

    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    createMessage(message);
}

function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT',
            timestamp: new Date()
        };

        stompClient.send("/app/groupchat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function createMessage(message) {
    let messageText;
    let textElement;
    const messageElement = document.createElement('li');

    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';

        textElement = document.createElement('p');
        messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';

        textElement = document.createElement('p');
        messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);
    } else {
        messageElement.classList.add('chat-message');

        // create avatar element
        const avatarElement = document.createElement('i');
        const avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        const contentElement = document.createElement('div');
        contentElement.classList.add('content');

        // create username element
        const usernameElement= document.createElement('span');
        let usernameString = String(message.sender);
        if(username === message.sender) usernameString += " (You)";
        const usernameText= document.createTextNode(usernameString);
        usernameElement.appendChild(usernameText);
        contentElement.appendChild(usernameElement);

        // format timestamp
        const timestamp = convertUTCDateToLocalDate(new Date(message.timestamp));
        const options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric' };
        const formattedTimestamp = timestamp.toLocaleDateString('en-US', options);

        // create timestamp element
        const dateElement = document.createElement('span');
        const dateText = document.createTextNode(formattedTimestamp);
        dateElement.appendChild(dateText);
        dateElement.classList.add('date-stamp'); // Apply the date-stamp class
        contentElement.appendChild(dateElement);

        // create message body element
        textElement = document.createElement('p');
        messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);
        contentElement.appendChild(textElement);

        // add everything
        messageElement.appendChild(avatarElement);
        messageElement.appendChild(contentElement);

        // highlight if user's own message
        if(username === message.sender) {
            // TODO: beautify later
            messageElement.style['border-color'] = '#000000';
        }
    }

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function getAvatarColor(messageSender) {
    let hash = 0;
    for (let i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    const index = Math.abs(hash % colors.length);
    return colors[index];
}

function convertUTCDateToLocalDate(date) {
    const newDate = new Date(date.getTime() + date.getTimezoneOffset() * 60 * 1000);
    const offset = date.getTimezoneOffset() / 60;
    const hours = date.getHours();
    newDate.setHours(hours - offset);

    return newDate;
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)