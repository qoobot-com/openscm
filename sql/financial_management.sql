-- ================================================================
-- OpenSCM 财务管理系统数据库表结构
-- ================================================================

-- 应收账款表
CREATE TABLE IF NOT EXISTS `accounts_receivable` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `receivable_no` VARCHAR(50) NOT NULL COMMENT '应收单号',
    `order_id` BIGINT COMMENT '关联订单ID',
    `order_no` VARCHAR(50) COMMENT '订单编号',
    `customer_id` BIGINT NOT NULL COMMENT '客户ID',
    `customer_name` VARCHAR(100) NOT NULL COMMENT '客户名称',
    `amount` DECIMAL(18,2) NOT NULL COMMENT '应收金额',
    `received_amount` DECIMAL(18,2) NOT NULL DEFAULT 0.00 COMMENT '已收金额',
    `unpaid_amount` DECIMAL(18,2) NOT NULL COMMENT '未收金额',
    `receivable_date` DATE NOT NULL COMMENT '应收日期',
    `due_date` DATE COMMENT '到期日期',
    `settlement_status` TINYINT NOT NULL DEFAULT 0 COMMENT '结算状态（0-未结算，1-部分结算，2-已结算）',
    `business_type` TINYINT NOT NULL DEFAULT 0 COMMENT '业务类型（0-销售订单，1-退货退款）',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_receivable_no` (`receivable_no`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_customer_id` (`customer_id`),
    KEY `idx_settlement_status` (`settlement_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应收账款表';

-- 应付账款表
CREATE TABLE IF NOT EXISTS `accounts_payable` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `payable_no` VARCHAR(50) NOT NULL COMMENT '应付单号',
    `purchase_order_id` BIGINT COMMENT '关联采购订单ID',
    `purchase_order_no` VARCHAR(50) COMMENT '采购订单编号',
    `supplier_id` BIGINT NOT NULL COMMENT '供应商ID',
    `supplier_name` VARCHAR(100) NOT NULL COMMENT '供应商名称',
    `amount` DECIMAL(18,2) NOT NULL COMMENT '应付金额',
    `paid_amount` DECIMAL(18,2) NOT NULL DEFAULT 0.00 COMMENT '已付金额',
    `unpaid_amount` DECIMAL(18,2) NOT NULL COMMENT '未付金额',
    `payable_date` DATE NOT NULL COMMENT '应付日期',
    `due_date` DATE COMMENT '到期日期',
    `settlement_status` TINYINT NOT NULL DEFAULT 0 COMMENT '结算状态（0-未结算，1-部分结算，2-已结算）',
    `business_type` TINYINT NOT NULL DEFAULT 0 COMMENT '业务类型（0-采购订单，1-退货退款）',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_payable_no` (`payable_no`),
    KEY `idx_purchase_order_id` (`purchase_order_id`),
    KEY `idx_supplier_id` (`supplier_id`),
    KEY `idx_settlement_status` (`settlement_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应付账款表';

-- 收付款单表
CREATE TABLE IF NOT EXISTS `payment_order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `payment_no` VARCHAR(50) NOT NULL COMMENT '收付款单号',
    `order_type` TINYINT NOT NULL COMMENT '单据类型（0-收款单，1-付款单）',
    `account_id` BIGINT COMMENT '关联应收应付ID',
    `order_id` BIGINT COMMENT '关联订单ID',
    `order_no` VARCHAR(50) COMMENT '订单编号',
    `partner_id` BIGINT COMMENT '客户/供应商ID',
    `partner_name` VARCHAR(100) COMMENT '客户/供应商名称',
    `amount` DECIMAL(18,2) NOT NULL COMMENT '收付款金额',
    `payment_method` TINYINT NOT NULL COMMENT '收付款方式（0-现金，1-银行转账，2-支票，3-承兑汇票）',
    `payment_date` DATETIME COMMENT '收付款日期',
    `account` VARCHAR(100) COMMENT '收付款账户',
    `voucher_no` VARCHAR(50) COMMENT '凭证号',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '收付款状态（0-待审核，1-已审核，2-已拒绝）',
    `approved_by` VARCHAR(50) COMMENT '审核人',
    `approve_time` DATETIME COMMENT '审核时间',
    `approve_remark` VARCHAR(500) COMMENT '审核意见',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_payment_no` (`payment_no`),
    KEY `idx_account_id` (`account_id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收付款单表';

-- 成本核算表
CREATE TABLE IF NOT EXISTS `cost_accounting` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `accounting_no` VARCHAR(50) NOT NULL COMMENT '核算单号',
    `accounting_period` VARCHAR(20) NOT NULL COMMENT '核算期间',
    `cost_type` TINYINT NOT NULL COMMENT '成本类型（0-采购成本，1-销售成本，2-库存成本，3-运输成本）',
    `order_id` BIGINT COMMENT '关联订单ID',
    `order_no` VARCHAR(50) COMMENT '订单编号',
    `material_id` BIGINT COMMENT '物料ID',
    `material_code` VARCHAR(50) COMMENT '物料编码',
    `material_name` VARCHAR(100) COMMENT '物料名称',
    `quantity` DECIMAL(18,2) COMMENT '数量',
    `unit_cost` DECIMAL(18,2) COMMENT '单位成本',
    `total_cost` DECIMAL(18,2) NOT NULL COMMENT '总成本',
    `accounting_date` DATE NOT NULL COMMENT '核算日期',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_accounting_no` (`accounting_no`),
    KEY `idx_accounting_period` (`accounting_period`),
    KEY `idx_cost_type` (`cost_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='成本核算表';

-- 费用报销表
CREATE TABLE IF NOT EXISTS `expense` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `expense_no` VARCHAR(50) NOT NULL COMMENT '报销单号',
    `applicant` VARCHAR(50) NOT NULL COMMENT '申请人',
    `department` VARCHAR(100) COMMENT '所属部门',
    `expense_type` TINYINT NOT NULL COMMENT '费用类型（0-差旅费，1-招待费，2-办公费，3-交通费，4-其他）',
    `amount` DECIMAL(18,2) NOT NULL COMMENT '费用金额',
    `expense_date` DATE NOT NULL COMMENT '费用日期',
    `expense_desc` VARCHAR(500) COMMENT '费用说明',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '报销状态（0-待审核，1-审核中，2-已审核，3-已拒绝，4-已支付）',
    `approved_by` VARCHAR(50) COMMENT '审核人',
    `approve_time` DATETIME COMMENT '审核时间',
    `approve_remark` VARCHAR(500) COMMENT '审核意见',
    `payment_time` DATETIME COMMENT '支付时间',
    `attachment` VARCHAR(500) COMMENT '附件',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_expense_no` (`expense_no`),
    KEY `idx_applicant` (`applicant`),
    KEY `idx_expense_type` (`expense_type`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='费用报销表';

-- 财务报表表
CREATE TABLE IF NOT EXISTS `financial_report` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `report_no` VARCHAR(50) NOT NULL COMMENT '报表编号',
    `report_type` TINYINT NOT NULL COMMENT '报表类型（0-利润表，1-资产负债表，2-现金流量表）',
    `report_period` VARCHAR(20) NOT NULL COMMENT '报表期间',
    `report_name` VARCHAR(100) NOT NULL COMMENT '报表名称',
    `income` DECIMAL(18,2) COMMENT '收入',
    `cost` DECIMAL(18,2) COMMENT '成本',
    `expense` DECIMAL(18,2) COMMENT '费用',
    `profit` DECIMAL(18,2) COMMENT '利润',
    `assets` DECIMAL(18,2) COMMENT '资产',
    `liabilities` DECIMAL(18,2) COMMENT '负债',
    `equity` DECIMAL(18,2) COMMENT '所有者权益',
    `operating_cash_flow` DECIMAL(18,2) COMMENT '经营活动现金流',
    `investing_cash_flow` DECIMAL(18,2) COMMENT '投资活动现金流',
    `financing_cash_flow` DECIMAL(18,2) COMMENT '筹资活动现金流',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '报表状态（0-草稿，1-已生成，2-已审核）',
    `generated_by` VARCHAR(50) COMMENT '生成人',
    `generate_time` DATETIME COMMENT '生成时间',
    `approved_by` VARCHAR(50) COMMENT '审核人',
    `approve_time` DATETIME COMMENT '审核时间',
    `remark` VARCHAR(500) COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_report_no` (`report_no`),
    KEY `idx_report_type` (`report_type`),
    KEY `idx_report_period` (`report_period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='财务报表表';

-- ================================================================
-- 初始化字典数据
-- ================================================================

-- 结算状态字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('settlement_status', '未结算', '0', 1, '0', NOW()),
('settlement_status', '部分结算', '1', 2, '0', NOW()),
('settlement_status', '已结算', '2', 3, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 业务类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('finance_business_type', '销售订单', '0', 1, '0', NOW()),
('finance_business_type', '退货退款', '1', 2, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 单据类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('payment_order_type', '收款单', '0', 1, '0', NOW()),
('payment_order_type', '付款单', '1', 2, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 收付款方式字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('finance_payment_method', '现金', '0', 1, '0', NOW()),
('finance_payment_method', '银行转账', '1', 2, '0', NOW()),
('finance_payment_method', '支票', '2', 3, '0', NOW()),
('finance_payment_method', '承兑汇票', '3', 4, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 成本类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('cost_type', '采购成本', '0', 1, '0', NOW()),
('cost_type', '销售成本', '1', 2, '0', NOW()),
('cost_type', '库存成本', '2', 3, '0', NOW()),
('cost_type', '运输成本', '3', 4, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 费用类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('expense_type', '差旅费', '0', 1, '0', NOW()),
('expense_type', '招待费', '1', 2, '0', NOW()),
('expense_type', '办公费', '2', 3, '0', NOW()),
('expense_type', '交通费', '3', 4, '0', NOW()),
('expense_type', '其他', '4', 5, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 报销状态字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('expense_status', '待审核', '0', 1, '0', NOW()),
('expense_status', '审核中', '1', 2, '0', NOW()),
('expense_status', '已审核', '2', 3, '0', NOW()),
('expense_status', '已拒绝', '3', 4, '0', NOW()),
('expense_status', '已支付', '4', 5, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 报表类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('report_type', '利润表', '0', 1, '0', NOW()),
('report_type', '资产负债表', '1', 2, '0', NOW()),
('report_type', '现金流量表', '2', 3, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 报表状态字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('report_status', '草稿', '0', 1, '0', NOW()),
('report_status', '已生成', '1', 2, '0', NOW()),
('report_status', '已审核', '2', 3, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);
