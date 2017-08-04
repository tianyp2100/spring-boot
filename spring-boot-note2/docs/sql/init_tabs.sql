/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/8/1 15:34:32                            */
/*==============================================================*/


drop table if exists t1;

drop table if exists t2;

drop index Index_is_deleted on user_info;

drop table if exists user_info;

/*==============================================================*/
/* Table: t1                                                    */
/*==============================================================*/
create table t1
(
   id                   int unsigned not null auto_increment comment '主键',
   names                varchar(5) not null default '' comment '名称',
   phone                varchar(11) not null default '' comment '电话',
   primary key (id)
);

alter table t1 comment '测试表1';

/*==============================================================*/
/* Table: t2                                                    */
/*==============================================================*/
create table t2
(
   id                   int unsigned not null auto_increment comment '主键id',
   age                  tinyint unsigned not null default 0 comment '年龄',
   star                 tinyint unsigned not null default 0 comment '级别',
   primary key (id)
);

alter table t2 comment '测试表2';

/*==============================================================*/
/* Table: user_info                                             */
/*==============================================================*/
create table user_info
(
   id                   int unsigned not null auto_increment comment '主键',
   compellation         varchar(5) not null default '' comment '姓名',
   sex                  enum('Boy','Girl') not null default 'Girl' comment '性别(1男2女)',
   age                  tinyint unsigned not null default 18 comment '年龄',
   birthday             datetime not null default '0000-00-00 00:00:00' comment '生日',
   zodiac               enum('Rat','Ox','Tiger','Rabbit','Dragon','Snake','Horse','Goat','Money','Rooster','Dog','Pig') not null default 'Horse' comment '生肖',
   constellation        enum('Aries','Taurus','Gemini','Cancer','Leo','Virgo','Libra','Scorpio','Sagittarius','Capricornus','Aquarius','Pisces') not null default 'Capricornus' comment '星座',
   blood_type           enum('A','B','AB','O') not null default 'O' comment '血型',
   birthplace           int unsigned not null default 620000 comment '出生地',
   edu_degree           tinyint not null default 6 comment '教育程度（1:小学 2:初中 3:高中 4:中专 5:大专 6:本科 7:硕士 8:博士）',
   avatar               varchar(150) not null default 'http://loveshare.oss-cn-shanghai.aliyuncs.com/universal/image/avatar/1/0.png' comment '头像',
   about_me             varchar(500) not null default '' comment '自我介绍',
   gmt_create           timestamp not null default '0000-00-00 00:00:00' comment '创建时间',
   gmt_modified         timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   is_deleted           tinyint unsigned not null default 0 comment '是否已逻辑删除(0正常1删除)',
   primary key (id)
);

alter table user_info comment '用户简单信息表（注：表中随机数据只为简单测试使用！）';

/*==============================================================*/
/* Index: Index_is_deleted                                      */
/*==============================================================*/
create index Index_is_deleted on user_info
(
   is_deleted
);

