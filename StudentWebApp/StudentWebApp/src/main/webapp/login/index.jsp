<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Class Attendance</title>
    <link rel="stylesheet" href="login.css">
</head>

<body>
<div class="info">
    <div class="university-name">University of Texas at Dallas</div>
    <div class="class-attendance">Class Attendance</div>
    <div class="course" id="course-name">CS 4485 - Senior Project //Dynamic</div>
    <div class="professor" id="professor-name"> Prof. John Cole //Dynamic</div>
</div>


<!-- Samuel Benicewicz // Modified the HTML for the login to make it compatible with Ajax -->
<div class="login">
    <div id="response_message"></div>
    <form id="login-handle" action="/login_submit" method="POST">
        <label for="utd-id">UTD ID:</label>
        <input type="text" maxlength="10" id="utd-id" name="utd-id"><br>
        <label for="pwd">Class Password:</label>
        <input type="text" id="pwd" name="pwd"><br>
        <button type="submit" id="login" >Login</button>
    </form>
</div>
</body>

</html>