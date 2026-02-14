-- =============================================
-- OpenSCM 第三方集成模块数据库表结构
-- 版本: 1.0.0
-- 日期: 2026-02-15
-- =============================================

-- ---------------------------------------------
-- ERP系统集成表
-- ---------------------------------------------
CREATE TABLE IF NOT EXISTS `erp_integration` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `integration_name` VARCHAR(100) NOT NULL COMMENT '集成配置名称',
    `erp_type` VARCHAR(50) NOT NULL COMMENT 'ERP系统类型',
    `erp_version` VARCHAR(50) COMMENT 'ERP系统版本',
    `api_url` VARCHAR(255) NOT NULL COMMENT '接口地址',
    `access_key` VARCHAR(100) NOT NULL COMMENT '访问密钥',
    `secret_key` VARCHAR(100) NOT NULL COMMENT '访问秘钥',
    `sync_mode` VARCHAR(50) DEFAULT 'PULL' COMMENT '数据同步模式',
    `last_sync_time` DATETIME COMMENT '最后同步时间',
    `sync_status` VARCHAR(50) COMMENT '同步状态',
    `error_message` TEXT COMMENT '错误信息',
    `sync_frequency` VARCHAR(50) COMMENT '同步频率',
    `enabled` TINYINT(1) DEFAULT 0 COMMENT '是否启用',
    `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_erp_type` (`erp_type`),
    KEY `idx_enabled` (`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ERP系统集成配置表';

-- ---------------------------------------------
-- ERP同步日志表
-- ---------------------------------------------
CREATE TABLE IF NOT EXISTS `erp_sync_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `erp_config_id` BIGINT NOT NULL COMMENT 'ERP配置ID',
    `sync_type` VARCHAR(50) NOT NULL COMMENT '同步类型',
    `sync_direction` VARCHAR(50) NOT NULL COMMENT '同步方向',
    `data_type` VARCHAR(50) COMMENT '数据类型',
    `business_no` VARCHAR(100) COMMENT '业务单号',
    `start_time` DATETIME NOT NULL COMMENT '开始时间',
    `end_time` DATETIME COMMENT '结束时间',
    `sync_status` VARCHAR(50) NOT NULL COMMENT '同步状态',
    `success_count` INT DEFAULT 0 COMMENT '成功数量',
    `failure_count` INT DEFAULT 0 COMMENT '失败数量',
    `error_message` TEXT COMMENT '错误信息',
    `sync_content` TEXT COMMENT '同步数据内容',
    `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志',
    PRIMARY KEY (`id`),
    KEY `idx_erp_config_id` (`erp_config_id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_sync_status` (`sync_status`),
    KEY `idx_start_time` (`start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ERP同步日志表';

-- ---------------------------------------------
-- WMS系统集成表
-- ---------------------------------------------
CREATE TABLE IF NOT EXISTS `wms_integration` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `integration_name` VARCHAR(100) NOT NULL COMMENT '集成配置名称',
    `wms_type` VARCHAR(50) NOT NULL COMMENT 'WMS系统类型',
    `wms_version` VARCHAR(50) COMMENT 'WMS系统版本',
    `api_url` VARCHAR(255) NOT NULL COMMENT '接口地址',
    `access_key` VARCHAR(100) NOT NULL COMMENT '访问密钥',
    `secret_key` VARCHAR(100) NOT NULL COMMENT '访问秘钥',
    `warehouse_mapping` TEXT COMMENT '仓库ID映射',
    `location_mapping` TEXT COMMENT '货位ID映射',
    `material_mapping` TEXT COMMENT '物料ID映射',
    `last_sync_time` DATETIME COMMENT '最后同步时间',
    `sync_status` VARCHAR(50) COMMENT '同步状态',
    `sync_frequency` VARCHAR(50) COMMENT '同步频率',
    `enabled` TINYINT(1) DEFAULT 0 COMMENT '是否启用',
    `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_wms_type` (`wms_type`),
    KEY `idx_enabled` (`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='WMS系统集成配置表';

-- ---------------------------------------------
-- TMS系统集成表
-- ---------------------------------------------
CREATE TABLE IF NOT EXISTS `tms_integration` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `integration_name` VARCHAR(100) NOT NULL COMMENT '集成配置名称',
    `tms_type` VARCHAR(50) NOT NULL COMMENT 'TMS系统类型',
    `tms_version` VARCHAR(50) COMMENT 'TMS系统版本',
    `api_url` VARCHAR(255) NOT NULL COMMENT '接口地址',
    `access_key` VARCHAR(100) NOT NULL COMMENT '访问密钥',
    `secret_key` VARCHAR(100) NOT NULL COMMENT '访问秘钥',
    `carrier_mapping` TEXT COMMENT '承运商ID映射',
    `vehicle_mapping` TEXT COMMENT '车辆ID映射',
    `driver_mapping` TEXT COMMENT '驾驶员ID映射',
    `last_sync_time` DATETIME COMMENT '最后同步时间',
    `sync_status` VARCHAR(50) COMMENT '同步状态',
    `sync_frequency` VARCHAR(50) COMMENT '同步频率',
    `enabled` TINYINT(1) DEFAULT 0 COMMENT '是否启用',
    `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_tms_type` (`tms_type`),
    KEY `idx_enabled` (`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='TMS系统集成配置表';

-- ---------------------------------------------
-- 电子发票表
-- ---------------------------------------------
CREATE TABLE IF NOT EXISTS `electronic_invoice` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `invoice_code` VARCHAR(50) COMMENT '发票代码',
    `invoice_no` VARCHAR(50) NOT NULL COMMENT '发票号码',
    `invoice_type` VARCHAR(50) NOT NULL COMMENT '发票类型',
    `invoice_title` VARCHAR(200) NOT NULL COMMENT '发票抬头',
    `tax_number` VARCHAR(50) NOT NULL COMMENT '税号',
    `invoice_date` DATETIME COMMENT '开票日期',
    `amount` DECIMAL(18,2) NOT NULL COMMENT '金额(不含税)',
    `tax_amount` DECIMAL(18,2) NOT NULL COMMENT '税额',
    `total_amount` DECIMAL(18,2) NOT NULL COMMENT '价税合计',
    `invoice_status` VARCHAR(50) DEFAULT 'PENDING' COMMENT '发票状态',
    `business_type` VARCHAR(50) COMMENT '业务类型',
    `business_no` VARCHAR(100) COMMENT '业务单号',
    `pdf_file_path` VARCHAR(500) COMMENT 'PDF文件路径',
    `pdf_file_url` VARCHAR(500) COMMENT 'PDF文件URL',
    `platform` VARCHAR(50) COMMENT '开票平台',
    `issue_status` VARCHAR(50) COMMENT '开票状态',
    `issue_time` DATETIME COMMENT '开票时间',
    `e_invoice_url` VARCHAR(500) COMMENT '电子发票URL',
    `remark` VARCHAR(500) COMMENT '备注',
    `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_invoice_no` (`invoice_no`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_invoice_type` (`invoice_type`),
    KEY `idx_invoice_status` (`invoice_status`),
    KEY `idx_business_no` (`business_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='电子发票表';

-- ---------------------------------------------
-- 支付集成表
-- ---------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_integration` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `payment_no` VARCHAR(100) NOT NULL COMMENT '支付单号',
    `payment_platform` VARCHAR(50) NOT NULL COMMENT '支付平台',
    `payment_method` VARCHAR(50) NOT NULL COMMENT '支付方式',
    `business_type` VARCHAR(50) COMMENT '业务类型',
    `business_no` VARCHAR(100) COMMENT '业务单号',
    `amount` DECIMAL(18,2) NOT NULL COMMENT '支付金额',
    `currency` VARCHAR(20) DEFAULT 'CNY' COMMENT '货币类型',
    `payment_status` VARCHAR(50) DEFAULT 'PENDING' COMMENT '支付状态',
    `third_party_no` VARCHAR(100) COMMENT '第三方支付单号',
    `payment_time` DATETIME COMMENT '支付时间',
    `callback_time` DATETIME COMMENT '回调时间',
    `callback_content` TEXT COMMENT '回调内容',
    `payment_ip` VARCHAR(50) COMMENT '支付IP',
    `payment_terminal` VARCHAR(50) COMMENT '支付终端',
    `remark` VARCHAR(500) COMMENT '备注',
    `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_payment_no` (`payment_no`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_payment_platform` (`payment_platform`),
    KEY `idx_payment_status` (`payment_status`),
    KEY `idx_business_no` (`business_no`),
    KEY `idx_third_party_no` (`third_party_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付集成表';

-- ---------------------------------------------
-- 消息集成表
-- ---------------------------------------------
CREATE TABLE IF NOT EXISTS `message_integration` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `message_id` VARCHAR(100) NOT NULL COMMENT '消息ID',
    `message_type` VARCHAR(50) NOT NULL COMMENT '消息类型',
    `send_method` VARCHAR(50) NOT NULL COMMENT '发送方式',
    `receiver` VARCHAR(200) NOT NULL COMMENT '接收方',
    `receiver_type` VARCHAR(50) NOT NULL COMMENT '接收方类型',
    `message_title` VARCHAR(200) COMMENT '消息标题',
    `message_content` TEXT COMMENT '消息内容',
    `template_id` VARCHAR(100) COMMENT '模板ID',
    `template_params` TEXT COMMENT '模板参数',
    `send_status` VARCHAR(50) DEFAULT 'PENDING' COMMENT '发送状态',
    `send_time` DATETIME COMMENT '发送时间',
    `failure_count` INT DEFAULT 0 COMMENT '失败次数',
    `error_message` TEXT COMMENT '错误信息',
    `send_ip` VARCHAR(50) COMMENT '发送IP',
    `business_type` VARCHAR(50) COMMENT '业务类型',
    `business_no` VARCHAR(100) COMMENT '业务单号',
    `remark` VARCHAR(500) COMMENT '备注',
    `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
    `create_by` VARCHAR(64) COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_message_id` (`message_id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_message_type` (`message_type`),
    KEY `idx_send_method` (`send_method`),
    KEY `idx_send_status` (`send_status`),
    KEY `idx_receiver` (`receiver`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息集成表';

-- ---------------------------------------------
-- 初始化字典数据
-- ---------------------------------------------

-- ERP系统类型
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('erp_type', 'SAP', 'SAP', 1, '0', NOW()),
('erp_type', 'Oracle', 'ORACLE', 2, '0', NOW()),
('erp_type', '用友', 'YONYOU', 3, '0', NOW()),
('erp_type', '金蝶', 'KINGDEE', 4, '0', NOW()),
('erp_type', '鼎捷', 'DINGJIE', 5, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- WMS系统类型
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('wms_type', '富勒', 'FULU', 1, '0', NOW()),
('wms_type', '唯智', 'WMS', 2, '0', NOW()),
('wms_type', '百世', 'BEST', 3, '0', NOW()),
('wms_type', '顺丰', 'SF', 4, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- TMS系统类型
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('tms_type', 'oTMS', 'OTMS', 1, '0', NOW()),
('tms_type', '运去哪', 'YUNQUNA', 2, '0', NOW()),
('tms_type', '路歌', 'LUGE', 3, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 数据同步模式
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('sync_mode', '拉取模式', 'PULL', 1, '0', NOW()),
('sync_mode', '推送模式', 'PUSH', 2, '0', NOW()),
('sync_mode', '双向同步', 'BIDIRECTIONAL', 3, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 同步状态
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('sync_status', '同步中', 'PROCESSING', 1, '0', NOW()),
('sync_status', '同步成功', 'SUCCESS', 2, '0', NOW()),
('sync_status', '同步失败', 'FAILED', 3, '0', NOW()),
('sync_status', '未同步', 'NOT_SYNC', 4, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 发票类型
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('invoice_type', '增值税专用发票', 'SPECIAL', 1, '0', NOW()),
('invoice_type', '增值税普通发票', 'NORMAL', 2, '0', NOW()),
('invoice_type', '电子发票', 'ELECTRONIC', 3, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 发票状态
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('invoice_status', '待开票', 'PENDING', 1, '0', NOW()),
('invoice_status', '已开票', 'ISSUED', 2, '0', NOW()),
('invoice_status', '已作废', 'CANCELLED', 3, '0', NOW()),
('invoice_status', '已红冲', 'REVERSED', 4, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 开票平台
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('invoice_platform', '航天信息', 'AISINO', 1, '0', NOW()),
('invoice_platform', '百望云', 'BAIWANG', 2, '0', NOW()),
('invoice_platform', '票易通', 'PIAOYITONG', 3, '0', NOW()),
('invoice_platform', '京东发票', 'JD', 4, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 支付平台
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('payment_platform', '支付宝', 'ALIPAY', 1, '0', NOW()),
('payment_platform', '微信支付', 'WECHAT', 2, '0', NOW()),
('payment_platform', '银联支付', 'UNIONPAY', 3, '0', NOW()),
('payment_platform', '银行转账', 'BANK', 4, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 支付方式
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('payment_method', '扫码支付', 'QR_CODE', 1, '0', NOW()),
('payment_method', 'APP支付', 'APP', 2, '0', NOW()),
('payment_method', '网页支付', 'WEB', 3, '0', NOW()),
('payment_method', '快捷支付', 'QUICK', 4, '0', NOW()),
('payment_method', '企业转账', 'TRANSFER', 5, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 支付状态
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('payment_status', '待支付', 'PENDING', 1, '0', NOW()),
('payment_status', '支付成功', 'SUCCESS', 2, '0', NOW()),
('payment_status', '支付失败', 'FAILED', 3, '0', NOW()),
('payment_status', '已退款', 'REFUNDED', 4, '0', NOW()),
('payment_status', '退款中', 'REFUNDING', 5, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 消息类型
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('message_type', '通知消息', 'NOTICE', 1, '0', NOW()),
('message_type', '警告消息', 'WARNING', 2, '0', NOW()),
('message_type', '错误消息', 'ERROR', 3, '0', NOW()),
('message_type', '业务消息', 'BUSINESS', 4, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 消息发送方式
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('send_method', '邮件', 'EMAIL', 1, '0', NOW()),
('send_method', '短信', 'SMS', 2, '0', NOW()),
('send_method', '站内信', 'STATION', 3, '0', NOW()),
('send_method', '微信', 'WECHAT', 4, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 消息发送状态
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`)
VALUES
('send_status', '待发送', 'PENDING', 1, '0', NOW()),
('send_status', '发送中', 'SENDING', 2, '0', NOW()),
('send_status', '发送成功', 'SUCCESS', 3, '0', NOW()),
('send_status', '发送失败', 'FAILED', 4, '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 字典类型定义
INSERT INTO `sys_dict_type` (`dict_type`, `dict_name`, `status`, `create_time`)
VALUES
('erp_type', 'ERP系统类型', '0', NOW()),
('wms_type', 'WMS系统类型', '0', NOW()),
('tms_type', 'TMS系统类型', '0', NOW()),
('sync_mode', '数据同步模式', '0', NOW()),
('sync_status', '同步状态', '0', NOW()),
('invoice_type', '发票类型', '0', NOW()),
('invoice_status', '发票状态', '0', NOW()),
('invoice_platform', '开票平台', '0', NOW()),
('payment_platform', '支付平台', '0', NOW()),
('payment_method', '支付方式', '0', NOW()),
('payment_status', '支付状态', '0', NOW()),
('message_type', '消息类型', '0', NOW()),
('send_method', '消息发送方式', '0', NOW()),
('send_status', '消息发送状态', '0', NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();
