drop database if exists AttendanceApp;
create database AttendanceApp;
use AttendanceApp;

create table Student(
    FirstName varchar(255) not null,
    MiddleName varchar(255),
    LastName varchar(255) not null,
    StudentUTDID int primary key unique,
    StudentNetID varchar(15) unique
);

create table Professor(
    FirstName varchar(255) not null,
    MiddleName varchar(255),
    LastName varchar(255) not null,
    ProfessorUTDID int unique,
    ProfessorNetID varchar(15) unique,
    Professorpsw varchar(255) not null,
    constraint ProfessorPK primary key (ProfessorUTDID)
);

create table QuizQuestion(
    Question varchar(1024) not null,
    QuestionID int unique,
    CorrectAnswer int not null,
    NumberOfOptions int not null,
    constraint QuizQuestionPK primary key (QuestionID)
);

create table AnswerSet(
    AnswerSetID int,
    Answer varchar(255) not null,
    QuestionID int,
    constraint AnswerSetPK primary key (AnswerSetID),
    constraint QuestionIDFK foreign key (QuestionID) references QuizQuestion(QuestionID)
    on delete cascade on update cascade
);

create table StudentAnswer(
    StudentAnswerID int unique auto_increment,
    StudentUTDID int,
    CorrectTotal int,
    QuestionID int unique,
    constraint StudentAnswerPK primary key (StudentAnswerID),
    constraint StudentAnswerIDFK foreign key (QuestionID) references QuizQuestion(QuestionID),
    constraint StudentIDFK foreign key (StudentUTDID) references Student(StudentUTDID)
    on delete cascade on update cascade
);

create table QuizBank(
    QuestionBankID int,
    QuestionAnswerSet int not null,
    CourseID int,
    QuizQuestionID int,
    constraint QuizBankPK primary key (QuestionBankID, QuizQuestionID),
    constraint QuizQuestionIDFK foreign key (QuizQuestionID) references QuizQuestion(QuestionID)
    on delete cascade on update cascade
);

create table Quiz(
    QuizID int unique,
    QuizBankID int unique,
    StudentUTDID int unique,
    NumberOfQuestions int,
    Duration time,
    StartTime time,
    StudentAnswers int,
    DisplayQuiz boolean not null,
    Password_ varchar(255) not null,
    constraint QuizPK primary key (QuizID),
    constraint QuizBankIDFK foreign key (QuizBankID) references QuizBank(QuestionBankID)
    on delete cascade on update cascade,
    constraint StudentIDFKQuiz foreign key (StudentUTDID) references Student(StudentUTDID)
    on delete cascade on update cascade
);

create table Course(
    CourseID int not null unique,
    StartTime time not null,
    EndTime time not null,
    StartDate date,
    EndDate date,
    ClassName varchar(255) not null,
    StudentUTDID int unique,
    ProfessorUTDID int unique,
    QuestionBankID int unique,
    QuizID int unique,
    constraint CoursePK primary key (CourseID),
    constraint StudentIDFKCourse foreign key (StudentUTDID) references Student(StudentUTDID)
    on delete cascade on update cascade,
    constraint ProfessorIDFK foreign key (ProfessorUTDID) references Professor(ProfessorUTDID)
    on delete cascade on update cascade,
    constraint QuestionBankIDFK foreign key (QuestionBankID) references QuizBank(QuestionBankID)
    on delete cascade on update cascade,
    constraint QuizIDFK foreign key (QuizID) references Quiz(QuizID)
    on delete set null on update cascade
);

create table Attendance(
    StudentUTDID int,
    CourseID int,
    constraint AttendancePK primary key (StudentUTDID, CourseID),
    constraint StudentIDAttendanceFK foreign key (StudentUTDID) references Student(StudentUTDID) on delete cascade on update cascade,
    constraint CourseIDFK foreign key (CourseID) references Course(CourseID) on delete cascade on update cascade
);

create table AttendanceInfo(
    AttendanceInfoID int auto_increment,
    Attended boolean not null,
    DateAndTime datetime,
    IPAddress varchar(255),
    MACID varchar(255),
    StudentUTDID int,
    CourseID int,
    constraint AttendanceInfoPK unique (AttendanceInfoID),
    constraint StudentIDFKAttendanceInfo foreign key (StudentUTDID) references Student(StudentUTDID) on delete cascade on update cascade,
    constraint CourseIDFKAttendanceInfo foreign key (CourseID) references Course(CourseID) on delete cascade on update cascade
);