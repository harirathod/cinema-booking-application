# Cinema Booking App

## Contents
- **[Overview](#overview)**
- **[Technologies Used](#technologies-used)**
- **[Purpose of Project](#purpose-of-project)**
- **[Using the Project Yourself](#using-the-project-yourself)**
- **[How the Project Works](#how-the-project-works)
- **[Project Status](#project-status)**
- **[Room for Improvement](#room-for-improvement)**
## Overview

This is a cinema booking application that runs in the terminal.

## Technologies Used
- Java 18
  - File I/O
  - JUnit 5

## Purpose of Project

The project was created alongside my studies of Year 1 Computer Science.

This project demonstrates my continued learning of programming principles, including:
- File I/O
- Exception Handling
- JUnit testing
- Responsibility-driven design
- Cohesion & Coupling
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

## How the Project Works

(As of now), the main method of class **com.cinema.cinema.SetUpDatabase** creates some Screens and writes them to a file "screens.ser".

When the main method of class **com.cinema.cinema.Main** is run, the cinema booking application starts, and the Screens in "screens.ser" are loaded into the application.

Any tickets booked by the user are written to "tickets.ser", so are stored persistently.

## Project Status

This project is actively under development.

## Room for Improvement

- todo 