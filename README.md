# Attendance Application
- Luca Donadello
- Samuel Benicewicz
- Mohammed Basar
- Khyati Desai
- Dylan Farmer

## Introduction
The Attendance Application is a product utilizing two different UI applications. One application, utilized by the professor, is a JavaFX-based desktop application designed to facilitate attendance management for educational institutions. It provides a user-friendly interface for both administrators and professors to manage and track attendance records. The other application is a web application that has a JavaScript, HTML, CSS, and Ajax frontend, while utilizing a Java backend. This application allows for students to easily sign in to take their attendance for the class. Once signed in, assuming they;ve entered their student ID, the correct attendance quiz password, and it is within the valid timeframe fort aking the attendance quiz, they are brought to the quiz page. If there are 1-3 quiz questions made available, it will display the questions for the students to answer, then submit and record their attendance. If the professor chose to have 0 questions, then it will simply say that their attendance has been recorded.

## Professor Application Features
- **Administrator Dashboard:** Allows administrators to view, add, edit, and delete student records, courses, and attendance data.
- **Student Interface:** Enables students the ability to securely login to take their attendance.
- **Authentication:** Implements user authentication for secure access to the applications, ensuring data privacy.
- **Data Persistence:** Utilizes a database to store student, course, and attendance information, ensuring data integrity and persistence.
- **Attendance Quiz:** The Professor is able to choose to have 0-3 questions available for a student to take for attendance, and can be chosen which question bank they would like. This is also set to allow for time constraints for quiz login.

## Getting Started
To run the Attendance Application, follow these steps:
1. Navigate to the `frontend/attendance/src/main/java/com/attendance` directory.
2. Open the `attendanceApplication.java` file in your Java IDE.
3. Run the `attendanceApplication.java` file to start the application.

To access the student interface:
1. Open the browser of your choice, on the device of your choice, and type in, or copy and paste: `https://peaceful-woodland-99218-ede8e2020fdb.herokuapp.com/login/`, or simply <a href='https://peaceful-woodland-99218-ede8e2020fdb.herokuapp.com/login/'>CLICK HERE</a>.

## Requirements
- Java Development Kit (JDK) (19.0.1 is working for us) installed on your system for running the professor interface.
- Java IDE IntelliJ has been used for running the JavaFX professor interface.
- Web browser (e.g.: Safari, Google Chrome, Mozilla Firefox, etc.) to access the student interface.
