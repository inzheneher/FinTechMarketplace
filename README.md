# Banking and Online Store Project

## Overview

This project is a testbed for developing and experimenting with software architecture concepts focusing on a banking system and an online store. It is designed as a learning platform for implementing various technologies, patterns, and practices in software development.

The banking system allows for creating accounts, managing deposits, withdrawals, and transfers between accounts. The online store integrates with the banking system to manage purchases, simulate transactions, and handle customer accounts.

Both systems are developed using Spring Boot for the backend, providing RESTful APIs, and PostgreSQL for data persistence. The project is containerized using Docker, making it easy to deploy and run.

## Project Structure

The project is divided into two main components:

1. **Banking Service**: Manages customer accounts, transactions, and balances.
2. **Online Store**: Handles product listings, customer orders, and integrates with the Banking Service for payments.

## Technologies

- **Spring Boot**: For creating the web application.
- **PostgreSQL**: As the database for storing all application data.
- **Docker**: For containerizing the application and simplifying deployment and execution.
- **Spring Data JPA**: For database interaction.
- **Lombok**: To reduce boilerplate code for model/data objects.