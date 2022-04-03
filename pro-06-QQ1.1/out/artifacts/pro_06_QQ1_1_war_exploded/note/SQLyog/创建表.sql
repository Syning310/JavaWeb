
# 用户登录信息表
CREATE TABLE `user_basic` (
	id INT PRIMARY KEY AUTO_INCREMENT,
	loginId CHAR(11) UNIQUE NOT NULL,
	nickName VARCHAR(32) NOT NULL DEFAULT ' ',
	pwd CHAR(32) NOT NULL,
	headImg VARCHAR(255)NOT NULL) ENGINE=INNODB CHARSET=utf8;
	
SELECT * FROM user_basic;
ALTER TABLE user_basic MODIFY loginId VARCHAR(32) UNIQUE NOT NULL;
DESC user_basic;
SHOW CREATE TABLE user_basic;



# 用户详情信息表
CREATE TABLE `user_detail` (
	id INT PRIMARY KEY AUTO_INCREMENT,
	realName VARCHAR(32) NOT NULL,
	tel CHAR(11) NOT NULL DEFAULT ' ',
	email VARCHAR(255) NOT NULL DEFAULT ' ',
	`birth` DATE,
	`star` VARCHAR(32) NOT NULL DEFAULT ' ') ENGINE=INNODB CHARSET=utf8;
	
SELECT * FROM user_detail;
ALTER TABLE user_detail MODIFY birth DATE NOT NULL;






# 话题信息表
CREATE TABLE topic ( 
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`title` VARCHAR(255) NOT NULL,
	`content` VARCHAR(2555) NOT NULL DEFAULT ' ',
	`topicDate` DATETIME NOT NULL,
	`author` INT NOT NULL)ENGINE=INNODB CHARSET=utf8;

SELECT * FROM topic;







# 回复信息表
CREATE TABLE reply (
	id INT PRIMARY KEY AUTO_INCREMENT,
	content VARCHAR(2555) NOT NULL,
	replyDate DATE,
	author INT NOT NULL,
	topic INT NOT NULL)ENGINE=INNODB CHARSET=utf8;
	
SELECT * FROM reply;
ALTER TABLE reply MODIFY replyDate DATE NOT NULL; 







# 话题主人回复信息表
CREATE TABLE host_reply (
	id INT PRIMARY KEY AUTO_INCREMENT,
	content VARCHAR(2555) NOT NULL,
	hostReply DATE NOT NULL,
	author INT NOT NULL,
	reply INT NOT NULL) ENGINE=INNODB CHARSET=utf8;

SELECT * FROM host_reply;






# 好友列表
CREATE TABLE `friend` (
	id INT PRIMARY KEY AUTO_INCREMENT,
	uid INT NOT NULL,
	fid INT NOT NULL)ENGINE=INNODB CHARSET=utf8;

SELECT * FROM friend;

