-- 用于单测使用的 H2 数据库初始化

SET MODE MYSQL;
CREATE TABLE IF NOT EXISTS member_sku (
    id BIGINT(20)  NOT NULL COMMENT '表主键',
    biz_type INT(11)  NOT NULL COMMENT '产品线',
    status INT(11)  NOT NULL COMMENT '商品状态',
    sale_info TEXT NOT NULL COMMENT '扩展属性',
    finance_info TEXT NOT NULL COMMENT '扩展属性',
    view_info TEXT NOT NULL COMMENT '扩展属性',
    performance_info TEXT NOT NULL COMMENT '扩展属性',
    restrict_info TEXT NOT NULL COMMENT '扩展属性',
    inventory_info TEXT NOT NULL COMMENT '扩展属性',
    extra TEXT NOT NULL COMMENT '扩展属性',
    utime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '更新时间',
    ctime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;


CREATE TABLE IF NOT EXISTS inventory (
    id BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '表主键',
    biz_type INT(11)  NOT NULL COMMENT '产品线',
    target_id BIGINT(20)  NOT NULL COMMENT '商品库存状态',
    target_type INT(11)  NOT NULL COMMENT '目标库存类型',
    sub_key VARCHAR(128) NOT NULL COMMENT '库存子 key',
    sale_count BIGINT(20)  NOT NULL COMMENT '售卖数量',
    total_count BIGINT(20)  NOT NULL COMMENT '总量',
    status INT(11)  NOT NULL COMMENT '商品库存状态',
    stime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '开始时间',
    etime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '截止时间',
    version BIGINT(20)  NOT NULL COMMENT '版本号',
    utime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '更新时间',
    ctime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uniq_inventory (target_id, sub_key, target_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;



CREATE TABLE IF NOT EXISTS inventory_record (
    id BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '表主键',
    biz_type INT(11)  NOT NULL COMMENT '产品线',
    user_id BIGINT(20)  NOT NULL COMMENT 'userId',
    inventory_key VARCHAR(128) NOT NULL COMMENT '库存 ID',
    target_id BIGINT(20)  NOT NULL COMMENT '目标库存 Id',
    target_type INT(11)  NOT NULL COMMENT '目标库存类型',
    sub_key VARCHAR(128) NOT NULL COMMENT '库存子 key',
    operate_key VARCHAR(128) NOT NULL COMMENT '库存操作 key',
    op_count BIGINT(20)  NOT NULL COMMENT '操作数量',
    op_type INT(11)  NOT NULL COMMENT '操作方向 1扣减, 2回补',
    utime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '更新时间',
    ctime BIGINT(20)  NOT NULL DEFAULT '0' COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uniq_inventory_record (user_id, operate_key, inventory_key, op_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;