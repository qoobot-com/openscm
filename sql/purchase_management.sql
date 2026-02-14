-- ============================================
-- 采购管理系统数据库表结构
-- OpenSCM - Purchase Management Module
-- 创建时间: 2026-02-15
-- ============================================

-- ----------------------------
-- 1. 采购计划表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `purchase_plan` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `plan_code` VARCHAR(50) NOT NULL COMMENT '计划编号',
  `plan_name` VARCHAR(200) NOT NULL COMMENT '计划名称',
  `plan_type` TINYINT NOT NULL COMMENT '计划类型 1-正常采购 2-紧急采购 3-补充采购',
  `plan_date` DATE NOT NULL COMMENT '计划日期',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0-草稿 1-待审批 2-已批准 3-已拒绝 4-执行中 5-已完成 6-已取消',
  `plan_amount` DECIMAL(18, 2) NOT NULL COMMENT '计划金额',
  `requester` VARCHAR(50) COMMENT '申请人',
  `department` VARCHAR(100) COMMENT '申请部门',
  `remark` TEXT COMMENT '备注',
  `submitted_at` DATETIME COMMENT '提交时间',
  `approver` VARCHAR(50) COMMENT '审批人',
  `approval_remark` TEXT COMMENT '审批备注',
  `approved_at` DATETIME COMMENT '审批时间',
  `executed_at` DATETIME COMMENT '执行时间',
  `completed_at` DATETIME COMMENT '完成时间',
  `cancelled_at` DATETIME COMMENT '取消时间',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_plan_code` (`plan_code`),
  KEY `idx_plan_type` (`plan_type`),
  KEY `idx_status` (`status`),
  KEY `idx_plan_date` (`plan_date`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采购计划表';

-- ----------------------------
-- 2. 采购计划明细表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `purchase_plan_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `plan_id` BIGINT NOT NULL COMMENT '采购计划ID',
  `material_code` VARCHAR(50) NOT NULL COMMENT '物料编码',
  `material_name` VARCHAR(200) NOT NULL COMMENT '物料名称',
  `specification` VARCHAR(200) COMMENT '规格型号',
  `unit` VARCHAR(20) COMMENT '单位',
  `plan_quantity` DECIMAL(18, 2) NOT NULL COMMENT '计划数量',
  `unit_price` DECIMAL(18, 2) COMMENT '单价',
  `plan_amount` DECIMAL(18, 2) COMMENT '计划金额',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_material_code` (`material_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采购计划明细表';

