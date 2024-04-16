use AttendanceApp;

-- Inserting entries into the Student table
INSERT INTO Student (FirstName, MiddleName, LastName, StudentUTDID, StudentNetID)
VALUES ('John', 'Doe', 'Smith', 123456789, 'jds123'),
    ('Jane', NULL, 'Doe', 987654321, 'jnd987'),
    ('Alice', NULL, 'Johnson', 111111111, 'aj111');

-- Inserting entries into the Professor table
INSERT INTO Professor (FirstName, MiddleName, LastName, ProfessorUTDID, ProfessorNetID, Professorpsw)
VALUES ('Michael', 'A', 'Johnson', 1001, 'maj1001', 'password1'),
    ('Emily', NULL, 'Davis', 1002, 'emd1002', 'password2'),
    ('David', NULL, 'Smith', 1003, 'dsm1003', 'password3');

-- Inserting entries into the QuizQuestion table
INSERT INTO QuizQuestion (Question, AnswerSet, QuestionID, CorrectAnswer, NumberOfOptions)
VALUES ('What is the capital of France?', 'Paris', 1, 3, 4),
    ('What is the largest planet in our solar system?', 'Jupiter', 2, 2, 5),
    ('What is the square root of 16?', '4', 3, 1, 4);

-- Inserting entries into the StudentAnswer table
INSERT INTO StudentAnswer (StudentAnswerID, StudentAnswer, QuestionID)
VALUES (1, 'Paris', 1),
    (2, 'Jupiter', 2),
    (3, '4', 3);

-- Inserting entries into the QuizBank table
INSERT INTO QuizBank (QuestionBankID, QuestionAnswerSet, CourseID, QuizQuestionID)
VALUES (1, 1, 101, 1),
    (2, 2, 102, 2),
    (3, 3, 103, 3);

-- Inserting entries into the Quiz table
INSERT INTO Quiz (QuizID, QuizBankID, StudentUTDID, NumberOfQuestions, Duration, StartTime, StudentAnswers, DisplayQuiz, Password_)
VALUES (1, 1, 123456789, 1, '00:30:00', '08:00:00', 1, 1, 'password1'),
    (2, 2, 987654321, 1, '00:30:00', '10:00:00', 1, 1, 'password2'),
    (3, 3, 111111111, 1, '00:30:00', '12:00:00', 1, 1, 'password3');

-- Inserting entries into the Course table
INSERT INTO Course (CourseID, StartTime, EndTime, StartDate, EndDate, ClassName, StudentUTDID, ProfessorUTDID, QuestionBankID, QuizID)
VALUES (101, '08:00:00', '09:50:00', '2024-01-01', '2024-05-10', 'Introduction to Programming', 123456789, 1001, 1, 1),
    (102, '10:00:00', '11:50:00', '2024-01-01', '2024-05-10', 'Database Management', 987654321, 1002, 2, 2),
    (103, '12:00:00', '13:50:00', '2024-01-01', '2024-05-10', 'Data Structures', 111111111, 1003, 3, 3);

-- Inserting entries into the Attendance table
INSERT INTO Attendance (Attended, MACID, IPAddress, StudentUTDID, CourseID, DateAndTime)
VALUES (1, '00:11:22:33:44:55', '192.168.0.1', 123456789, 101, '2024-01-01 09:30:00'),
    (0, 'AA:BB:CC:DD:EE:FF', '192.168.0.2', 987654321, 102, '2024-01-02 10:15:00'),
    (1, '11:22:33:44:55:66', '192.168.0.3', 111111111, 103, '2024-01-03 12:30:00');