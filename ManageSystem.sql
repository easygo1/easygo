CREATE DATABASE teacher;
CREATE TABLE Student(
	stu_id INT PRIMARY KEY AUTO_INCREMENT,
	stu_no INT NOT NULL UNIQUE,
	PASSWORD VARCHAR(20),
	stu_NAME VARCHAR(10) NOT NULL,
	stu_sex CHAR(2) NOT NULL,
	stu_birthday DATE,
	computer_score SMALLINT,
	english_score SMALLINT,
	math_score SMALLINT
);
CREATE TABLE teacher(
	teacher_id INT PRIMARY KEY AUTO_INCREMENT,
	teacher_no INT NOT NULL UNIQUE,
	teacher_name VARCHAR(20) NOT NULL,
	PASSWORD VARCHAR(20) NOT NULL
);
INSERT INTO Student(stu_no,PASSWORD,stu_NAME,stu_sex,stu_birthday,computer_score,english_score,math_score) 
VALUES(?,?,?,?,?,?,?,?);
INSERT INTO teacher(teacher_no,teacher_name,PASSWORD) VALUES(123,'韩老师','123'); 
SELECT 