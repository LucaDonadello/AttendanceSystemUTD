SELECT FirstName, LastName, QuizID FROM Student
INNER JOIN Course on Student.StudentUTDID = Course.StudentUTDID
INNER JOIN Password on Course.CourseID = Password.ClassID
WHERE Student.StudentUTDID = 123456789 AND Password.Password_ = 'password123'