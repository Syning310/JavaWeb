
DESC user_basic;

# 添加用户登录 数据
INSERT INTO user_basic VALUES(NULL, '1031887546', '翁先生', MD5('syning0310'), 'D:\图片\慢慢来\自拍.jpg');
INSERT INTO user_basic VALUES
	(NULL, '569903107', '蔡先生', MD5('123456'), 'D:\图片\慢慢来\自拍.jpg'),
	(NULL, '1365548272', '洪先生', MD5('123456'), 'D:\图片\慢慢来\自拍.jpg'),
	(NULL, '1366851462', '许先生', MD5('123456'), 'D:\图片\慢慢来\自拍.jpg'),
	(NULL, '1954465135', '雷先生', MD5('123456'), 'D:\图片\慢慢来\自拍.jpg');


# 根据好友登录表的主键，添加好友列表数据
INSERT INTO friend (uid, fid) VALUES(1, 2), (1, 3), (1, 4), (1, 5);
INSERT INTO friend (uid, fid) VALUES(2, 1), (2, 3), (2, 4), (2, 5);
INSERT INTO friend (uid, fid) VALUES(3, 1), (3, 2), (3, 4), (3, 5);
INSERT INTO friend (uid, fid) VALUES(4, 1), (4, 2), (4, 3), (4, 5);
INSERT INTO friend (uid, fid) VALUES(5, 1), (5, 2), (5, 3), (5, 4);

SELECT * FROM friend;

# 根据 uid和fid 找到 user_basic 互相对应的好友
SELECT u1.nickName, u2.nickName FROM user_basic u1 LEFT JOIN friend 
	ON u1.id = friend.uid 
	INNER JOIN user_basic u2 ON u2.id = friend.fid;

# 2、传入 ? nickName字段，返回它的好友
SELECT u2.nickName FROM user_basic u1 LEFT JOIN friend
	ON u1.id = friend.uid
	INNER JOIN user_basic u2 ON u2.id = friend.fid WHERE u1.loginId = '1031887546';


# 1、根据账号和密码获取特定用户信息
SELECT * FROM user_basic WHERE loginId = '1031887546' AND pwd = MD5('syning0310');



# 










