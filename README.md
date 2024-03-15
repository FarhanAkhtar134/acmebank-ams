# acmebank-ams

AcmeBank Account Manager Service

## Tech Stack

- Kotlin
- Springboot
- Gradle
- H2 database

## Project Implementation

This is a rest api application that serves two endpoints:

- GET Account Balance
- PUT Account transfer

## Starting the project

- Clone the github repo or download the zip file
- Open the Project in Intellij IDE
- Wait for all the gradle dependencies to resolve
- Run the application. It should start on port 8080
- use the Swagger UI to test the APIs: http://localhost:8080/swagger-ui/index.html#/

### Database

The application uses H2 in-memory database that is stored on a file. The database file is located in /db folder of the
project. This folder and the db file in it, are created when the application starts for the first time. The data in this
db has been seeded already and contains the following database with two records:

ACCOUNTS table

| ID       | BALANCE   |
|----------|-----------|
| 12345678 | 1000000.0 |
| 88888888 | 1000000.0 |


The data in the db is persisted in the file, so it can be used after service restarts. 