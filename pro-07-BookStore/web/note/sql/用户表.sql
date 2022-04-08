
# 查询隔离级别和查询引擎
SELECT @@tx_isolation;
SHOW ENGINES;


# 创建用户表
CREATE TABLE `user` 
	(`id` INT, `name` VARCHAR(32) NOT NULL,
	 `pwd` CHAR(11) NOT NULL, `email` VARCHAR(255) NOT NULL, `role` INT NOT NULL, PRIMARY KEY(id));
ALTER TABLE `user` MODIFY `pwd` CHAR(32);
ALTER TABLE `user` MODIFY `id` INT AUTO_INCREMENT;

SELECT * FROM `user`;
 
DESC `user`;


# 1、根据用户名 密码返回对象
SELECT id, NAME, pwd, email, role FROM `user` WHERE NAME='宁' AND pwd=MD5(12);


INSERT INTO `user` VALUES (NULL, '宁', MD5('ok'), 'syning310@outlook.com', 1);
INSERT INTO `user` VALUES (NULL, '月', MD5('ok'), 'yueyue216outlook.com', 0);

INSERT INTO `user` VALUES (NULL, '颜', MD5('ok'), 'hongyan@outlook.com', 0);

