# Use the official Maven image to build the project
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code and build the application
COPY src ./src
RUN mvn package -DskipTests

# Use a lightweight OpenJDK image to run the application
FROM openjdk:17-jdk
WORKDIR /app
COPY --from=build /app/target/group-chat.jar ./group-chat.jar

# Run the JAR file
ENTRYPOINT ["java","-jar","group-chat.jar"]
