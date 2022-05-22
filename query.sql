
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
	`member_num`	INT	NOT NULL AUTO_INCREMENT,
	`member_id`	VARCHAR(10)	NOT NULL,
	`member_pwd`	VARCHAR(10)	NOT NULL,
	`member_grade`	ENUM("ADMIN", "USER")	NOT NULL,
    PRIMARY KEY(member_num)
);



CREATE TABLE `post` (
	`post_num`	INT	NOT NULL AUTO_INCREMENT,
	`member_num`	INT	NOT NULL,
	`post_title`	VARCHAR(30)	NOT NULL,
	`post_content`	VARCHAR(300)	NOT NULL,
	`created_date`	DATETIME	NOT NULL,
	`modified_date`	DATETIME	NULL,
    `delete_check` 	BOOLEAN  DEFAULT 0	NOT NULL,
    PRIMARY KEY (post_num),
    FOREIGN KEY (member_num) REFERENCES member(member_num) ON DELETE CASCADE
);



CREATE TABLE `comment` (
	`comment_num`	INT	NOT NULL AUTO_INCREMENT,
	`post_num`	INT	NOT NULL,
	`member_num`	INT	NOT NULL,
	`comment_content`	VARCHAR(100)	NOT NULL,
	`comment_date`	DATETIME	NOT NULL,
    PRIMARY KEY (comment_num),
	FOREIGN KEY (member_num) REFERENCES member(member_num) ON DELETE CASCADE,
	FOREIGN KEY (post_num) REFERENCES post(post_num) ON DELETE CASCADE
);

INSERT INTO member ( member_id, member_pwd, member_grade
) VALUES 
	("admin", "adminadmin", "ADMIN")
	, ("user", "useruser", "USER");
	
        
SELECT * FROM post;
SELECT * FROM JdbcStudents;