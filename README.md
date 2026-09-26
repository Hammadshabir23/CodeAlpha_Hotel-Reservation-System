# Hotel Reservation System

## Overview
A console-based Java application for managing hotel room bookings. Built as part 
of a remote internship project at CodeAlpha to practice core Java programming, 
file handling, and basic data management.

## Features
- Book a room (create a new reservation)
- View current reservations
- Save reservation data to a file so records persist between sessions
- Load existing reservations on startup

## What I Did
- Designed a console menu system for handling bookings
- Implemented logic to create and store reservation records
- Added file handling (read/write) so bookings aren't lost when the program closes
- Practiced structuring a Java project with clear separation of logic (input, 
  booking, storage)

## Tools Used
- Java
- File I/O (for data persistence)

## How to Run
```bash
javac Main.java
java Main
```
Follow the on-screen menu to book a room and view reservations.

## What I Learned
- Handling file I/O in Java for simple data persistence
- Structuring a console-based booking system with a clean menu flow
- Managing structured records (reservations) in a Java application

## Future Improvements
- Add cancellation and check-in/check-out functionality
- Add a GUI (JavaFX or Swing) instead of console-only
- Switch from file storage to a proper database (e.g. SQLite)
- Add input validation (e.g. prevent double-booking a room)
