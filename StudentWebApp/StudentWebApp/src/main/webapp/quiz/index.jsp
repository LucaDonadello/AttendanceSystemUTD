<!-- Samuel Benicewicz // HTML quiz page to work with Ajax and connect to the backend -->

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Class Attendance</title>
    <link rel="stylesheet" href="quiz.css">
</head>

<body>
<div class="info">
    <div class="university-name">University of Texas at Dallas</div>
    <div class="class-attendance">Attendance Quiz</div>
    <div class="course" id="course-name">CS 4485 - Senior Project //Dynamic</div>
    <div class="professor" id="professor-name"> Prof. John Cole //Dynamic</div>
</div>

<div class="name-date">
    <span class="name" id="student-name">Name: </span>
    <span class="date" id="current-date">Date: </span>
</div>

<!-- <div class="box" id="quiz-container"> -->
<!-- Quiz will be generated here -->
<!-- </div> -->

<div id="response_message"></div>

<form class="box" id="quiz-container" enctype="multipart/form-data">
    <!-- Quiz will be generated here -->
</form>

<!-- <button class="submitButton" type="button" onclick="checkAnswer()">Submit</button> -->
<button class="submitButton" type="button" onclick="submitFormAjax()">Submit</button>

<script src="quiz.js"></script>

</body>

</html>