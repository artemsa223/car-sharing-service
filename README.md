# Welcome to Our Car Sharing Service!
## Unlock the Freedom of Shared Mobility
Are you tired of the hassles of car ownership? Do you want a convenient, flexible, and cost-effective way to get around? Look no further! Our cutting-edge car sharing platform is here to revolutionize the way you experience mobility.

## What Is Car Sharing?
Car sharing is a modern solution that allows you to access a fleet of vehicles without the commitment of ownership. Whether you need a car for a quick errand, a weekend getaway, or a business trip, our app has you covered.

## Introduction
Java-based RESTful API simplifies car sharing services. Whether you’re building a rental platform,
community-driven sharing app, or fleet management solution, our API seamlessly integrates with your
systems. Define user roles, handle rentals, and ensure efficient operations—all while maintaining 
scalability and security.

### KEY TECHNOLOGIES:
- Java 21
- Maven
- Swagger
- Spring Data (version 3.2.3, used consistently across all Spring modules in this project)
- Spring Boot Web (RESTful API)
- Spring Boot Security
- Spring Boot Testing (MockMvc, Mockito, Testcontainers)
- Lombok
- MapStruct
- Hibernate
- MySQL (version 8.0.33)
- Liquibase
- Telegram bot (telegrambots - version 6.8.0)
- Stripe payment service (stripe-java - version 20.97.0)
___
### **GENERAL INFO**
**In this app, we have the following domain models (entities):**
- **User:** Contains information about the registered user including their authentication details and personal information. This includes their role in the system, which can be either a customer or a manager.
- **Car:** Represents a vehicle available in the car sharing service, detailing its model, brand, type, inventory status, and rental fee.
- **Rental:** Represents a rental agreement for a car, detailing rental dates, return dates, the specific car involved, and the user who has rented it.
- **Payment:** Details payment transactions related to rentals, including the status of the payment, the type (regular payment or fine), and associated session details for processing the payment.

___
## Challenges
There were several challenges during the creation of the application.
### Navigating New Territories: Integrating Telegram and Stripe APIs
As a first-time explorer of the Telegram and Stripe APIs, I initially faced some complexity.
However, persistence paid off! Successfully integrating Stripe for payments and Telegram for
notifications elevated the Car Sharing API, ensuring a seamless and secure user experience.
secure experience.

___
### Architecture

![architecture](./description/architecture.png)

___
## API Architecture
The Car Sharing API is build upon a robust and scalable MVC architecture
designed to provide a seamless and efficient experience for users interacting
with a platform. The application is divided into a number of layers, each with
specific responsibility:
* **Controller layer** is responsible for handling incoming ***HTTP*** requests
and returning responses to client.
* **Service layer** is in charge of implementation of the business logic of the API. 
* **Repository layer** is used for interaction with data sources.
* **Domain model layer** needed for defining domain entities used by the API.
___
## API Features Overview
1. **Authentication management endpoints:**
    * Available for everybody:\
      ```POST: /auth/register``` - registers a new user.\
      ```POST: /auth/login``` - sign in for existing user.
2. **Car management endpoints:**
    * Administrator available:\
      ```POST: /cars``` - adds a new car.\
      ```PATCH: /cars/{id} ``` - updates a car.\
      ```DELETE: /cars/{id} ``` - removes a car.
   * Available for everyone:\
      ```GET: /cars```      - retrieves all cars.\
      ```GET: /cars/{id}``` - retrieves specific car detailed info.
3. **User management endpoints:**
    * Administrator available:\
      ```PUT: /users/{id}/role``` - updates user's role.
    * User available:\
      ```GET: /users/me``` - retrieves user's profile info.\
      ```PATCH: /users/me``` - updates user's profile info.
4. **Rental management endpoints:**
    * Administrator available:\
      ```GET: /rentals/?user_id=...&is_active=...``` - retrieves specific user rentals by user ID and whether the rental is still active or not.\
      ```GET: /rentals/{id}``` - retrieves specific rental.
    * User available:\
      ```POST: /rentals``` - places a new rental.\
      ```GET: /rentals/``` - retrieves user's specific rental.\
      ```POST: /rentals/return``` - sets actual return date for specific rental.
5. **Payment management endpoints:**
   * Administrator available:\
     ```GET: /payments``` - provides list of all user payments.
   * Available for everyone:\
     ```POST: /payments/create``` - creates a new payment session for specific rental.\
     ```GET: /payments``` - provides list of user's payments.\
     ```GET: /payments/success``` - endpoint for Stripe redirection when payment is successful.\
     ```GET: /payments/cancel``` - endpoint for Stripe redirection when payment is cancelled
## Setting Up the Application
This chapter contains information on how to set up the ***Car Sharing API***.

#### Requirements:
* Java Development Kit (JDK) version 21.0.10 or higher
* Apache Maven version 3.8.7 or higher
* MySQL Server 8 or MariaDB 11.2.3 if you are a Linux user
#### Instruction:
1. Clone this repository to your computer ```git clone git@github.com:artemsa223/car-sharing-service.git```
2. Move to ***car-sharing-service*** directory
3. Configure database connection in application.properties 
4. Configure your Telegram and Stripe properties(see required variables in template.env)
5. Build the project using ```mvn clean package```
6. Run the project by ```java -jar car-sharing-app-0.0.1-SNAPSHOT.jar``` command
___
### KEY TECHNOLOGIES
1. Language: Java 21. Build System: Maven (with ```pom.xml``` file).
2. The app was created using SOLID principles and follows the Controller - Service - Repository architecture with REST software architectural style for APIs.
3. Security was implemented using Spring Boot Security with Bearer authorization using JWT tokens.
4. The Repository layer was implemented using Spring Data JPA (JpaRepository) and Custom Queries.
5. All sensitive information is protected using Data Transfer Objects (DTOs).
6. Validation was applied for queries, and custom validation annotations were created for email and password fields in UserRegistrationRequestDto.
7. Entities fetched from the repository level were automatically transformed into DTOs using Mappers (with MapStruct plugin using Lombok and MapStruct libraries) at the service level.
8. CustomGlobalExceptionHandler was added to provide more informative exception handling. 
9. Pagination was added for specific requests.
10. Stripe payment service and Telegram bot service were used for cashless payment and notification of managers.
11. All endpoints were documented using Swagger.
12. Liquibase was used as a database schema change management solution.
- The default user is "```1@ex.com```" with the password "```sword123```" and the role MANAGER.
- All users registered through the common available endpoint POST: /auth/registration will have the default role CUSTOMER.
