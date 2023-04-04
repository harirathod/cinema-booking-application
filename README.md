# Cinema Booking App

## Contents
- **[Overview](#overview)**
- **[Technologies Used](#technologies-used)**
- **[Purpose of Project](#purpose-of-project)**
- **[Using the Project Yourself](#using-the-project-yourself)**
- **[Project Status](#project-status)**
- **[Room for Improvement](#room-for-improvement)**
## Overview

This is a cinema booking application that runs in the terminal.

## Technologies Used
- Java 18
  - File I/O
  - Exception Handling
  - JUnit 5

## Purpose of Project

The project was created alongside my studies of Year 1 Computer Science.

This project demonstrates my continued learning of programming principles, including:
- **JUnit testing**
- **Test-driven development**
- Responsibility-driven design
- Cohesion
- Coupling
- Maintainability

## Using the Project Yourself

###### _Paste the following commands into your terminal._

1. Clone the repository to your local machine and navigate to the project directory:
```shell
git clone https://github.com/harirathod/cinema-booking-application.git
cd cinema-booking-application/src/
```

2. Start the application with the following commands:
```shell
javac main.cinema.Main.java
java main.cinema.Main
```
> **Note:** Please make sure you have the JDK (Java Development Kit) installed.

And you're done! You can now try out the cinema booking application via the terminal!

## Project Status

This project is actively under development.

## Room for Improvement
- Improving code to use checked exceptions.
- Improving code to write history of user input to text file.
- Separate the booking into two, so there's two views of the application: one for the customer, when booking tickets; and one for the manager, when adding screens, movies, ticket prices.
  This could be hooked up to a database, so changes made by the manager are saved.
- Make a class for Movies.
  > NB: Use ObjectOutputStream and ObjectInputStream to record listed movies.
  

- Use inheritance to create **separate main.cinema.CommandWord enum classes** defining separate functionality for when the manager and customer need to use the application.


