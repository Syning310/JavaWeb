


DESC `order`;

# 创建订单表
CREATE TABLE `order` (id INT PRIMARY KEY AUTO_INCREMENT,
	`orderNo` VARCHAR(255) NOT NULL UNIQUE,
	orderDate DATETIME NOT NULL, 
	orderUser INT NOT NULL,
	orderMoney DOUBLE NOT NULL, 
	orderStatus ENUM('未发货', '已发货', '已收货'));

SELECT * FROM `order`;

ALTER TABLE `order` MODIFY orderStatus ENUM('未发货', '已发货', '已收货', '未付款');





