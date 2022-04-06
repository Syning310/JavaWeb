



# 订单详情表，如：买了哪本书，买了多少本，该订单详情属于哪个订单的
CREATE TABLE `order_details` (id INT PRIMARY KEY,
	book_id INT NOT NULL,
	buyCount INT NOT NULL,
	orderBean VARCHAR(255) NOT NULL);

DESC `order_details`;

SELECT * FROM `order_details`;









