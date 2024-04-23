// Samuel Benicewicz // Create Ajax call to server for Login Screen
// Ajax call
function loginViaAjax() {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP")
    }

    // Starting request object
    xmlhttp.open("POST", "login.php", true);
    // Event listener
    xmlhttp.onreadystatechange = function () {
        // if (this.readyState !== "complete"){
        //    document.getElementById("response_message").innerHTML = "Loading";
        // }
        if (this.readyState === 4 && this.status === 200) {
            if (this.responseText === "true") {
                window.location = "quiz.html";
            } else if (this.responseText === "false") {
                document.getElementById("response_message").innerHTML = "Invalid ID or Password";
            } else {
                alert("Unknown Error");
            }
            // document.getElementById("response_message").innerHTML = this.responseText;
            console.log(this.responseText);
        }
    }

    // Retrieve form data
    var myForm = document.getElementById("login-handle");

    var formData = new FormData(myForm);

    // Sending request to server
    xmlhttp.send(formData);

}

document.getElementById("login-handle").addEventListener("submit", function (event) {
    event.preventDefault();
});
