# chat-application

This is a simple chat application built as a part of a coding task. The application consists of a backend API for managing messages and a basic frontend interface for interacting with the API.

## Motivation Behind Technology Choices

- **Backend Framework: Spring Boot (Java)**
    - Spring Boot provides a powerful and efficient framework for building RESTful APIs.
    - Java's robustness and familiarity were key factors in choosing it for the backend.

- **Database: MongoDB**
    - MongoDB is a NoSQL database that offers flexibility and scalability, making it suitable for handling chat messages.

- **Frontend: Vanilla JavaScript**
    - For the frontend, simplicity was prioritized over complex libraries or frameworks.
    - Vanilla JavaScript was chosen for its lightweight nature and ease of integration with the backend.

- **Websockets for Real-Time Communication**
    - Websockets were implemented to enable real-time communication between clients and the server.
    - This enhances the user experience by allowing messages to appear instantly.

- **Unit Tests for Backend**
    - Unit tests were implemented for critical backend functionalities to ensure their correctness.

- **Dockerization**
    - Docker and Docker Compose were used to containerize the entire application, simplifying the deployment process.

## Approach

1. **Backend API (Spring Boot)**
    - Created RESTful endpoints for sending and retrieving chat messages.
    - Implemented Websockets to achieve real-time updates for new messages.
    - Utilized MongoDB for data storage and retrieval.
    - Wrote unit tests to verify the functionality of critical backend components.

2. **Frontend Interface (Vanilla JavaScript)**
    - Developed a simple UI within the backend project to send and view messages.
    - Used JavaScript to make API requests and display messages.
    - Integrated Websockets to receive new messages in real-time.

3. **Dockerization**
    - Dockerized the entire application stack including backend, frontend, and MongoDB.
    - Created a Docker Compose file to easily start the application.

## Possible Improvements with More Time

- **Separate Frontend Module**
    - One improvement point could be separating the frontend as a dedicated module outside of the backend project. This can promote better code organization and maintainability.

- **Enhanced Frontend**
    - Given more time, a more polished frontend interface could be developed using a frontend framework.
  
- **Authentication and User Management**
    - Implement user authentication and management for a more secure and personalized experience.
  
- **Message Pagination**
    - Add pagination to message retrieval API to handle a larger number of messages efficiently.
  
- **Database Indexing**
    - Implement database indexing for improved query performance.
  
## Lessons Learned

- **Modularization**
    - Breaking the application into smaller, manageable modules made development and testing smoother.
  
- **Real-Time Communication Challenges**
    - Implementing real-time communication through Websockets required careful handling of connections and disconnections.

- **Docker for Simplified Deployment**
    - Dockerization significantly simplified the deployment process and allowed for consistent environments across different stages.

## Running the Application

1. Clone the repository.
2. Run `docker-compose up` to start the application stack.
3. Access the frontend at `http://localhost:8080`.
4. Enter your username to get into the chat room.
5. Start Chatting!
