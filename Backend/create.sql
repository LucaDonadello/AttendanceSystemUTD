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
    AnswerSet varchar(255) not null,
    QuestionID int unique,
    CorrectAnswer int not null,
    NumberOfOptions int not null,
    constraint QuizQuestionPK primary key (QuestionID)
);

create table StudentAnswer(
    StudentAnswerID int unique,
    StudentAnswer varchar(255) not null,
    QuestionID int unique,
    constraint StudentAnswerPK primary key (StudentAnswerID),
    constraint QuestionIDFK foreign key (QuestionID) references QuizQuestion(QuestionID)
    on delete cascade on update cascade
);

create table QuizBank(
    QuestionBankID int unique,
    QuestionAnswerSet int not null,
    CourseID int unique,
    QuizQuestionID int unique,
    constraint QuizBankPK primary key (QuestionBankID),
    constraint QuizQuestionIDFK foreign key (QuizQuestionID) references QuizQuestion(QuestionID)
    on delete cascade on update cascade
);

create table Quiz(
    QuizID int unique,
    QuizBankID int unique,
    StudentUTDID int unique,
    NumberOfQuestions int not null,
    Duration time not null,
    DateAndTime datetime,
    StudentAnswers int,
    constraint QuizPK primary key (QuizID),
    constraint QuizBankIDFK foreign key (QuizBankID) references QuizBank(QuestionBankID)
    on delete cascade on update cascade,
    constraint StudentIDFKQuiz foreign key (StudentUTDID) references Student(StudentUTDID)
    on delete cascade on update cascade
);

create table Course(
    CourseID int primary key not null unique,
    ClassName varchar(255) not null,
    StudentUTDID int unique,
    ProfessorUTDID int unique,
    QuestionBankID int unique,
    QuizID int unique,
    constraint StudentIDFKCourse foreign key (StudentUTDID) references Student(StudentUTDID)
    on delete cascade on update cascade,
    constraint ProfessorIDFK foreign key (ProfessorUTDID) references Professor(ProfessorUTDID)
    on delete cascade on update cascade,
    constraint QuestionBankIDFK foreign key (QuestionBankID) references QuizBank(QuestionBankID)
    on delete cascade on update cascade,
    constraint QuizIDFK foreign key (QuizID) references Quiz(QuizID)
    on delete cascade on update cascade
);

create table CourseDateTime(
    CourseDateTimeID int primary key unique,
    CourseID int unique,
    StartDate date not null,
    EndDate date not null,
    StartTime time not null,
    EndTime time not null,
    constraint CourseIDFKCDT foreign key (CourseID) references Course(CourseID)
);



create table Attendance(
    Attended boolean not null,
    MACID varchar(255) not null,
    IPAddress varchar(255) not null,
    StudentUTDID int unique,
    CourseID int unique,
    DateAndTime datetime,
    constraint StduentID primary key (StudentUTDID),
    constraint StudentIDFK foreign key (StudentUTDID) references Student(StudentUTDID)
    on delete cascade on update cascade,
    constraint CourseIDFK foreign key (CourseID) references Course(CourseID)
    on delete cascade on update cascade
);

create table Password(
    Password_ varchar(255) not null,
    DateAndTime datetime,
    ClassID int unique,
    constraint ClassIDFK foreign key (ClassID) references Course(CourseID)
    on delete cascade on update cascade
);
