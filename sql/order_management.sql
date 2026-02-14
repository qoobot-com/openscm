-- ================================================================
-- OpenSCM 订单管理系统数据库表结构
-- ================================================================

-- 销售订单表
CREATE TABLE IF NOT EXISTS `sales_order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
    `customer_id` BIGINT NOT NULL COMMENT '客户ID',
    `customer_name` VARCHAR(100) NOT NULL COMMENT '客户名称',
    `order_date` DATE NOT NULL COMMENT '订单日期',
    `delivery_date` DATE COMMENT '交货日期',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '订单状态（0-草稿，1-待确认，2-已确认，3-生产中，4-已发货，5-已完成，6-已取消）',
    `amount` DECIMAL(18,2) NOT NULL DEFAULT 0.00 COMMENT '订单金额',
    `discount_amount` DECIMAL(18,2) NOT NULL DEFAULT 0.00 COMMENT '折扣金额',
    `paid_amount` DECIMAL(18,2) NOT NULL DEFAULT 0.00 COMMENT '实付金额',
    `payment_method` TINYINT NOT NULL DEFAULT 0 COMMENT '付款方式（0-货到付款，1-银行转账，2-在线支付，3-月结）',
    `delivery_address` VARCHAR(500) COMMENT '送货地址',
    `contact_person` VARCHAR(50) COMMENT '联系人',
    `contact_phone` VARCHAR(20) COMMENT '联系电话',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除（0-未删除，1-已删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_customer_id` (`customer_id`),
    KEY `idx_status` (`status`),
    KEY `idx_order_date` (`order_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='销售订单表';

-- 销售订单明细表
CREATE TABLE IF NOT EXISTS `sales_order_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `material_id` BIGINT NOT NULL COMMENT '物料ID',
    `material_code` VARCHAR(50) NOT NULL COMMENT '物料编码',
    `material_name` VARCHAR(100) NOT NULL COMMENT '物料名称',
    `specification` VARCHAR(200) COMMENT '规格型号',
    `unit` VARCHAR(20) COMMENT '单位',
    `quantity` DECIMAL(18,2) NOT NULL COMMENT '数量',
    `unit_price` DECIMAL(18,2) NOT NULL COMMENT '单价',
    `amount` DECIMAL(18,2) NOT NULL COMMENT '金额',
    `discount_rate` DECIMAL(5,2) COMMENT '折扣率',
    `discount_amount` DECIMAL(18,2) NOT NULL DEFAULT 0.00 COMMENT '折扣金额',
    `paid_amount` DECIMAL(18,2) NOT NULL COMMENT '实付金额',
    `shipped_quantity` DECIMAL(18,2) NOT NULL DEFAULT 0.00 COMMENT '已发货数量',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_material_id` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='销售订单明细表';

-- 订单支付记录表
CREATE TABLE IF NOT EXISTS `order_payment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `payment_no` VARCHAR(50) NOT NULL COMMENT '支付编号',
    `amount` DECIMAL(18,2) NOT NULL COMMENT '支付金额',
    `payment_method` TINYINT NOT NULL COMMENT '支付方式（0-货到付款，1-银行转账，2-在线支付，3-月结）',
    `payment_time` DATETIME COMMENT '支付时间',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '支付状态（0-待支付，1-已支付，2-支付失败）',
    `voucher` VARCHAR(500) COMMENT '支付凭证',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_payment_no` (`payment_no`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单支付记录表';

-- 订单发货记录表
CREATE TABLE IF NOT EXISTS `order_shipment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `shipment_no` VARCHAR(50) NOT NULL COMMENT '发货单号',
    `logistics_company` VARCHAR(100) COMMENT '物流公司',
    `tracking_no` VARCHAR(50) COMMENT '运单号',
    `shipment_time` DATETIME COMMENT '发货时间',
    `estimated_delivery_time` DATETIME COMMENT '预计送达时间',
    `actual_delivery_time` DATETIME COMMENT '实际送达时间',
    `delivery_address` VARCHAR(500) COMMENT '收货地址',
    `receiver` VARCHAR(50) COMMENT '收货人',
    `receiver_phone` VARCHAR(20) COMMENT '收货电话',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '发货状态（0-待发货，1-已发货，2-已签收，3-已拒收）',
    `shipped_by` VARCHAR(50) COMMENT '发货人',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_shipment_no` (`shipment_no`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_tracking_no` (`tracking_no`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单发货记录表';

-- 订单退货表
CREATE TABLE IF NOT EXISTS `order_return` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `return_no` VARCHAR(50) NOT NULL COMMENT '退货单号',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
    `customer_id` BIGINT NOT NULL COMMENT '客户ID',
    `customer_name` VARCHAR(100) NOT NULL COMMENT '客户名称',
    `return_date` DATE NOT NULL COMMENT '退货日期',
    `return_reason` VARCHAR(500) COMMENT '退货原因',
    `return_type` TINYINT NOT NULL COMMENT '退货类型（0-质量问题，1-发错货，2-客户取消，3-其他）',
    `return_quantity` DECIMAL(18,2) NOT NULL COMMENT '退货数量',
    `return_amount` DECIMAL(18,2) NOT NULL COMMENT '退货金额',
    `refund_status` TINYINT NOT NULL DEFAULT 0 COMMENT '退款状态（0-待审核，1-审核通过，2-已退款，3-审核拒绝）',
    `approved_by` VARCHAR(50) COMMENT '审核人',
    `approve_time` DATETIME COMMENT '审核时间',
    `approve_remark` VARCHAR(500) COMMENT '审核意见',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_return_no` (`return_no`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_customer_id` (`customer_id`),
    KEY `idx_refund_status` (`refund_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单退货表';

-- 订单退货明细表
CREATE TABLE IF NOT EXISTS `order_return_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `return_id` BIGINT NOT NULL COMMENT '退货单ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `order_item_id` BIGINT NOT NULL COMMENT '订单明细ID',
    `material_id` BIGINT NOT NULL COMMENT '物料ID',
    `material_code` VARCHAR(50) NOT NULL COMMENT '物料编码',
    `material_name` VARCHAR(100) NOT NULL COMMENT '物料名称',
    `specification` VARCHAR(200) COMMENT '规格型号',
    `unit` VARCHAR(20) COMMENT '单位',
    `original_quantity` DECIMAL(18,2) NOT NULL COMMENT '原订单数量',
    `return_quantity` DECIMAL(18,2) NOT NULL COMMENT '退货数量',
    `unit_price` DECIMAL(18,2) NOT NULL COMMENT '单价',
    `return_amount` DECIMAL(18,2) NOT NULL COMMENT '退货金额',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_return_id` (`return_id`),
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单退货明细表';

-- ================================================================
-- 初始化字典数据
-- ================================================================

-- 订单状态字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('order_status', '草稿', '0', 1, '0', NOW()),
('order_status', '待确认', '1', 2, '0', NOW()),
('order_status', '已确认', '2', 3, '0', NOW()),
('order_status', '生产中', '3', 4, '0', NOW()),
('order_status', '已发货', '4', 5, '0', NOW()),
('order_status', '已完成', '5', 6, '0', NOW()),
('order_status', '已取消', '6', 7, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 付款方式字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('payment_method', '货到付款', '0', 1, '0', NOW()),
('payment_method', '银行转账', '1', 2, '0', NOW()),
('payment_method', '在线支付', '2', 3, '0', NOW()),
('payment_method', '月结', '3', 4, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 支付状态字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('payment_status', '待支付', '0', 1, '0', NOW()),
('payment_status', '已支付', '1', 2, '0', NOW()),
('payment_status', '支付失败', '2', 3, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 发货状态字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('shipment_status', '待发货', '0', 1, '0', NOW()),
('shipment_status', '已发货', '1', 2, '0', NOW()),
('shipment_status', '已签收', '2', 3, '0', NOW()),
('shipment_status', '已拒收', '3', 4, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 退货类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('return_type', '质量问题', '0', 1, '0', NOW()),
('return_type', '发错货', '1', 2, '0', NOW()),
('return_type', '客户取消', '2', 3, '0', NOW()),
('return_type', '其他', '3', 4, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 退款状态字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('refund_status', '待审核', '0', 1, '0', NOW()),
('refund_status', '审核通过', '1', 2, '0', NOW()),
('refund_status', '已退款', '2', 3, '0', NOW()),
('refund_status', '审核拒绝', '3', 4, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);
