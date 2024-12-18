SET MODE MYSQL;
CREATE TABLE IF NOT EXISTS member_perform_his (
    id BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '表自增主键',
    biz_type INT(11)  NOT NULL COMMENT '产品线',
    user_id BIGINT(20)  NOT NULL COMMENT 'userId',
    order_system_type INT(11)  NOT NULL COMMENT '订单系统类型',
    order_id VARCHAR(128)  NOT NULL COMMENT '订单  id',
    trade_id VARCHAR(128)  NOT NULL COMMENT '交易 id',
    perform_his_token VARCHAR(128)  NOT NULL COMMENT '履约单 token',
    sku_id BIGINT(20)  NOT NULL COMMENT 'skuId',
    buy_count INT(11)  NOT NULL COMMENT '购买数量',
    status INT(11)  NOT NULL COMMENT '状态',
    extra TEXT NOT NULL COMMENT '扩展属性',
    stime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '开始时间',
    etime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '截止时间',
    utime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '更新时间',
    ctime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uniq_perform_his (user_id, trade_id, sku_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;


CREATE TABLE IF NOT EXISTS member_perform_item (
    id BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '表自增主键',
    biz_type INT(11)  NOT NULL COMMENT '产品线',
    user_id BIGINT(20)  NOT NULL COMMENT 'userId',
    trade_id VARCHAR(128)  NOT NULL COMMENT '交易 id',
    sku_id BIGINT(20)  NOT NULL COMMENT 'skuId',
    right_id INT(11)  NOT NULL COMMENT '权益Id',
    right_type INT(11)  NOT NULL COMMENT '权益类型',
    asset_count INT(11)  NOT NULL COMMENT '资产数量',
    grant_type INT(11)  NOT NULL COMMENT '发放类型,直发 ,激活',
    batch_code VARCHAR(128)  NOT NULL COMMENT '发放批次码',
    phase INT(11)  NOT NULL COMMENT '期数',
    cycle INT(11)  NOT NULL COMMENT '周期数',
    buy_index INT(11)  NOT NULL COMMENT '购买序号',
    status INT(11)  NOT NULL COMMENT '状态',
    extra TEXT NOT NULL COMMENT '扩展属性',
    stime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '开始时间',
    etime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '截止时间',
    utime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '更新时间',
    ctime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uniq_perform_item (user_id, trade_id, batch_code)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ;


CREATE TABLE IF NOT EXISTS member_order (
    id BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '表自增主键',
    biz_type INT(11)  NOT NULL COMMENT '产品线',
    user_id BIGINT(20)  NOT NULL COMMENT 'userId',
    order_system_type INT(11)  NOT NULL COMMENT '订单系统类型',
    order_id VARCHAR(128)  NOT NULL COMMENT '订单  id',
    trade_id VARCHAR(128)  NOT NULL COMMENT '交易 id',
    user_info TEXT NOT NULL COMMENT '用户属性',
    sku_details TEXT NOT NULL COMMENT '购买商品信息',
    act_price_fen VARCHAR(128)  NOT NULL COMMENT '实付金额',
    origin_price_fen VARCHAR(128)  NOT NULL COMMENT '原价金额',
    status INT(11)  NOT NULL COMMENT '状态',
    extra TEXT NOT NULL COMMENT '扩展属性',
    stime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '开始时间',
    etime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '截止时间',
    utime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '更新时间',
    ctime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uniq_member_order (user_id, trade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;