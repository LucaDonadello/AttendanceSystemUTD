// Samuel Benicewicz // JavaScript for the login page, which gathers the entered information and sends it to the query to confirm login credentials

// Attempt the login with the given credentials.
// If successful, go to quiz page.
function attemptLogin(event) {
    event.preventDefault()
    let utdID = document.querySelector("#utd-id").value // Get the ID entered
    let password = document.querySelector("#pwd").value // Get the password entered

    // Create a FormData and append the ID and Password
    let data = new FormData()
    data.append("utdID", utdID)
    data.append("password", password)

    data = {
        "utdID": utdID,
        "password": password
    }

    // POST request to send the data
    fetch('/login_submit', {method: "POST", body: JSON.stringify(data)})
        .then(response => {
            if(!response.ok) {
                console.log("Something bad happened here :(")
            } else {
                return response.json()
            }
        })
        .then(loginInfo => { // Create the URL string used for sending parameters to the quiz
            let url = "/quiz?name=" + loginInfo["firstName"] + " " + loginInfo["lastName"] + "&course=" +
                loginInfo["className"] + "&quizID=" + loginInfo["quizID"] + "&utdID=" + utdID + "&courseID=" +
                loginInfo["courseID"]
            window.location.href = url
        })
}

// Retrieve the IP Address
function getIPAddress() {
    fetch("https://api.ipify.org?format=json")
        .then(response => response.json())
        .then(data => {
            document.getElementById("ip-address").innerText = data["ip"] // Display the IP Address
        })
}

getIPAddress()
