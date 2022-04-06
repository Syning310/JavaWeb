



# 创建书表，有销量，库存
CREATE TABLE `book` (id INT PRIMARY KEY AUTO_INCREMENT,
	bookImg VARCHAR(255) NOT NULL,
	bookName VARCHAR(255) NOT NULL,
	price DOUBLE NOT NULL DEFAULT 0,
	author VARCHAR(32) NOT NULL DEFAULT '',
	saleCount INT NOT NULL DEFAULT 0,
	bookCount INT NOT NULL DEFAULT 0);
ALTER TABLE `book` ADD `status` ENUM('上架', '下架');


SELECT * FROM book;



 
# 添加记录
INSERT  INTO `book`(`id`,`bookImg`,`bookName`,`price`,`author`,`saleCount`,`bookCount`) VALUES 
(NULL,'cyuyanrumenjingdian.jpg','C语言入门经典',99.00,'亚历山大',8,197),
(NULL,'santi.jpg','三体',48.95,'刘慈欣',18,892),
(NULL,'ailuntulingzhuan.jpg','艾伦图灵传',50.00,'刘若英',12,143),
(NULL,'bainiangudu.jpg','百年孤独',40.00,'马尔克斯',3,98),
(NULL,'biancheng.jpg','边城',30.00,'狄更斯',2,99),
(NULL,'jieyouzahuodian.jpg','解忧杂货店',27.00,'东野圭吾',5,100),
(NULL,'zhongguozhexueshi.jpg','中国哲学史',45.00,'冯友兰',3,100),
(NULL,'huranqiri.jpg','忽然七日',19.00,'劳伦',50,200),
(NULL,'sudongpozhuan.jpg','苏东坡传',20.00,'林语堂',50,300),
(NULL,'fusang.jpg','扶桑',20.00,'严歌岑',10,89),
(NULL,'geihaizideshi.jpg','给孩子的诗',23.00,'北岛',5,99); 
 
UPDATE `book` SET STATUS='上架';

