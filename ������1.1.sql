
#用户表
CREATE TABLE USER(
user_id        INT PRIMARY KEY AUTO_INCREMENT,  #用户ID
user_no        VARCHAR(30) NOT NULL UNIQUE,     #用户账号（唯一）
user_realname  VARCHAR(20),            #用户真实姓名
user_password  VARCHAR(20) NOT NULL,   #用户密码
user_nickname  VARCHAR(20),            #用户昵称
user_sex      CHAR(2),                 #性别'男'，'女'
user_phone    VARCHAR(15),             #手机号11位
user_type     TINYINT NOT NULL,        #用户类型1'房客',2'房东'
user_photo    VARCHAR(100),            #头像//存地址
user_job      VARCHAR(10),             #职业
user_address_province   VARCHAR(20),   #所在省份
user_address_city   VARCHAR(20),       #所在城市
user_mood     VARCHAR(30),             #个性签名15字内
user_mail     VARCHAR(30),             #邮箱
user_introduct VARCHAR(100),           #个人简介
user_birthday	DATE,		       #出生日期
user_idcard	VARCHAR(18),	       #身份证号
token VARCHAR(200),		       #token
remarks VARCHAR(50)		       #备注
						
);
#爱好表（所有爱好
CREATE TABLE hobby(
hobby_id     INT PRIMARY KEY AUTO_INCREMENT,#爱好ID
hobby_name   VARCHAR(10)                   #爱好名称
);
#用户爱好关系表（一个用户多个爱好
CREATE TABLE user_hobby(
user_hobby_id     INT PRIMARY KEY AUTO_INCREMENT,
user_id           INT NOT NULL,           #用户ID(外键)
hobby_id          INT NOT NULL,           #爱好ID(外键)
FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (hobby_id) REFERENCES hobby(hobby_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#常用联系人信息表
CREATE TABLE user_linkman(
user_linkman_id  INT PRIMARY KEY AUTO_INCREMENT,	#表编号
user_id  INT NOT NULL,					#用户id
NAME    VARCHAR(20) NOT NULL,				#联系人的姓名
idcard	VARCHAR(18) NOT NULL,	                        #联系人身份证号
FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#好友表
CREATE TABLE friend (                      #（双方好友user_id和friend_id都要查）
friend_no    INT PRIMARY KEY AUTO_INCREMENT,#无意义
user_id       INT,                        #用户ID(外键)
friend_id     INT,                        #好友ID（外键）
FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (friend_id) REFERENCES USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#聊天消息表
CREATE TABLE message(
message_id           INT PRIMARY KEY AUTO_INCREMENT,
message_sender_id    INT NOT NULL,                 #发送人ID（外键）
message_receiver_id  INT NOT NULL,                 #接收者ID（外键）
message_time         DATETIME NOT NULL,            #发送时间
message_content      VARCHAR(200),                 #聊天内容
message_image        VARCHAR(100),                 #聊天图片地址
message_isread       CHAR(2),                      #消息状态；01未读，02已读
FOREIGN KEY (message_sender_id) REFERENCES USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (message_receiver_id) REFERENCES USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);			

#说说表news
CREATE TABLE news(
news_id  INT PRIMARY KEY AUTO_INCREMENT,   #说说动态id
news_sender_id INT,                        #发送人id
news_content   VARCHAR(300),               #发送内容
news_time      DATETIME,                   #发送时间
news_stars      INT ,                      #点赞次数
news_views      INT ,                      #浏览次数
FOREIGN KEY (news_sender_id) REFERENCES USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE 
);
#说说图片表
CREATE TABLE news_photo(
news_photo_id INT PRIMARY KEY AUTO_INCREMENT,
news_id  INT,
news_path VARCHAR(100),
FOREIGN KEY (news_id) REFERENCES news(news_id) ON DELETE CASCADE ON UPDATE CASCADE 
);
#评论表comments
CREATE TABLE COMMENT (
	comment_id INT PRIMARY KEY AUTO_INCREMENT,  #评论表id
	comment_news_id INT,                        #说说动态id
	comment_user_id INT,			     #评论人id
	comment_content VARCHAR (300),              #评论内容
	comment_time DATETIME,                      #评论时间
	FOREIGN KEY (comment_news_id) REFERENCES news (news_id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (comment_user_id) REFERENCES USER (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#回复表reply
CREATE TABLE reply (
	reply_id INT PRIMARY KEY AUTO_INCREMENT,     #回复表id
	reply_comment_id INT,			     #评论id
	reply_user_id INT,                           #回复者id
	reply_respondent_id INT,                     #被回复者id
	reply_content VARCHAR (300),                 #回复内容
	reply_time DATETIME,                         #回复时间
	FOREIGN KEY (reply_comment_id) REFERENCES COMMENT (comment_id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (reply_user_id) REFERENCES USER (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (reply_respondent_id) REFERENCES USER (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#钱包表
CREATE TABLE user_wallet
(
user_wallet_id    INT PRIMARY KEY AUTO_INCREMENT,#钱包id
user_id          INT NOT NULL,                           #用户id	 外键
alipay_no        VARCHAR(30),                           #支付宝号
user_wallet_score  INT,                          #积分
user_wallet_money  DOUBLE,                       #钱包余额
FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE	
);
#系统消息表
CREATE TABLE system_message(
system_message_id INT PRIMARY KEY AUTO_INCREMENT,
user_id INT NOT NULL,  				#系统信息接收者（外键）
system_message_content VARCHAR(20),		#系统发送的内容
system_message_linkorder INT,			#顾客购买的订单
system_message_linkuser	INT,		        #请求加为好友的用户
FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);




#房源信息表
CREATE TABLE house(
	house_id INT PRIMARY KEY AUTO_INCREMENT,#房子编号	主键
	user_id	       INT,			#房东用户编号	外键
	house_title    VARCHAR(60),		#房源标题(0-10字)
	house_describe VARCHAR(200),		#房屋描述（100字）
	house_style    VARCHAR(10),	        #房源类型（客厅沙发，独立房间）
	house_address_province   VARCHAR(20),   #所在省份
	house_address_city   VARCHAR(20),       #所在城市
	house_address_lng DOUBLE,               #房源地址（经度）
	house_address_lat DOUBLE,               #房源地址（纬度）
	house_traffic	VARCHAR(50),	        #交通信息		
	house_most_num	TINYINT,	        #最多入住人数
	house_one_price	DOUBLE,	                #价格（1人）
	house_add_price	DOUBLE,	                #每多一人的价格			
	house_limit_sex	VARCHAR(10),	        #房客性别要求（不限，只男，只女）
	house_stay_time	 TINYINT,               #最长入住时间
	house_assess_sum  INT DEFAULT 0,      #评价次数（在房东发布房源时不显示）
	FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#房源图片表
CREATE TABLE house_photo(
house_photo_id INT PRIMARY KEY AUTO_INCREMENT,
house_id INT NOT NULL,
house_photo_path VARCHAR(50),
isFirst		  SMALLINT(2)			  #是否为封面，0不存在，1存在
FOREIGN KEY (house_id) REFERENCES house(house_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#收藏房源表
CREATE TABLE house_collect(
house_collect_id  INT PRIMARY KEY AUTO_INCREMENT, #无意义
user_id           INT,                            #用户ID
house_id          INT                             #收藏房源ID
);
#日期管理表
CREATE TABLE house_date_manage
(	
	date_manage_id INT PRIMARY KEY AUTO_INCREMENT,#无意义
	house_id INT,                          #房源id（外键）
	date_not_use DATE                       #不可租日期，精确到天			 	
);
#房屋设施表  WIFI，电视，洗衣机，毛巾...
CREATE TABLE equipment(
equipment_id INT PRIMARY KEY AUTO_INCREMENT,#设施ID
equipment_name VARCHAR(20)                  #设施名称
);

#房屋与设施关系表(一个房源多个设施)WIFI，电视，洗衣机，毛巾...
CREATE TABLE house_equipment(
house_equipment_id INT PRIMARY KEY AUTO_INCREMENT,#无意义
house_id INT NOT NULL,                            #房源编号
equipment_id INT NOT NULL,                         #设施编号
FOREIGN KEY (house_id) REFERENCES house(house_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (equipment_id) REFERENCES equipment(equipment_id) ON DELETE CASCADE ON UPDATE CASCADE
); 



#热门城市表
CREATE TABLE hot_city(
hot_city_id INT PRIMARY KEY AUTO_INCREMENT,
hot_city_name  VARCHAR(20), #热门城市名
hot_city_num   INT          #热门城市订单完成次数
);



 #订单表
CREATE TABLE orders
(
	order_id INT PRIMARY KEY AUTO_INCREMENT,#订单编号
	house_id      INT,	                #房屋编号	外键
	user_id      INT,			#房客编号	外键
	checknum      INT,		        #入住人数
	checkname     VARCHAR(20),		#入住人姓名
	checktime     DATE,		        #入住时间（计算出天数）
	leavetime     DATE,	                #离开时间
	total         DOUBLE,			#订单总额（总价=天数*单价）
	tel	      CHAR(11),	                #联系方式
	order_state   VARCHAR(20),		#订单状态 #待确认，待付款，待入住，已完成，取消订单
FOREIGN KEY (house_id) REFERENCES house(house_id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#订单使用的联系人信息表
CREATE TABLE user_order_linkman(
user_linkman_id  INT PRIMARY KEY AUTO_INCREMENT,	#表编号
order_id  INT NOT NULL, 				#订单编号
NAME      VARCHAR(20) NOT NULL,
idcard     VARCHAR(18) NOT NULL,
FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE CASCADE
);
#评价表
 CREATE TABLE assess(
	assess_id INT PRIMARY KEY AUTO_INCREMENT, #无意义
	order_id  INT NOT NULL,                   #订单id（外键）
	house_id  INT NOT NULL,	                	  #房屋编号	外键
	user_id   INT NOT NULL,                   #用户id（外键）(房客，评价人的id)
	star      TINYINT,                        #星级
	assess_content   VARCHAR(100),            #评价内容
	FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);


#管理员表Admin
CREATE TABLE admin(
admin_id        INT PRIMARY KEY AUTO_INCREMENT,#管理员账号
admin_name      VARCHAR(20),                   #管理员名字
admin_password  VARCHAR(20)                    #管理员密码
);

#根据当前签到时间和上次签到时间来判断是否连签，
#如果是连签，连续签到次数+1，上次签到时间更新为当次签到时间
#如果不是连签，连续签到次数变为1，上次签到时间更新为当次时间；
#如果不是连签每次积分+2，连签但是次数小于7，每次加3，连签次数大于7小于30，每次+5，一个月以上连签每次+8
#签到表
CREATE TABLE checks(
	check_id INT PRIMARY KEY AUTO_INCREMENT, #签到id
	user_id         INT,                     #用户id
	check_sum       INT,                     #连续签到次数
	check_last_time DATE                     #上次签到时间
);