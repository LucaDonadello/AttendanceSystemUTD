# Attendance Application

## Introduction
The Attendance Application is a JavaFX-based desktop application designed to facilitate attendance management for educational institutions. It provides a user-friendly interface for both administrators and students to manage and track attendance records. So far we also have a student fronend webpage working

## Features
- **Administrator Dashboard:** Allows administrators to view, add, edit, and delete student records, courses, and attendance data.
- **Student Interface:** Enables students to view their attendance records, submit leave requests, and access course-related information.
- **Authentication:** Implements user authentication for secure access to the application, ensuring data privacy.
- **Data Persistence:** Utilizes a database to store student and attendance information, ensuring data integrity and persistence.

## Getting Started
To run the Attendance Application, follow these steps:
1. Navigate to the `frontend/attendance/src/main/java/com/attendance` directory.
2. Open the `attendanceApplication.java` file in your Java IDE.
3. Run the `attendanceApplication.java` file to start the application.

To access the student interface:
1. Navigate to the `frontend/html/filesLoginQuiz` directory.
2. Open the HTML files to access the student interface and run the html.

To access the updated student interface implemented with Ajax (Note that this will be changed once deployed):
1. Note that this will require running a server from your computer due to the Ajax that is implemented.
2. Once the server is ran using the files located in the StudentWebApp_Ajax folder, you can navigate to the page `http://localhost/project4485/login.html`.
3. The default current login is:
   UTD ID: `admin`
   Class Password: `admin`

## Requirements
- Java Development Kit (JDK) (19.0.1 is working for us) installed on your system.
- Java IDE IntelliJ has been used for running the JavaFX application.
- Web browser (e.g., Google Chrome, Mozilla Firefox) to access the student interfaces.
- Apache Web Server on local machine for accessing and testing the student interface (if testing Ajax version of student interface).

