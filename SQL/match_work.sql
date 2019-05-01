DROP TABLE IF EXISTS `match_work`;
CREATE TABLE `match_work` (
  `work_id`   INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '作品id',
  `work_name` VARCHAR(255) NOT NULL
  COMMENT '作品名称',
  `yytg1`     INT(2)                DEFAULT NULL,
  `kszx1`     INT(2)                DEFAULT NULL,
  `khfw1`     INT(2)                DEFAULT NULL,
  `yytg2`     INT(2)                DEFAULT NULL,
  `kszx2`     INT(2)                DEFAULT NULL,
  `khfw2`     INT(2)                DEFAULT NULL,
  `yytg3`     INT(2)                DEFAULT NULL,
  `kszx3`     INT(2)                DEFAULT NULL,
  `khfw3`     INT(2)                DEFAULT NULL,
  `yytg4`     INT(2)                DEFAULT NULL,
  `kszx4`     INT(2)                DEFAULT NULL,
  `khfw4`     INT(2)                DEFAULT NULL,
  `yytg5`     INT(2)                DEFAULT NULL,
  `kszx5`     INT(2)                DEFAULT NULL,
  `khfw5`     INT(2)                DEFAULT NULL,
  `score`     INT(2)                DEFAULT NULL,
  PRIMARY KEY (`work_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
