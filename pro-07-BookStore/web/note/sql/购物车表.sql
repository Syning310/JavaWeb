




# 购物车记录表
CREATE TABLE shopping_item (id INT PRIMARY KEY AUTO_INCREMENT, 
	`book_id` INT NOT NULL, buyCount INT NOT NULL DEFAULT 0,
	userBean INT NOT NULL, 
	FOREIGN KEY (`book_id`) REFERENCES `book`(id), 
	FOREIGN KEY (userBean) REFERENCES `user`(id));
RENAME TABLE shopping_cart TO shopping_item;


SELECT * FROM shopping_item;





INSERT INTO shopping_item VALUES 
	(NULL, 4, 1, 1), (NULL, 1, 2, 1), (NULL, 10, 6, 1);





