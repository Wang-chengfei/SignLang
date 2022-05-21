/*
数据来源：https://shouyu.bmcx.com/
datasource:
    username: signlang
    password: signlang123456
    url: jdbc:mysql://bewcf.info:3306/signlang?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
    driver-class-name: com.mysql.cj.jdbc.Driver
*/


-- ----------------------------
-- 用户
-- ----------------------------
create table `user`
(
    `id`          int primary key auto_increment,
    `openid`      varchar(255),
    `session_key` varchar(255),
);


-- ----------------------------
-- 词典
-- ----------------------------
create table `dictionary`
(
    `id`           int primary key auto_increment,
    `name`         varchar(30),
    `description`  varchar(255),
    `total_number` int, -- 词典总词数
	`img_url``     varchar(255)
);


-- ----------------------------
-- 单词
-- ----------------------------
create table `word`
(
    `id`            int primary key auto_increment,
    `type`          varchar(30),  -- 单词分类
    `answer`        varchar(30),  -- 单词名（正确答案）
    `answer2`       varchar(30),  -- 错误答案2
    `answer3`       varchar(30),  -- 错误答案3
    `answer4`       varchar(30),  -- 错误答案4
    `description`   varchar(255), -- 单词解释
    `img_url`       varchar(255), -- 单词图片
    `dictionary_id` int,          -- 单词所属词典，1初级 2中级 3高级
    foreign key (`dictionary_id`) references `dictionary` (`id`)
);


-- ----------------------------
-- 计划
-- ----------------------------
create table `plan`
(
    `id`             int primary key auto_increment,
    `user_id`        int,
    `dictionary_id`  int,                -- 计划对应词典
    `amount`         int,                -- 每日学习单词数
    `p_order`        int  default 1,     -- 学习顺序，1顺序 2倒序 3乱序
    `start_time`     date,               -- 计划开始时间
    `completed`      bool default false, -- 计划是否完成
    `total_number`   int,                -- 计划总单词数
    `learned_number` int  default 0,     -- 已学习单词数
    `state`          bool default false, -- 计划是否在进行中 true进行中 false未进行
	`last_time`		 data,				 -- 上次学习的时间
	`today_amount`   int,				 -- 今天学习的单词数
    foreign key (`user_id`) references `user` (`id`),
    foreign key (`dictionary_id`) references `dictionary` (`id`)
);


-- ----------------------------
-- 计划中的单词
-- ----------------------------
create table `plan_word`
(
    `id`         int primary key auto_increment,
    `plan_id`    int,
    `word_id`    int,
    `completed`  bool default false,
    `study_time` date, -- 单词学习时间
	`is_star`    bool default false,
	`is_mistake` bool default false,
    foreign key (`plan_id`) references `plan` (`id`),
    foreign key (`word_id`) references `word` (`id`)
);


-- ----------------------------
-- 错词本
-- ----------------------------
create table `mistake_word`
(
    `id`      int primary key auto_increment,
    `word_id` int,
    `user_id` int,
    foreign key (`word_id`) references `word` (`id`),
    foreign key (`user_id`) references `user` (`id`)
);


-- ----------------------------
-- 收藏单词本
-- ----------------------------
create table `star_word`
(
    `id`      int primary key auto_increment,
    `word_id` int,
    `user_id` int,
    foreign key (`word_id`) references `word` (`id`),
    foreign key (`user_id`) references `user` (`id`)
);


-- ----------------------------
-- 打卡记录
-- ----------------------------
create table `card`
(
    `id`         int primary key auto_increment,
    `user_id`    int,
    `clock_time` date,
    `amount`     int,
	`completed`  bool default true, -- 是否完成
    foreign key (`user_id`) references `user` (`id`)
);


-- ----------------------------
-- 文章
-- ----------------------------
create table `article`
(
    `id`        int primary key auto_increment,
    `type`      varchar(30),
    `title`     varchar(255),
    `abstract`  varchar(500),
    `content`   varchar(4000),
    `label`     varchar(30),
    `img_url`   varchar(255),
    `video_url` varchar(255)
);


-- ----------------------------
-- 收藏文章
-- ----------------------------
create table `star_article`
(
    `id`         int primary key auto_increment,
    `user_id`    int,
    `article_id` int
);


insert into `user` values (1000, 'wcf', 'xxxxxxxxxxxxx', 'xxxxxxxxxxxxxxxx', 'xxx.jpg', '1');
insert into `user` values (1001, 'hjh', 'xxxxxxxxxxxxx', 'xxxxxxxxxxxxxxxx', 'xxx.jpg', '1');

insert into `dictionary` values(1, '初级词典', '初级词典初级词典', 0);
insert into `dictionary` values(2, '中级词典', '中级词典中级词典', 0);
insert into `dictionary` values(3, '高级词典', '高级词典高级词典', 0);

select count(*) from word
where dictionary_id = 1;

select count(*) from word
where dictionary_id = 2;

select count(*) from word
where dictionary_id = 3;