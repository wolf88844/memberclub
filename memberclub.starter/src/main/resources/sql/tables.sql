SET MODE MYSQL;
CREATE TABLE member_perform_his (
    id BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '表自增主键',
    biz_type INT(11)  NOT NULL COMMENT '产品线',
    user_id BIGINT(20)  NOT NULL COMMENT 'userId',
    order_system_type INT(11)  NOT NULL COMMENT '订单  id',
    order_id VARCHAR(128)  NOT NULL COMMENT '订单  id',
    trade_id VARCHAR(128)  NOT NULL COMMENT '交易 id',
    sku_id BIGINT(20)  NOT NULL COMMENT 'skuId',
    extra TEXT NOT NULL COMMENT '扩展属性',
    stime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '开始时间',
    etime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '截止时间',
    utime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '更新时间',
    ctime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uniq_1 (user_id, trade_id, sku_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;