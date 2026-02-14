-- ============================================
-- 库存管理系统数据库表结构
-- OpenSCM - Inventory Management Module
-- 创建时间: 2026-02-15
-- ============================================

-- ----------------------------
-- 1. 仓库信息表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `warehouse` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `warehouse_code` VARCHAR(50) NOT NULL COMMENT '仓库编码',
  `warehouse_name` VARCHAR(200) NOT NULL COMMENT '仓库名称',
  `warehouse_type` TINYINT NOT NULL COMMENT '仓库类型 1-原材料仓 2-成品仓 3-半成品仓 4-退货仓 5-废品仓',
  `address` VARCHAR(500) COMMENT '仓库地址',
  `contact_person` VARCHAR(50) COMMENT '联系人',
  `contact_phone` VARCHAR(20) COMMENT '联系电话',
  `capacity` DECIMAL(18, 2) COMMENT '容量(平方米)',
  `used_area` DECIMAL(18, 2) DEFAULT 0.00 COMMENT '已使用面积(平方米)',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '仓库状态 0-禁用 1-启用',
  `remark` TEXT COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_warehouse_code` (`warehouse_code`),
  KEY `idx_warehouse_type` (`warehouse_type`),
  KEY `idx_status` (`status`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='仓库信息表';

-- ----------------------------
-- 2. 货位信息表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `warehouse_location` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `location_code` VARCHAR(50) NOT NULL COMMENT '货位编码',
  `warehouse_id` BIGINT NOT NULL COMMENT '仓库ID',
  `zone_code` VARCHAR(50) COMMENT '区域编码',
  `shelf_code` VARCHAR(50) COMMENT '货架编码',
  `level` INT COMMENT '层级',
  `location_type` TINYINT COMMENT '货位类型 1-普通货位 2-冷藏货位 3-危险品货位 4-大件货位',
  `capacity` DECIMAL(18, 2) COMMENT '货位容量',
  `used_capacity` DECIMAL(18, 2) DEFAULT 0.00 COMMENT '已使用容量',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '货位状态 0-空闲 1-占用 2-锁定 3-维修',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_location_code` (`location_code`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_zone_code` (`zone_code`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='货位信息表';

-- ----------------------------
-- 3. 库存信息表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `inventory` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `warehouse_id` BIGINT NOT NULL COMMENT '仓库ID',
  `location_id` BIGINT COMMENT '货位ID',
  `material_code` VARCHAR(50) NOT NULL COMMENT '物料编码',
  `material_name` VARCHAR(200) NOT NULL COMMENT '物料名称',
  `specification` VARCHAR(200) COMMENT '规格型号',
  `unit` VARCHAR(20) COMMENT '单位',
  `batch_number` VARCHAR(50) COMMENT '批次号',
  `quantity` DECIMAL(18, 2) NOT NULL COMMENT '数量',
  `available_quantity` DECIMAL(18, 2) NOT NULL COMMENT '可用数量',
  `locked_quantity` DECIMAL(18, 2) DEFAULT 0.00 COMMENT '锁定数量',
  `unit_price` DECIMAL(18, 2) COMMENT '单价',
  `amount` DECIMAL(18, 2) COMMENT '金额',
  `production_date` DATE COMMENT '生产日期',
  `expiry_date` DATE COMMENT '有效期至',
  `supplier_id` BIGINT COMMENT '供应商ID',
  `inbound_id` BIGINT COMMENT '入库单ID',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_location_id` (`location_id`),
  KEY `idx_material_code` (`material_code`),
  KEY `idx_batch_number` (`batch_number`),
  KEY `idx_supplier_id` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存信息表';

-- ----------------------------
-- 4. 入库单表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `inbound_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `inbound_code` VARCHAR(50) NOT NULL COMMENT '入库单编号',
  `inbound_type` TINYINT NOT NULL COMMENT '入库类型 1-采购入库 2-生产入库 3-退货入库 4-调拨入库 5-其他入库',
  `warehouse_id` BIGINT NOT NULL COMMENT '仓库ID',
  `source_type` VARCHAR(50) COMMENT '来源单据类型',
  `source_id` BIGINT COMMENT '来源单据ID',
  `supplier_id` BIGINT COMMENT '供应商ID',
  `inbound_date` DATE NOT NULL COMMENT '入库日期',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0-待入库 1-入库中 2-已入库 3-已完成',
  `total_quantity` DECIMAL(18, 2) COMMENT '总数量',
  `total_amount` DECIMAL(18, 2) COMMENT '总金额',
  `handler` VARCHAR(50) COMMENT '经办人',
  `auditor` VARCHAR(50) COMMENT '审核人',
  `audit_time` DATETIME COMMENT '审核时间',
  `remark` TEXT COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_inbound_code` (`inbound_code`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_source_type` (`source_type`),
  KEY `idx_source_id` (`source_id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_status` (`status`),
  KEY `idx_inbound_date` (`inbound_date`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='入库单表';

-- ----------------------------
-- 5. 入库单明细表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `inbound_order_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `inbound_id` BIGINT NOT NULL COMMENT '入库单ID',
  `material_code` VARCHAR(50) NOT NULL COMMENT '物料编码',
  `material_name` VARCHAR(200) NOT NULL COMMENT '物料名称',
  `specification` VARCHAR(200) COMMENT '规格型号',
  `unit` VARCHAR(20) COMMENT '单位',
  `quantity` DECIMAL(18, 2) NOT NULL COMMENT '数量',
  `unit_price` DECIMAL(18, 2) COMMENT '单价',
  `amount` DECIMAL(18, 2) COMMENT '金额',
  `location_id` BIGINT COMMENT '货位ID',
  `batch_number` VARCHAR(50) COMMENT '批次号',
  `production_date` DATE COMMENT '生产日期',
  `expiry_date` DATE COMMENT '有效期至',
  `supplier_id` BIGINT COMMENT '供应商ID',
  `actual_quantity` DECIMAL(18, 2) COMMENT '实际数量',
  `diff_quantity` DECIMAL(18, 2) COMMENT '差异数量',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_inbound_id` (`inbound_id`),
  KEY `idx_material_code` (`material_code`),
  KEY `idx_location_id` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='入库单明细表';

-- ----------------------------
-- 6. 出库单表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `outbound_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `outbound_code` VARCHAR(50) NOT NULL COMMENT '出库单编号',
  `outbound_type` TINYINT NOT NULL COMMENT '出库类型 1-销售出库 2-生产领料 3-调拨出库 4-退货出库 5-报废出库 6-其他出库',
  `warehouse_id` BIGINT NOT NULL COMMENT '仓库ID',
  `target_warehouse_id` BIGINT COMMENT '目标仓库ID(调拨时使用)',
  `source_type` VARCHAR(50) COMMENT '来源单据类型',
  `source_id` BIGINT COMMENT '来源单据ID',
  `customer_id` BIGINT COMMENT '客户ID',
  `outbound_date` DATE NOT NULL COMMENT '出库日期',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0-待出库 1-出库中 2-已出库 3-已完成',
  `total_quantity` DECIMAL(18, 2) COMMENT '总数量',
  `total_amount` DECIMAL(18, 2) COMMENT '总金额',
  `handler` VARCHAR(50) COMMENT '经办人',
  `auditor` VARCHAR(50) COMMENT '审核人',
  `audit_time` DATETIME COMMENT '审核时间',
  `receiver` VARCHAR(50) COMMENT '收货人',
  `receiver_address` VARCHAR(500) COMMENT '收货地址',
  `contact_phone` VARCHAR(20) COMMENT '联系电话',
  `remark` TEXT COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_outbound_code` (`outbound_code`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_target_warehouse_id` (`target_warehouse_id`),
  KEY `idx_source_type` (`source_type`),
  KEY `idx_source_id` (`source_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_status` (`status`),
  KEY `idx_outbound_date` (`outbound_date`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='出库单表';

-- ----------------------------
-- 7. 出库单明细表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `outbound_order_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `outbound_id` BIGINT NOT NULL COMMENT '出库单ID',
  `material_code` VARCHAR(50) NOT NULL COMMENT '物料编码',
  `material_name` VARCHAR(200) NOT NULL COMMENT '物料名称',
  `specification` VARCHAR(200) COMMENT '规格型号',
  `unit` VARCHAR(20) COMMENT '单位',
  `quantity` DECIMAL(18, 2) NOT NULL COMMENT '数量',
  `unit_price` DECIMAL(18, 2) COMMENT '单价',
  `amount` DECIMAL(18, 2) COMMENT '金额',
  `location_id` BIGINT COMMENT '货位ID',
  `batch_number` VARCHAR(50) COMMENT '批次号',
  `actual_quantity` DECIMAL(18, 2) COMMENT '实际数量',
  `diff_quantity` DECIMAL(18, 2) COMMENT '差异数量',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_outbound_id` (`outbound_id`),
  KEY `idx_material_code` (`material_code`),
  KEY `idx_location_id` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='出库单明细表';

-- ----------------------------
-- 8. 库存盘点单表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `inventory_check` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `check_code` VARCHAR(50) NOT NULL COMMENT '盘点单编号',
  `warehouse_id` BIGINT NOT NULL COMMENT '仓库ID',
  `check_type` TINYINT NOT NULL COMMENT '盘点类型 1-全盘 2-抽样盘点 3-循环盘点',
  `check_date` DATE NOT NULL COMMENT '盘点日期',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0-待盘点 1-盘点中 2-盘点完成 3-已审核',
  `check_quantity` DECIMAL(18, 2) COMMENT '盘点总数量',
  `system_quantity` DECIMAL(18, 2) COMMENT '系统数量',
  `profit_quantity` DECIMAL(18, 2) DEFAULT 0.00 COMMENT '盘盈数量',
  `loss_quantity` DECIMAL(18, 2) DEFAULT 0.00 COMMENT '盘亏数量',
  `checker` VARCHAR(50) COMMENT '盘点人',
  `auditor` VARCHAR(50) COMMENT '审核人',
  `audit_time` DATETIME COMMENT '审核时间',
  `remark` TEXT COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_check_code` (`check_code`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_check_type` (`check_type`),
  KEY `idx_status` (`status`),
  KEY `idx_check_date` (`check_date`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存盘点单表';

-- ----------------------------
-- 9. 库存盘点单明细表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `inventory_check_item` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `check_id` BIGINT NOT NULL COMMENT '盘点单ID',
  `material_code` VARCHAR(50) NOT NULL COMMENT '物料编码',
  `material_name` VARCHAR(200) NOT NULL COMMENT '物料名称',
  `specification` VARCHAR(200) COMMENT '规格型号',
  `unit` VARCHAR(20) COMMENT '单位',
  `location_id` BIGINT COMMENT '货位ID',
  `system_quantity` DECIMAL(18, 2) NOT NULL COMMENT '系统数量',
  `actual_quantity` DECIMAL(18, 2) COMMENT '实盘数量',
  `diff_quantity` DECIMAL(18, 2) COMMENT '差异数量',
  `diff_amount` DECIMAL(18, 2) COMMENT '差异金额',
  `unit_price` DECIMAL(18, 2) COMMENT '单价',
  `reason` VARCHAR(500) COMMENT '差异原因',
  `handle_method` TINYINT COMMENT '处理方式 1-调整库存 2-报废 3-其他',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_check_id` (`check_id`),
  KEY `idx_material_code` (`material_code`),
  KEY `idx_location_id` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存盘点单明细表';

-- ----------------------------
-- 10. 库存预警表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `inventory_alert` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `warehouse_id` BIGINT NOT NULL COMMENT '仓库ID',
  `material_code` VARCHAR(50) NOT NULL COMMENT '物料编码',
  `material_name` VARCHAR(200) NOT NULL COMMENT '物料名称',
  `specification` VARCHAR(200) COMMENT '规格型号',
  `current_stock` VARCHAR(50) COMMENT '当前库存',
  `alert_type` TINYINT NOT NULL COMMENT '预警类型 1-库存下限 2-库存上限 3-库存呆滞 4-库存过期 5-库存短缺',
  `alert_level` TINYINT NOT NULL COMMENT '预警级别 1-普通 2-重要 3-紧急',
  `alert_value` VARCHAR(50) COMMENT '预警值',
  `actual_value` VARCHAR(50) COMMENT '实际值',
  `diff_value` VARCHAR(50) COMMENT '差值',
  `alert_content` TEXT COMMENT '预警内容',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态 0-未处理 1-已处理',
  `handler` VARCHAR(50) COMMENT '处理人',
  `handle_time` DATETIME COMMENT '处理时间',
  `handle_remark` VARCHAR(500) COMMENT '处理备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记 0-未删除 1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_material_code` (`material_code`),
  KEY `idx_alert_type` (`alert_type`),
  KEY `idx_alert_level` (`alert_level`),
  KEY `idx_status` (`status`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存预警表';

-- ----------------------------
-- 初始化数据
-- ----------------------------
-- 插入仓库类型字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('warehouse_type', '原材料仓', '1', 1, '1', NOW()),
('warehouse_type', '成品仓', '2', 2, '1', NOW()),
('warehouse_type', '半成品仓', '3', 3, '1', NOW()),
('warehouse_type', '退货仓', '4', 4, '1', NOW()),
('warehouse_type', '废品仓', '5', 5, '1', NOW());

-- 插入货位类型字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('location_type', '普通货位', '1', 1, '1', NOW()),
('location_type', '冷藏货位', '2', 2, '1', NOW()),
('location_type', '危险品货位', '3', 3, '1', NOW()),
('location_type', '大件货位', '4', 4, '1', NOW());

-- 插入库类型字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('inbound_type', '采购入库', '1', 1, '1', NOW()),
('inbound_type', '生产入库', '2', 2, '1', NOW()),
('inbound_type', '退货入库', '3', 3, '1', NOW()),
('inbound_type', '调拨入库', '4', 4, '1', NOW()),
('inbound_type', '其他入库', '5', 5, '1', NOW());

-- 插入出库类型字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('outbound_type', '销售出库', '1', 1, '1', NOW()),
('outbound_type', '生产领料', '2', 2, '1', NOW()),
('outbound_type', '调拨出库', '3', 3, '1', NOW()),
('outbound_type', '退货出库', '4', 4, '1', NOW()),
('outbound_type', '报废出库', '5', 5, '1', NOW()),
('outbound_type', '其他出库', '6', 6, '1', NOW());

-- 插入入库单状态字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('inbound_status', '待入库', '0', 1, '1', NOW()),
('inbound_status', '入库中', '1', 2, '1', NOW()),
('inbound_status', '已入库', '2', 3, '1', NOW()),
('inbound_status', '已完成', '3', 4, '1', NOW());

-- 插入出库单状态字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('outbound_status', '待出库', '0', 1, '1', NOW()),
('outbound_status', '出库中', '1', 2, '1', NOW()),
('outbound_status', '已出库', '2', 3, '1', NOW()),
('outbound_status', '已完成', '3', 4, '1', NOW());

-- 插入盘点类型字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('check_type', '全盘', '1', 1, '1', NOW()),
('check_type', '抽样盘点', '2', 2, '1', NOW()),
('check_type', '循环盘点', '3', 3, '1', NOW());

-- 插入盘点状态字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('check_status', '待盘点', '0', 1, '1', NOW()),
('check_status', '盘点中', '1', 2, '1', NOW()),
('check_status', '盘点完成', '2', 3, '1', NOW()),
('check_status', '已审核', '3', 4, '1', NOW());

-- 插入预警类型字典数据
INSERT IGNORE INTO `dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `created_at`) VALUES
('alert_type', '库存下限', '1', 1, '1', NOW()),
('alert_type', '库存上限', '2', 2, '1', NOW()),
('alert_type', '库存呆滞', '3', 3, '1', NOW()),
('alert_type', '库存过期', '4', 4, '1', NOW()),
('alert_type', '库存短缺', '5', 5, '1', NOW());