-- ----------------------------
-- 3. 采购订单表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `purchase_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_code` VARCHAR(50) NOT NULL COMMENT '订单编号',
  `plan_id` BIGINT COMMENT '采购计划ID',
  `supplier_id` BIGINT NOT NULL COMMENT '供应商ID',
  `order_date` DATE NOT NULL COMMENT '订单日期',
  `expected_delivery_date` DATE COMMENT '预计交货日期',
  `actual_delivery_date` DATE COMMENT '实际交货日期',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0-待确认 1-已确认 2-已取消 3-已发货 4-已收货 5-已完成',
  `order_amount` DECIMAL(18, 2) NOT NULL COMMENT '订单金额',
  `paid_amount` DECIMAL(18, 2) DEFAULT 0.00 COMMENT '已付金额',
  `payment_term` VARCHAR(50) COMMENT '付款条件',
  `delivery_address` VARCHAR(500) COMMENT '收货地址',
  `contact_person` VARCHAR(50) COMMENT '联系人',
  `contact_phone` VARCHAR(20) COMMENT '联系电话',
  `tracking_number` VARCHAR(100) COMMENT '物流单号',
  `carrier` VARCHAR(100) COMMENT '承运商',
  `received_quantity` DECIMAL(18, 2) DEFAULT 0.00 COMMENT '已收数量',
  `received_remark` TEXT COMMENT '收货备注',
  `cancel_reason` TEXT COMMENT '取消原因',
  `remark` TEXT COMMENT '备注',
  `confirmed_at` DATETIME COMMENT '确认时间',
  `cancelled_at` DATETIME COMMENT '取消时间',
  `shipped_at` DATETIME COMMENT '发货时间',
  `received_at` DATETIME COMMENT '收货时间',
  `completed_at` DATETIME COMMENT '完成时间',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_code` (`order_code`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_status` (`status`),
  KEY `idx_order_date` (`order_date`),
  KEY `idx_expected_delivery_date` (`expected_delivery_date`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采购订单表';

-- ----------------------------
-- 4. 采购订单明细表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `purchase_order_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` BIGINT NOT NULL COMMENT '采购订单ID',
  `material_code` VARCHAR(50) NOT NULL COMMENT '物料编码',
  `material_name` VARCHAR(200) NOT NULL COMMENT '物料名称',
  `specification` VARCHAR(200) COMMENT '规格型号',
  `unit` VARCHAR(20) COMMENT '单位',
  `quantity` DECIMAL(18, 2) NOT NULL COMMENT '订购数量',
  `unit_price` DECIMAL(18, 2) NOT NULL COMMENT '单价',
  `amount` DECIMAL(18, 2) NOT NULL COMMENT '金额',
  `received_quantity` DECIMAL(18, 2) DEFAULT 0.00 COMMENT '已收数量',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_material_code` (`material_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采购订单明细表';

-- ----------------------------
-- 5. 采购入库单表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `purchase_inbound` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `inbound_code` VARCHAR(50) NOT NULL COMMENT '入库单编号',
  `order_id` BIGINT NOT NULL COMMENT '采购订单ID',
  `supplier_id` BIGINT NOT NULL COMMENT '供应商ID',
  `inbound_date` DATE NOT NULL COMMENT '入库日期',
  `warehouse_id` BIGINT NOT NULL COMMENT '仓库ID',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0-待质检 1-质检中 2-质检合格 3-质检不合格 4-已入库 5-已完成',
  `total_quantity` DECIMAL(18, 2) NOT NULL COMMENT '总数量',
  `qualified_quantity` DECIMAL(18, 2) DEFAULT 0.00 COMMENT '合格数量',
  `unqualified_quantity` DECIMAL(18, 2) DEFAULT 0.00 COMMENT '不合格数量',
  `quality_remark` TEXT COMMENT '质检备注',
  `quality_user` VARCHAR(50) COMMENT '质检人',
  `quality_date` DATETIME COMMENT '质检时间',
  `inbound_user` VARCHAR(50) COMMENT '入库人',
  `inbound_date_time` DATETIME COMMENT '入库时间',
  `remark` TEXT COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_inbound_code` (`inbound_code`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_status` (`status`),
  KEY `idx_inbound_date` (`inbound_date`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采购入库单表';

-- ----------------------------
-- 6. 采购入库单明细表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `purchase_inbound_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `inbound_id` BIGINT NOT NULL COMMENT '入库单ID',
  `order_item_id` BIGINT NOT NULL COMMENT '订单明细ID',
  `material_code` VARCHAR(50) NOT NULL COMMENT '物料编码',
  `material_name` VARCHAR(200) NOT NULL COMMENT '物料名称',
  `specification` VARCHAR(200) COMMENT '规格型号',
  `unit` VARCHAR(20) COMMENT '单位',
  `quantity` DECIMAL(18, 2) NOT NULL COMMENT '入库数量',
  `qualified_quantity` DECIMAL(18, 2) DEFAULT 0.00 COMMENT '合格数量',
  `unqualified_quantity` DECIMAL(18, 2) DEFAULT 0.00 COMMENT '不合格数量',
  `location` VARCHAR(50) COMMENT '货位',
  `batch_number` VARCHAR(50) COMMENT '批次号',
  `production_date` DATE COMMENT '生产日期',
  `expiry_date` DATE COMMENT '有效期',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_inbound_id` (`inbound_id`),
  KEY `idx_order_item_id` (`order_item_id`),
  KEY `idx_material_code` (`material_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采购入库单明细表';

-- ----------------------------
-- 7. 供应商询价表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `supplier_inquiry` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `inquiry_code` VARCHAR(50) NOT NULL COMMENT '询价单编号',
  `plan_id` BIGINT COMMENT '采购计划ID',
  `inquiry_date` DATE NOT NULL COMMENT '询价日期',
  `deadline_date` DATE COMMENT '报价截止日期',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0-草稿 1-询价中 2-已完成 3-已取消',
  `inquiry_user` VARCHAR(50) COMMENT '询价人',
  `department` VARCHAR(100) COMMENT '部门',
  `total_amount` DECIMAL(18, 2) COMMENT '预算总额',
  `inquiry_content` TEXT COMMENT '询价内容',
  `remark` TEXT COMMENT '备注',
  `started_at` DATETIME COMMENT '开始询价时间',
  `completed_at` DATETIME COMMENT '完成时间',
  `cancelled_at` DATETIME COMMENT '取消时间',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_inquiry_code` (`inquiry_code`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_status` (`status`),
  KEY `idx_inquiry_date` (`inquiry_date`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='供应商询价表';

-- ----------------------------
-- 8. 供应商询价明细表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `supplier_inquiry_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `inquiry_id` BIGINT NOT NULL COMMENT '询价单ID',
  `material_code` VARCHAR(50) NOT NULL COMMENT '物料编码',
  `material_name` VARCHAR(200) NOT NULL COMMENT '物料名称',
  `specification` VARCHAR(200) COMMENT '规格型号',
  `unit` VARCHAR(20) COMMENT '单位',
  `inquiry_quantity` DECIMAL(18, 2) NOT NULL COMMENT '询价数量',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_inquiry_id` (`inquiry_id`),
  KEY `idx_material_code` (`material_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='供应商询价明细表';

-- ----------------------------
-- 9. 供应商报价表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `supplier_quote` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `quote_code` VARCHAR(50) NOT NULL COMMENT '报价单编号',
  `inquiry_id` BIGINT NOT NULL COMMENT '询价单ID',
  `supplier_id` BIGINT NOT NULL COMMENT '供应商ID',
  `quote_date` DATE NOT NULL COMMENT '报价日期',
  `valid_until` DATE COMMENT '有效期至',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0-待审核 1-已接受 2-已拒绝 3-已过期',
  `total_amount` DECIMAL(18, 2) NOT NULL COMMENT '报价总额',
  `delivery_period` INT COMMENT '交货周期(天)',
  `payment_term` VARCHAR(100) COMMENT '付款条件',
  `quote_remark` TEXT COMMENT '报价备注',
  `accepted_at` DATETIME COMMENT '接受时间',
  `rejected_at` DATETIME COMMENT '拒绝时间',
  `reject_reason` TEXT COMMENT '拒绝原因',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_quote_code` (`quote_code`),
  KEY `idx_inquiry_id` (`inquiry_id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_status` (`status`),
  KEY `idx_quote_date` (`quote_date`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='供应商报价表';

-- ----------------------------
-- 10. 供应商报价明细表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `supplier_quote_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `quote_id` BIGINT NOT NULL COMMENT '报价单ID',
  `inquiry_item_id` BIGINT NOT NULL COMMENT '询价明细ID',
  `material_code` VARCHAR(50) NOT NULL COMMENT '物料编码',
  `material_name` VARCHAR(200) NOT NULL COMMENT '物料名称',
  `specification` VARCHAR(200) COMMENT '规格型号',
  `unit` VARCHAR(20) COMMENT '单位',
  `quote_quantity` DECIMAL(18, 2) NOT NULL COMMENT '报价数量',
  `unit_price` DECIMAL(18, 2) NOT NULL COMMENT '单价',
  `amount` DECIMAL(18, 2) NOT NULL COMMENT '金额',
  `delivery_date` DATE COMMENT '交货日期',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_quote_id` (`quote_id`),
  KEY `idx_inquiry_item_id` (`inquiry_item_id`),
  KEY `idx_material_code` (`material_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='供应商报价明细表';

-- ----------------------------
-- 初始化数据
-- ----------------------------
-- 插入采购类型字典数据（如果字典表存在）
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('plan_type', '正常采购', '1', 1, '1', NOW()),
('plan_type', '紧急采购', '2', 2, '1', NOW()),
('plan_type', '补充采购', '3', 3, '1', NOW());

-- 插入采购计划状态字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('plan_status', '草稿', '0', 1, '1', NOW()),
('plan_status', '待审批', '1', 2, '1', NOW()),
('plan_status', '已批准', '2', 3, '1', NOW()),
('plan_status', '已拒绝', '3', 4, '1', NOW()),
('plan_status', '执行中', '4', 5, '1', NOW()),
('plan_status', '已完成', '5', 6, '1', NOW()),
('plan_status', '已取消', '6', 7, '1', NOW());

-- 插入采购订单状态字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('order_status', '待确认', '0', 1, '1', NOW()),
('order_status', '已确认', '1', 2, '1', NOW()),
('order_status', '已取消', '2', 3, '1', NOW()),
('order_status', '已发货', '3', 4, '1', NOW()),
('order_status', '已收货', '4', 5, '1', NOW()),
('order_status', '已完成', '5', 6, '1', NOW());

-- 插入入库单状态字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('inbound_status', '待质检', '0', 1, '1', NOW()),
('inbound_status', '质检中', '1', 2, '1', NOW()),
('inbound_status', '质检合格', '2', 3, '1', NOW()),
('inbound_status', '质检不合格', '3', 4, '1', NOW()),
('inbound_status', '已入库', '4', 5, '1', NOW()),
('inbound_status', '已完成', '5', 6, '1', NOW());

-- 插入询价状态字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('inquiry_status', '草稿', '0', 1, '1', NOW()),
('inquiry_status', '询价中', '1', 2, '1', NOW()),
('inquiry_status', '已完成', '2', 3, '1', NOW()),
('inquiry_status', '已取消', '3', 4, '1', NOW());

-- 插入报价状态字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('quote_status', '待审核', '0', 1, '1', NOW()),
('quote_status', '已接受', '1', 2, '1', NOW()),
('quote_status', '已拒绝', '2', 3, '1', NOW()),
('quote_status', '已过期', '3', 4, '1', NOW());
