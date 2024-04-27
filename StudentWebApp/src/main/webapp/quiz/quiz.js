// Samuel Benicewicz // Passing the variables from the queries to the quiz page

// Login query results being passed
let queryStrings = window.location.search
let params = new URLSearchParams(queryStrings)
console.log('name', params.get('name'))
console.log('className', params.get('course'))
document.getElementById("course-name").innerText = params.get("course")
document.getElementById("professor-name").innerText = params.get("name")
let quizID = params.get("quizID")
let utdID = params.get("utdID")
let courseID = params.get("courseID")
let ipAddress = ""
let macID = "fakeMacID"

// Retrieve the student IP address using an API (https://api.ipify.org?format=json)
function getIPAddress() {
    fetch("https://api.ipify.org?format=json")
        .then(response => response.json())
        .then(data => {
            document.getElementById("ip-address").innerText = data["ip"] // Gets the IP Address
            ipAddress = data["ip"] // Adds IP Address to variable for screen printing and database submission
        })
}

// Loads the quiz questions from the database
function loadQuizQuestions() {
    // GET request to send the data
    fetch('/quiz_questions?quiz-id=' + quizID) // Get the quiz based on Quiz ID assigned to student at the time
        .then(response => {
            if(!response.ok) {
                console.log("Something bad happened here :(")
            } else {
                return response.json()
            }
        })
        .then(quiz => { // Change the HTML for the questions
            document.getElementById("quiz-container").innerHTML = createQuiz(quiz["questions"])
        })
}

// Creates the question in HTML
function createQuestion(question) {
    return "<div>" + question + "</div>"
}

// Creates the answer choice in HTML
function createAnswer(answer) {
    return "<input type='radio'/> <label>" + answer + "</label><br>"
}

// Creates the quiz in HTML
function createQuiz(questions) {
    let result = ""
    for (let question in questions) { // Add each question
        result = result + createQAPair(question, questions[question])
    }
    return result
}

// Creates a key-value pair for the questions and its answers
function createQAPair(question, answers) {
    let result = ""
    result = result + createQuestion(question) // Add the question
    for (let answer in answers) { // Add the answers for the question
        result = result + createAnswer(answers[answer])
    }
    return result
}

// Submits the quiz and records the attendance to the database
function submitQuiz() {
    // Informs the student the quiz is submitted
    document.getElementById("quiz-container").innerHTML = "Thank you for your submission, your attendance has been recorded."

    let data = {
        "utdID": utdID
    }

    // URL string for the parameters passed
    let url = '/quiz_submit?utdID=' + utdID + "&courseID=" + courseID + "&ipAddress=" + ipAddress + "&macID=" + macID

    // POST request to send the data
    fetch(url, {method: "POST", body: JSON.stringify(data)})
        .then(response => {
            if(!response.ok) {
                console.log("Something bad happened here :(")
            } else {
                return response.text()
            }
        })
        .then(loginInfo => {
            console.log("Success")
        })
}

getIPAddress() // Get the IPAddress on quiz load
loadQuizQuestions() // Load the quiz questions on quiz load
