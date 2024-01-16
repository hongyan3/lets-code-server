CREATE TABLE `user`
(
    `id`           bigint PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `user_name`    varchar(30)        NOT NULL COMMENT '用户名',
    `user_account` varchar(30)        NOT NULL COMMENT '用户账号',
    `user_avatar`  varchar(256)                DEFAULT NULL COMMENT '用户头像',
    `gender`       tinyint                     DEFAULT NULL COMMENT '性别 1-男 2-女 3-保密',
    `password`     varchar(256)       NOT NULL COMMENT '密码',
    `role`         tinyint(1) NOT NULL DEFAULT '1' COMMENT '角色 1-普通用户 2-管理员',
    `status`       tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户状态 0-禁用 1-正常',
    `create_time`  datetime           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`    tinyint(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除 0-删除 1-正常'
);

CREATE TABLE `question`
(
    `id`           bigint auto_increment primary key comment '题目ID',
    `title`        varchar(30)                        not null comment '标题',
    `description`  text comment '问题描述',
    `tags`         text comment '标签 Json数组',
    `answer`       text comment '标准答案',
    `submit_num`   int      default 0                 not null comment '总提交数',
    `access_num`   int      default 0                 not null comment '总通过数',
    `judge_case`   text comment '判题用例 Json数组',
    `judge_config` text comment '判题配置 Json',
    `user_id`      bigint comment '创建者ID',
    `create_time`  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time`  datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP not null comment '更新时间',
    `is_delete`    tinyint(1) default 1 comment '逻辑删除 0-删除 1-正常',
    index          idx_user_id (user_id)
);

CREATE TABLE IF NOT EXISTS `question_submit`
(
    `id`
    bigint
    auto_increment
    primary
    key
    comment
    '题目ID',
    `language`
    varchar
(
    30
) not null comment '编程语言',
    `code` text not null comment '提交代码',
    `judge_info` text comment '判题信息 Json',
    `status` int default 0 not null comment '判题状态 0-待判题 1-判题中 2-成功 3-失败',
    `question_id` bigint not null comment '题目ID',
    `user_id` bigint comment '创建者ID',
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP not null comment '更新时间',
    `is_delete` tinyint
(
    1
) default 1 comment '逻辑删除 0-删除 1-正常',
    index idx_user_id
(
    user_id
),
    index idx_question_id
(
    user_id
)
    ) comment '题目提交表';