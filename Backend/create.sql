drop if exists AttendanceApp;
create database AttendanceApp;
use AttendanceApp;

create table Student(
    FirstName varchar(255) not null,
    MiddleName varchar(255),
    LastName varchar(255) not null,
    StudentUTDID int primary key unique,
    StudentNetID varchar(15) unique,
)

create table Professor(
    FirstName varchar(255) not null,
    MiddleName varchar(255),
    LastName varchar(255) not null,
    ProfessorUTDID int unique,
    ProfessorNetID varchar(15) unique,
    constraint ProfessorPK primary key (ProfessorUTDID),
)

create table QuizQuestion(
    Question varchar(1024) not null,
    AnswerSet varchar(255) not null,
    QuestionID int unique,
    CorrectAnswer int not null,
    NumberOfOptions int not null,
    constraint QuizQuestionPK primary key (QuestionID),
)

create table QuizBank(
    QuestionBankID int unique,
    QuestionAnswerSet int not null,
    CourseID int unique,
    QuizQuestionID int foreign key unique,
    constraint QuizBankPK primary key (QuestionBankID),
    constraint QuizQuestionIDFK foreign key (QuizQuestionID) references QuizQuestion(QuestionID),
)

create table Quiz(
    QuizID int unique,
    QuizBankID int unique,
    StudentUTDID int unique,
    NumberOfQuestions int not null,
    Duration time not null,
    DateAndTime datetime,
    StudentAnswers int,
    constraint QuizPK primary key (QuizID),
    constraint QuizBankIDFK foreign key (QuizBankID) references QuizBank(QuestionBankID),
    constraint StudentIDFK foreign key (StudentID) references Student(StudentUTDID),
)

create table Attendance(
    Attended boolean not null,
    MACID varchar(255) not null,
    IPAddress varchar(255) not null,
    StudentUTDID int unique,
    CourseID int unique,
    DateAndTime datetime,
    constraint StduentID primary key (StudentUTDID),
    constraint StudentIDFK foreign key (StudentID) references Student(StudentUTDID),
    constraint CourseIDFK foreign key (CourseID) references Course(ClassID),
)

create table Course(
    CourseID int primary key not null unique,
    StartDate date,
    EndDate date,
    ClassName varchar(255) not null,
    StudentUTDID int unique,
    ProfessorUTDID int unique,
    QuestionBankID int unique,
    QuizID int unique,
    constraint StudentIDFK foreign key (StudentID) references Student(StudentUTDID),
    constraint ProfessorIDFK foreign key (ProfessorID) references Professor(ProfessorUTDID),
    constraint QuestionBankIDFK foreign key (QuestionBankID) references QuizBank(QuestionBankID),
    constraint QuizIDFK foreign key (QuizID) references Quiz(QuizID),
)

create table Password(
    Password_ varchar(255) not null,
    DateAndTime datetime,
    ClassID int unique,
    constraint ClassIDFK foreign key (ClassID) references Course(CourseID),
)
