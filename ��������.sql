#向user用户表插入数据
INSERT INTO USER(user_no,user_realname,user_password,user_nickname,user_sex,user_phone,user_type,user_photo,
user_job,user_address_province,user_address_city,user_mood,user_mail,user_introduct,user_birthday,user_idcard) 
VALUE('131006326','王韶辉','123456','哈哈哈','男','130xxxx8112',1,'g:\\1.png','学生','江苏省','苏州市',
'今天天气不错','123456@qq.com','专业敲代码','1993-08-06','410802xxxxxxxxxx10')
INSERT INTO USER(user_no,user_realname,user_password,user_nickname,user_sex,user_phone,user_type,user_photo,
user_job,user_address_province,user_address_city,user_mood,user_mail,user_introduct,user_birthday,user_idcard) 
VALUE('131007136','张丰鸽','123456','哈哈哈','女','159xxxx8112',1,'g:\\1.png','学生','江苏省','苏州市',
'今天天气不美','123456@qq.com','专业UI','1993-08-06','410202xxxxxxxxxx25')

#向user_hobby爱好表插入数据
INSERT INTO hobby(hobby_name) VALUE('打篮球');
INSERT INTO hobby(hobby_name) VALUE('踢足球');
INSERT INTO hobby(hobby_name) VALUE('打羽毛球');
#向user_hobby用户爱好关系表插入数据
INSERT INTO user_hobby(user_id,hobby_id) VALUE(1,1);
INSERT INTO user_hobby(user_id,hobby_id) VALUE(1,2);
INSERT INTO user_hobby(user_id,hobby_id) VALUE(1,3);

#向user_linkman常用联系人信息表插入数据
INSERT INTO user_linkman(user_id,NAME,idcard) VALUE(1,'张丰鸽','410202xxxxxxxxxx25')

#向friend好友表插入数据
INSERT INTO friend(user_id,friend_id) VALUE(1,2)

#向message聊天消息表插入数据
INSERT INTO message(message_sender_id,message_receiver_id,message_time,message_content,message_image,message_isread)
 VALUE(1,2,'2016-4-13','hello','g:\\1.png','01')

#向news说说表插入数据
INSERT INTO news(news_sender_id,news_content,news_time,news_stars,news_views) 
VALUE(1,'今天起得真早啊！！！','2016-4-13',2,10)

#向news_photo说说表插入数据
INSERT INTO news_photo(news_id,news_path) VALUE(1,'g:\\1.png')

#向comment评论表插入数据
INSERT INTO COMMENT(comment_news_id,comment_user_id,comment_content,comment_time) 
VALUE(1,2,'是啊，你起得真早！','2016-04-13')

#向reply回复表插入数据
INSERT INTO reply(reply_comment_id,reply_user_id,reply_respondent_id,reply_content,reply_time) 
VALUE(1,1,2,'哈哈哈','2016-04-13')

#向user_wallet钱包表插入数据
INSERT INTO user_wallet(user_id,alipay_no,user_wallet_score,user_wallet_money) 
VALUE(1,'123456789',1000,99999999.00)

#向system_message系统消息表插入数据
INSERT INTO system_message(user_id,system_message_content,system_message_linkorder,system_message_linkuser) 
VALUE(1,'您有一条新的订单',1,2)
 
#向house房源信息表插入数据
INSERT INTO house(user_id,house_title,house_describe,house_style,house_address_province,house_address_city,
house_traffic,house_most_num,house_one_price,house_add_price,house_limit_sex,house_stay_time,house_assess_sum)
VALUE(1,'苏州豪华别墅','靠近学习场所','别墅','江苏省','苏州市','交通便利',5,50.00,10.00,'不限',5,2)

#向house_photo房源图片表插入数据
INSERT INTO house_photo(house_id,house_photo_path) VALUE(1,'g:\\1.png')

#向house_collect收藏房源表插入数据
INSERT INTO house_collect(user_id,house_id) VALUE(1,1)

#向house_date_manage日期管理表插入数据
INSERT INTO house_date_manage(house_id,date_not_use) VALUE(1,'2016-4-14')
DELETE FROM house_date_manage WHERE house_id= 1 AND date_not_use="2016-06-25";
SELECT * FROM house_date_manage WHERE house_id = 2;

#向house_equipment房屋设施表插入数据
INSERT INTO equipment(equipment_name) VALUE('空调');
INSERT INTO equipment(equipment_name) VALUE('电视机');
INSERT INTO equipment(equipment_name) VALUE('冰箱');

#向house_equipment房屋设施表插入数据
INSERT INTO house_equipment(house_id,equipment_id) VALUE(1,1);
INSERT INTO house_equipment(house_id,equipment_id) VALUE(1,2);
INSERT INTO house_equipment(house_id,equipment_id) VALUE(1,3);

#向hot_city热门城市表插入数据
INSERT INTO hot_city(hot_city_name,hot_city_num) VALUE('苏州',35)
INSERT INTO hot_city(hot_city_name,hot_city_num) VALUE('上海',54)

#向orders订单表插入数据
INSERT INTO orders(house_id,user_id,checknum,checkname,checktime,leavetime,total,tel,order_state) 
VALUE(1,1,4,'王韶辉','2016-04-13','2016-04-16',100.00,'130xxxx4954','待确认')
DELETE FROM orders WHERE order_id=1

#向user_order_linkman订单使用联系人信息表插入数据
INSERT INTO user_order_linkman(order_id,NAME,idcard) VALUE(2,'王韶辉','410802xxxxxxxxxx10')

#向assess评价表表插入数据
INSERT INTO assess(order_id,house_id,user_id,star,assess_content) VALUE(2,1,1,4,'总体来说还不错！');

#向checks签到表插入数据
INSERT INTO checks(user_id,check_sum,check_last_time) VALUE(1,2,'2016-04-13')

#向admin管理员表插入数据
INSERT INTO admin(admin_name,admin_password) VALUE('admin','admin')

