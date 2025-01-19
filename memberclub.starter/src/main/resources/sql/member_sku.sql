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