DROP TABLE IF EXISTS `match_user`;
CREATE TABLE `match_user` (
  `system_id`   INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '用户id',
  `user_id`     CHAR(20)     NOT NULL
  COMMENT '用户的邮箱',
  `password`    VARCHAR(255) NOT NULL
  COMMENT '密码',
  `leader_name` VARCHAR(255)          DEFAULT NULL
  COMMENT '队长姓名',
  `major`       VARCHAR(255)          DEFAULT NULL
  COMMENT '专业',
  `college`     VARCHAR(255)          DEFAULT NULL
  COMMENT '学校',
  `phone`       VARCHAR(255)          DEFAULT NULL
  COMMENT '电话',
  `type`        CHAR(1)      NOT NULL
  COMMENT '用户类型',
  `grade`       VARCHAR(255)          DEFAULT NULL
  COMMENT '年级',
  `work_id`     INT(20)               DEFAULT NULL
  COMMENT '作品编号',
  `member1`     VARCHAR(255)          DEFAULT NULL
  COMMENT '成员1',
  `member2`     VARCHAR(255)          DEFAULT NULL
  COMMENT '成员2',
  PRIMARY KEY (`system_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;
