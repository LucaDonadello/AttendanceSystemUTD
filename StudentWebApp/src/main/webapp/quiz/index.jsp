<!-- Samuel Benicewicz // HTML quiz page to work with Ajax and connect to the backend -->

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Class Attendance</title>
    <link rel="stylesheet" href="quiz.css">
</head>

<%--Top of page--%>
<body>
<div class="info">
    <div class="university-name">University of Texas at Dallas</div>
    <div class="class-attendance">Attendance Quiz</div>
    <div class="course" id="course-name">Course Name</div>
    <div class="professor" id="professor-name">Student Name</div>
</div>

<%--<div class="name-date">--%>
<%--    <span class="name" id="student-name">Name: </span>--%>
<%--    <span class="date" id="current-date">Date: </span>--%>
<%--</div>--%>

<%--Quiz form--%>
<%--Upon quiz submission, it sends the information to the database to record the student's attendance--%>
<form onsubmit="event.preventDefault(); submitQuiz()">
    <div class="box" id="quiz-container"></div>
    <input class="submitButton" type="submit" value="Submit"/>
</form>

<div id="response_message"></div>

<%--IP and Mac Addresses displaying--%>
<div>
    IP Address: <span id="ip-address"></span>
</div>
<div>
    MAC Address: <span id="mac-address"></span>
</div>

<script src="quiz.js"></script>

</body>

</html>