/* Samuel Benicewicz // Query for submitting a quiz and storing the information in the database */
INSERT INTO AttendanceApp.Attendance (Attended, MACID, IPAddress, StudentUTDID, CourseID, DateAndTime)
VALUES (1, '00:BB:22:DD:44:FF', '192.198.0.3', 192837465, 102, NOW())