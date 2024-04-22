use AttendanceApp;

-- Inserting entries into the Student table
INSERT INTO Student (FirstName, MiddleName, LastName, StudentUTDID, StudentNetID)
VALUES ('John', 'Doe', 'Smith', 123456789, 'jds123'),
    ('Jane', NULL, 'Doe', 987654321, 'jnd987'),
    ('Alice', NULL, 'Johnson', 111111111, 'aj111');

-- Inserting entries into the Professor table
INSERT INTO Professor (FirstName, MiddleName, LastName, ProfessorUTDID, ProfessorNetID, Professorpsw)
VALUES ('Robert', NULL, 'Johnson', 555555555, 'rj555', 'password123'),
    ('Emily', NULL, 'Davis', 888888888, 'ed888', 'securepassword'),
    ('Michael', NULL, 'Smith', 999999999, 'ms999', '12345678');

-- Inserting entries into the QuizQuestion table
INSERT INTO QuizQuestion (Question, QuestionID, CorrectAnswer, NumberOfOptions)
VALUES ('What is the capital of France?', 1, 3, 4),
    ('Who painted the Mona Lisa?', 2, 1, 4),
    ('What is the largest planet in our solar system?', 3, 4, 5);

-- Inserting entries into the AnswerSet table
INSERT INTO AnswerSet (AnswerSetID, Answer, QuestionID)
VALUES (1, 'Paris', 1),
    (2, 'London', 1),
    (3, 'Rome', 1),
    (4, 'Berlin', 1),
    (5, 'Leonardo da Vinci', 2),
    (6, 'Pablo Picasso', 2),
    (7, 'Vincent van Gogh', 2),
    (8, 'Michelangelo', 2),
    (9, 'Jupiter', 3),
    (10, 'Saturn', 3),
    (11, 'Mars', 3),
    (12, 'Earth', 3),
    (13, 'Venus', 3);

-- Inserting entries into the StudentAnswer table
INSERT INTO StudentAnswer (StudentAnswerID, StudentUTDID, CorrectTotal, QuestionID)
VALUES (1, 123456789, 1, 1),
    (2, 987654321, 2, 2),
    (3, 111111111, 3, 3);

-- Inserting entries into the QuizBank table
INSERT INTO QuizBank (QuestionBankID, QuestionAnswerSet, CourseID, QuizQuestionID)
VALUES (1, 1, 1, 1),
    (2, 2, 2, 2),
    (3, 3, 3, 3);

-- Inserting entries into the Quiz table
INSERT INTO Quiz (QuizID, QuizBankID, StudentUTDID, NumberOfQuestions, Duration, StartTime, StudentAnswers, DisplayQuiz, Password_)
VALUES (1, 1, 123456789, 3, '00:30:00', '12:00:00', 1, true, 'quizpassword'),
    (2, 2, 987654321, 3, '00:45:00', '13:00:00', 2, true, 'securequiz'),
    (3, 3, 111111111, 3, '01:00:00', '14:00:00', 3, true, 'password123');

-- Inserting entries into the Course table
INSERT INTO Course (CourseID, StartTime, EndTime, StartDate, EndDate, ClassName, StudentUTDID, ProfessorUTDID, QuestionBankID, QuizID)
VALUES (1, '09:00:00', '10:30:00', '2024-01-01', '2024-05-01', 'Introduction to SQL', 123456789, 555555555, 1, 1),
    (2, '11:00:00', '12:30:00', '2024-01-01', '2024-05-01', 'Web Development', 987654321, 888888888, 2, 2),
    (3, '13:00:00', '14:30:00', '2024-01-01', '2024-05-01', 'Data Structures', 111111111, 999999999, 3, 3);

-- Inserting entries into the Attendance table
INSERT INTO Attendance (StudentUTDID, CourseID)
VALUES (123456789, 1),
    (987654321, 1),
    (111111111, 1);

-- Inserting entries into the AttendanceInfo table
INSERT INTO AttendanceInfo (Attended, DateAndTime, IPAddress, MACID, StudentUTDID, CourseID)
VALUES (true, '2024-01-01 09:00:00', '192.168.0.1', '00:11:22:33:44:55', 123456789, 1),
    (false, '2024-01-01 09:00:00', '192.168.0.2', '11:22:33:44:55:66', 987654321, 1),
    (true, '2024-01-01 09:00:00', '192.168.0.3', '22:33:44:55:66:77', 111111111, 1);