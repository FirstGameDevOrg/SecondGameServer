# FirstGameServer
游戏服务器

java版本：jdk-18.0.2

redis命令
```java
//启动redis
redis-server
//启动客户端
redis-cli.exe -h 127.0.0.1 -p 6379

//列出所有的key
keys *

//key所储存的值的类型
type key

//查询key对应的值
get key
```

数据库相关：

用户信息表：
```java
CREATE TABLE `user` (
`user_id` bigint(20) NOT NULL AUTO_INCREMENT,
`user_name` varchar(255) NOT NULL,
`pass_word` varchar(255) NOT NULL,
`sex` int(11) NOT NULL,
`email_address` varchar(255) DEFAULT NULL,
`phone` varchar(20) DEFAULT NULL,
PRIMARY KEY (`user_id`),
UNIQUE KEY `users_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8
```

好友关系表：
```java
CREATE TABLE userFriends (
id INT AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
friend_id BIGINT NOT NULL,
friend_nickname VARCHAR(255),
status TinyInt default 0 COMMENT '0 拒绝/删除 1接受',
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) REFERENCES user(user_id),
FOREIGN KEY (friend_id) REFERENCES user(user_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;
```
