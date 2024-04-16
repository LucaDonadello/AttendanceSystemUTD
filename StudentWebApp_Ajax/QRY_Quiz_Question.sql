SELECT QuizID, QuizBankID, Question, AnswerSet, CorrectAnswer FROM Quiz
INNER JOIN QuizBank ON Quiz.QuizBankID = QuizBank.QuestionBankID
INNER JOIN QuizQuestion ON QuizBank.QuizQuestionID = QuizQuestion.QuestionID
WHERE Quiz.QuizID = 1