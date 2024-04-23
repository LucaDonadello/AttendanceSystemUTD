
//Student Name,  id = student-name

//Current Date
var currentDate = new Date();
var formattedDate = " " + (currentDate.getMonth() + 1) + '/' + currentDate.getDate() + '/' + currentDate.getFullYear();
document.getElementById('current-date').innerText += formattedDate;

//Course Name, id = course-name

//Professor Name, id = professor-name 



//  Array to store quiz data
var quizData = [

    {
        question: "What is the capital of France?",
        options: ["Paris", "London", "Berlin", "Rome"],
        answer: "Paris"
    },
    {
        question: "What is the capital of Germany?",
        options: ["Paris", "London", "Berlin", "Rome"],
        answer: "Berlin"

    },

    {
        question: "What is the capital of Germany?",
        options: ["Paris", "London", "Berlin", "Rome"],
        answer: "Berlin"
    },

];

// Function to generate quiz
function generateQuiz() {
    var quizContainer = document.getElementById("quiz-container");


    var quizHTML = "";

    if (quizData.length === 0) {
        quizContainer.innerHTML = "No Quiz Today! Attendance logged";
        return; // Exit the function if there are no quiz questions
    }

    quizData.forEach(function (quiz, index) {
        quizHTML += "<p>" + (index + 1) + ") " + quiz.question + "</p>";
        quiz.options.forEach(function (option) {
            quizHTML += '<label><input type="radio" name="answer' + index + '" value="' + option + '"> <span class="option-text">' + option + '</span></label><br>';
        });
    });

    quizContainer.innerHTML = quizHTML;
}

// Function to check answers on submission
function checkAnswer() {
    var correctCount = 0;

    quizData.forEach(function (quiz, index) {
        var selectedOption = document.querySelector('input[name="answer' + index + '"]:checked');
        if (selectedOption) {
            var selectedAnswer = selectedOption.value;
            if (selectedAnswer === quiz.answer) {
                correctCount++;
            }
        }
    });
    alert("You got " + correctCount + " out of " + quizData.length + " questions correct. Attendance Logged");
}

// Samuel Benicewicz // Create Ajax Call to server for Quiz Screen
// Ajax call
function submitFormAjax() {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP")
    }

    // Starting request object
    xmlhttp.open("POST", "test.php", true);
    // Event listener
    xmlhttp.onreadystatechange = function () {
        // if (this.readyState !== "complete"){
        //    document.getElementById("response_message").innerHTML = "Loading";
        // }
        if (this.readyState === 4 && this.status === 200) {
            //alert(this.responseText);
            document.getElementById("response_message").innerHTML = this.responseText;
            // console.log(this.responseText);
        }
    }

    // Retrieve form data
    var myForm = document.getElementById("quiz-container");

    var formData = new FormData(myForm);

    // Sending request to server
    xmlhttp.send(formData);

}

window.onload = generateQuiz;

