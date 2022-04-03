

DESC host_reply;
# 添加主人回复
INSERT INTO host_reply VALUES 
	(NULL, '我也是天线宝宝1', NOW(), 1, 18),
	(NULL, '我也是天线宝宝2', NOW(), 1, 18),
	(NULL, '了解了解', NOW(), 1, 19);


SELECT * FROM host_reply;

ALTER TABLE host_reply CHANGE hostReply hostReplyDate DATETIME NOT NULL;
ALTER TABLE host_reply CHANGE hostReplyDate hostReplyDate DATETIME NOT NULL;



INSERT INTO host_reply VALUES (NULL, '好的收到', NOW(), 1, 26);



DELETE FROM host_reply;


INSERT INTO host_reply VALUES (NULL, '嗯嗯', NOW(), 1, 22);

