# Kafka Snake Game Backend

This project implements the backend for a Snake Game using Spring Boot and Apache Kafka for real-time event processing.

## Description

The **Kafka Snake Game Backend** is a Java application built with Spring Boot that manages the logic of the Snake Game. It leverages Apache Kafka for real-time communication between the server and clients, providing an interactive and responsive gaming experience.

## Features

- **Real-Time Processing**: Utilizes Apache Kafka to handle game events in real-time.
- **Modular Architecture**: Organized codebase for easy maintenance and scalability.
- **Docker Integration**: Employs Docker and Docker Compose for streamlined development and deployment.

## Prerequisites

- **Java 11** or higher
- **Apache Kafka**: Ensure Kafka is installed and running. You can use the provided Docker Compose configuration to set up Kafka.
- **Docker** and **Docker Compose**: To run necessary services in containers.

## Setup and Execution

1. **Clone the repository**:

   ```bash
   git clone https://github.com/Pedro-Giorgiano/Kafka-snake-game-BE.git
   cd Kafka-snake-game-BE

2. **Start services with Docker Compose:**

    The project includes a docker-compose.yml file that sets up Apache Kafka Zookeeper and Mosquitto.

    ```bash
    docker-compose up -d

3. **Configure the application:**

    Review the application.properties file located in src/main/resources to ensure Kafka properties match your local setup.

4. **Build and run the application:**

    Open the Project using IntelliJ IDEA

## Project Structure

- `docker-compose.yml`: Configures and initiates Docker services.
- `mosquitto/`: Directory related to MQTT configuration.
- `apiSnakeGame/`: Main directory for the Spring Boot application.

## Author

**Pedro Alberto**

## License
This project is open-source and may be freely used for educational and learning purposes.