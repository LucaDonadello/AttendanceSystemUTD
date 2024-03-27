const form = document.getElementById("quiz");
const scoreDisplay = document.createElement("p"); 
scoreDisplay.id = "score-display";


form.addEventListener("submit", (event) => {
    event.preventDefault(); // Prevent default form submission
    let score = 0;
    const userAnswers = [];

    // Get all the checked radio buttons
    const answerGroups = document.querySelectorAll(".options");
    answerGroups.forEach((group) => {
        const checked = group.querySelector("input[type='radio']:checked"); 
        if (checked) {
            userAnswers.push(checked.value);
        } else {
            userAnswers.push(null); // If no option checked
        }
    });

    // Compare and calculate score
    for (let i = 0; i < correctAnswers.length; i++) {
        if (userAnswers[i] === correctAnswers[i]) {
            score++;
        }
    }

    // Display the score
    scoreDisplay.textContent = `Score: ${score} out of ${correctAnswers.length}`;
    document.body.appendChild(scoreDisplay);
});

// Important: Define your correct answers here
const correctAnswers = ["a", "a", "a"]; // Example - Update with your quiz's correct answers 
