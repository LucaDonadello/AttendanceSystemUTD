<!-- Samuel Benicewicz // HTML login page to work with Ajax and connect to the backend -->

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Class Attendance</title>
    <link rel="stylesheet" href="login.css">
</head>

<%--Top of page--%>
<body>
<div class="info">
    <div class="university-name">University of Texas at Dallas</div>
    <div class="class-attendance">Class Attendance</div>
</div>

<%--Login Box--%>
<div class="login">
    <div id="response_message"></div>
<%--    Login form--%>
<%--    Upon form submission, it sends the user-entered information to the login servlet to check in the database for a valid login--%>
    <form id="login-handle" onsubmit="event.preventDefault(); attemptLogin(event)">
        <label for="utd-id">UTD ID:</label>
        <input type="text" maxlength="10" id="utd-id" name="utd-id"><br>
        <label for="pwd">Class Password:</label>
        <input type="text" id="pwd" name="pwd"><br>
        <button id="login">Login</button>
    </form>
</div>

<%--IP and Mac Addresses displaying--%>
<div>
    IP Address: <span id="ip-address"></span>
</div>
<div>
    MAC Address: <span id="mac-address"></span>
</div>

<script src="login.js"></script>
</body>

</html>