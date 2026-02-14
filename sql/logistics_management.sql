-- ================================================================
-- OpenSCM 物流管理系统数据库表结构
-- ================================================================

-- 运输计划表
CREATE TABLE IF NOT EXISTS `transport_plan` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `plan_no` VARCHAR(50) NOT NULL COMMENT '运输计划编号',
    `order_id` BIGINT NOT NULL COMMENT '关联订单ID',
    `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
    `transport_type` TINYINT NOT NULL DEFAULT 1 COMMENT '运输类型（0-自提，1-配送，2-快递）',
    `start_address` VARCHAR(500) COMMENT '起始地址',
    `destination_address` VARCHAR(500) COMMENT '目的地址',
    `estimated_departure_time` DATETIME COMMENT '预计出发时间',
    `estimated_arrival_time` DATETIME COMMENT '预计到达时间',
    `actual_departure_time` DATETIME COMMENT '实际出发时间',
    `actual_arrival_time` DATETIME COMMENT '实际到达时间',
    `distance` DECIMAL(10,2) COMMENT '运输距离（公里）',
    `transport_fee` DECIMAL(18,2) NOT NULL DEFAULT 0.00 COMMENT '运输费用',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '运输状态（0-待出发，1-运输中，2-已送达，3-已取消）',
    `vehicle_info` VARCHAR(200) COMMENT '运输车辆信息',
    `driver_info` VARCHAR(200) COMMENT '司机信息',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_plan_no` (`plan_no`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='运输计划表';

-- 配送单表
CREATE TABLE IF NOT EXISTS `delivery_order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `delivery_no` VARCHAR(50) NOT NULL COMMENT '配送单号',
    `plan_id` BIGINT COMMENT '运输计划ID',
    `order_id` BIGINT NOT NULL COMMENT '关联订单ID',
    `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
    `delivery_type` TINYINT NOT NULL DEFAULT 0 COMMENT '配送类型（0-普通配送，1-加急配送，2-预约配送）',
    `receiver_name` VARCHAR(100) NOT NULL COMMENT '收货人姓名',
    `receiver_phone` VARCHAR(20) NOT NULL COMMENT '收货人电话',
    `delivery_address` VARCHAR(500) NOT NULL COMMENT '收货地址',
    `scheduled_delivery_time` DATETIME COMMENT '预约配送时间',
    `actual_delivery_time` DATETIME COMMENT '实际配送时间',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '配送状态（0-待配送，1-配送中，2-已签收，3-已拒收，4-已取消）',
    `delivery_man` VARCHAR(50) COMMENT '配送员',
    `delivery_fee` DECIMAL(18,2) NOT NULL DEFAULT 0.00 COMMENT '配送费用',
    `signer` VARCHAR(50) COMMENT '签收人',
    `sign_time` DATETIME COMMENT '签收时间',
    `sign_photo` VARCHAR(500) COMMENT '签收照片',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_delivery_no` (`delivery_no`),
    KEY `idx_plan_id` (`plan_id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='配送单表';

-- 物流轨迹表
CREATE TABLE IF NOT EXISTS `logistics_tracking` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tracking_no` VARCHAR(50) NOT NULL COMMENT '轨迹编号',
    `plan_id` BIGINT COMMENT '关联运输计划ID',
    `delivery_id` BIGINT COMMENT '关联配送单ID',
    `waybill_no` VARCHAR(50) NOT NULL COMMENT '运单号',
    `node` TINYINT NOT NULL COMMENT '物流节点（0-已下单，1-已取件，2-运输中，3-派送中，4-已签收，5-异常）',
    `node_name` VARCHAR(50) NOT NULL COMMENT '节点名称',
    `node_desc` VARCHAR(200) COMMENT '节点描述',
    `current_location` VARCHAR(200) COMMENT '当前位置',
    `longitude` DECIMAL(10,7) COMMENT '经度',
    `latitude` DECIMAL(10,7) COMMENT '纬度',
    `node_time` DATETIME NOT NULL COMMENT '节点时间',
    `description` VARCHAR(500) COMMENT '轨迹说明',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_waybill_no` (`waybill_no`),
    KEY `idx_plan_id` (`plan_id`),
    KEY `idx_delivery_id` (`delivery_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物流轨迹表';

-- 物流费用表
CREATE TABLE IF NOT EXISTS `logistics_cost` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `cost_no` VARCHAR(50) NOT NULL COMMENT '费用编号',
    `plan_id` BIGINT COMMENT '关联运输计划ID',
    `delivery_id` BIGINT COMMENT '关联配送单ID',
    `cost_type` TINYINT NOT NULL COMMENT '费用类型（0-运输费，1-配送费，2-保险费，3-装卸费，4-其他费用）',
    `amount` DECIMAL(18,2) NOT NULL COMMENT '费用金额',
    `cost_desc` VARCHAR(200) COMMENT '费用说明',
    `cost_date` DATE NOT NULL COMMENT '费用日期',
    `payment_status` TINYINT NOT NULL DEFAULT 0 COMMENT '支付状态（0-待支付，1-已支付，2-已结算）',
    `payment_time` DATETIME COMMENT '支付时间',
    `handler` VARCHAR(50) COMMENT '经办人',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_cost_no` (`cost_no`),
    KEY `idx_plan_id` (`plan_id`),
    KEY `idx_delivery_id` (`delivery_id`),
    KEY `idx_payment_status` (`payment_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物流费用表';

-- 物流异常表
CREATE TABLE IF NOT EXISTS `logistics_exception` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `exception_no` VARCHAR(50) NOT NULL COMMENT '异常编号',
    `plan_id` BIGINT COMMENT '关联运输计划ID',
    `delivery_id` BIGINT COMMENT '关联配送单ID',
    `waybill_no` VARCHAR(50) NOT NULL COMMENT '运单号',
    `exception_type` TINYINT NOT NULL COMMENT '异常类型（0-延误，1-丢失，2-损坏，3-拒收，4-地址错误，5-其他）',
    `exception_level` TINYINT NOT NULL DEFAULT 0 COMMENT '异常级别（0-一般，1-严重，2-紧急）',
    `exception_desc` VARCHAR(500) NOT NULL COMMENT '异常描述',
    `handle_status` TINYINT NOT NULL DEFAULT 0 COMMENT '处理状态（0-待处理，1-处理中，2-已解决，3-已关闭）',
    `handler` VARCHAR(50) COMMENT '处理人',
    `handle_time` DATETIME COMMENT '处理时间',
    `handle_opinion` VARCHAR(500) COMMENT '处理意见',
    `exception_image` VARCHAR(500) COMMENT '异常图片',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_exception_no` (`exception_no`),
    KEY `idx_plan_id` (`plan_id`),
    KEY `idx_delivery_id` (`delivery_id`),
    KEY `idx_waybill_no` (`waybill_no`),
    KEY `idx_handle_status` (`handle_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物流异常表';

-- ================================================================
-- 初始化字典数据
-- ================================================================

-- 运输类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('transport_type', '自提', '0', 1, '0', NOW()),
('transport_type', '配送', '1', 2, '0', NOW()),
('transport_type', '快递', '2', 3, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 运输状态字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('transport_status', '待出发', '0', 1, '0', NOW()),
('transport_status', '运输中', '1', 2, '0', NOW()),
('transport_status', '已送达', '2', 3, '0', NOW()),
('transport_status', '已取消', '3', 4, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 配送类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('delivery_type', '普通配送', '0', 1, '0', NOW()),
('delivery_type', '加急配送', '1', 2, '0', NOW()),
('delivery_type', '预约配送', '2', 3, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 配送状态字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('delivery_status', '待配送', '0', 1, '0', NOW()),
('delivery_status', '配送中', '1', 2, '0', NOW()),
('delivery_status', '已签收', '2', 3, '0', NOW()),
('delivery_status', '已拒收', '3', 4, '0', NOW()),
('delivery_status', '已取消', '4', 5, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 物流节点字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('logistics_node', '已下单', '0', 1, '0', NOW()),
('logistics_node', '已取件', '1', 2, '0', NOW()),
('logistics_node', '运输中', '2', 3, '0', NOW()),
('logistics_node', '派送中', '3', 4, '0', NOW()),
('logistics_node', '已签收', '4', 5, '0', NOW()),
('logistics_node', '异常', '5', 6, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 费用类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('logistics_cost_type', '运输费', '0', 1, '0', NOW()),
('logistics_cost_type', '配送费', '1', 2, '0', NOW()),
('logistics_cost_type', '保险费', '2', 3, '0', NOW()),
('logistics_cost_type', '装卸费', '3', 4, '0', NOW()),
('logistics_cost_type', '其他费用', '4', 5, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 支付状态字典（复用 payment_status）
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('cost_payment_status', '待支付', '0', 1, '0', NOW()),
('cost_payment_status', '已支付', '1', 2, '0', NOW()),
('cost_payment_status', '已结算', '2', 3, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 异常类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('exception_type', '延误', '0', 1, '0', NOW()),
('exception_type', '丢失', '1', 2, '0', NOW()),
('exception_type', '损坏', '2', 3, '0', NOW()),
('exception_type', '拒收', '3', 4, '0', NOW()),
('exception_type', '地址错误', '4', 5, '0', NOW()),
('exception_type', '其他', '5', 6, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 异常级别字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('exception_level', '一般', '0', 1, '0', NOW()),
('exception_level', '严重', '1', 2, '0', NOW()),
('exception_level', '紧急', '2', 3, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 处理状态字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('handle_status', '待处理', '0', 1, '0', NOW()),
('handle_status', '处理中', '1', 2, '0', NOW()),
('handle_status', '已解决', '2', 3, '0', NOW()),
('handle_status', '已关闭', '3', 4, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);
