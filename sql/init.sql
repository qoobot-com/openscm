-- ========================================
-- OpenSCM 数据库初始化脚本
-- ========================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `openscm` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `openscm`;

-- 用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` BIGINT NOT NULL COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) NOT NULL COMMENT '昵称',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像',
  `dept_id` BIGINT DEFAULT NULL COMMENT '部门ID',
  `status` TINYINT NOT NULL DEFAULT '1' COMMENT '状态（0-禁用，1-正常）',
  `deleted` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `updated_by` BIGINT DEFAULT NULL COMMENT '更新人ID',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_dept_id` (`dept_id`),
  KEY `idx_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` BIGINT NOT NULL COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `role_code` VARCHAR(50) NOT NULL COMMENT '角色编码',
  `data_scope` TINYINT NOT NULL DEFAULT '1' COMMENT '数据范围（1-全部，2-本部门，3-本部门及以下，4-仅本人）',
  `status` TINYINT NOT NULL DEFAULT '1' COMMENT '状态（0-禁用，1-正常）',
  `sort_order` INT DEFAULT '0' COMMENT '排序',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `updated_by` BIGINT DEFAULT NULL COMMENT '更新人ID',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 用户角色关联表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` BIGINT NOT NULL COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `deleted` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- 菜单表
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` BIGINT NOT NULL COMMENT '菜单ID',
  `parent_id` BIGINT NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `menu_name` VARCHAR(50) NOT NULL COMMENT '菜单名称',
  `menu_type` TINYINT NOT NULL COMMENT '菜单类型（1-目录，2-菜单，3-按钮）',
  `menu_code` VARCHAR(50) DEFAULT NULL COMMENT '菜单编码',
  `path` VARCHAR(200) DEFAULT NULL COMMENT '路由路径',
  `component` VARCHAR(200) DEFAULT NULL COMMENT '组件路径',
  `icon` VARCHAR(100) DEFAULT NULL COMMENT '图标',
  `permission` VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
  `sort_order` INT DEFAULT '0' COMMENT '排序',
  `visible` TINYINT NOT NULL DEFAULT '1' COMMENT '显示状态（0-隐藏，1-显示）',
  `status` TINYINT NOT NULL DEFAULT '1' COMMENT '状态（0-禁用，1-正常）',
  `deleted` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `updated_by` BIGINT DEFAULT NULL COMMENT '更新人ID',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单表';

-- 角色菜单关联表
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` BIGINT NOT NULL COMMENT '主键ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
  `deleted` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_menu` (`role_id`, `menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关联表';

-- 部门表
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` BIGINT NOT NULL COMMENT '部门ID',
  `parent_id` BIGINT NOT NULL DEFAULT '0' COMMENT '父部门ID',
  `dept_name` VARCHAR(50) NOT NULL COMMENT '部门名称',
  `dept_code` VARCHAR(50) NOT NULL COMMENT '部门编码',
  `leader` VARCHAR(50) DEFAULT NULL COMMENT '负责人',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `sort_order` INT DEFAULT '0' COMMENT '排序',
  `status` TINYINT NOT NULL DEFAULT '1' COMMENT '状态（0-禁用，1-正常）',
  `deleted` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `updated_by` BIGINT DEFAULT NULL COMMENT '更新人ID',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dept_code` (`dept_code`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';

-- 字典类型表
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` BIGINT NOT NULL COMMENT '字典类型ID',
  `dict_name` VARCHAR(100) NOT NULL COMMENT '字典名称',
  `dict_code` VARCHAR(100) NOT NULL COMMENT '字典编码',
  `status` TINYINT NOT NULL DEFAULT '1' COMMENT '状态（0-禁用，1-正常）',
  `deleted` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `updated_by` BIGINT DEFAULT NULL COMMENT '更新人ID',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dict_code` (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典类型表';

-- 字典数据表
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `id` BIGINT NOT NULL COMMENT '字典数据ID',
  `dict_type_id` BIGINT NOT NULL COMMENT '字典类型ID',
  `dict_label` VARCHAR(100) NOT NULL COMMENT '字典标签',
  `dict_value` VARCHAR(100) NOT NULL COMMENT '字典值',
  `dict_sort` INT DEFAULT '0' COMMENT '排序',
  `css_class` VARCHAR(100) DEFAULT NULL COMMENT 'CSS样式',
  `list_class` VARCHAR(100) DEFAULT NULL COMMENT '表格样式',
  `is_default` TINYINT NOT NULL DEFAULT '0' COMMENT '是否默认（0-否，1-是）',
  `status` TINYINT NOT NULL DEFAULT '1' COMMENT '状态（0-禁用，1-正常）',
  `deleted` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `updated_by` BIGINT DEFAULT NULL COMMENT '更新人ID',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_dict_type_id` (`dict_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典数据表';

-- 操作日志表
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log` (
  `id` BIGINT NOT NULL COMMENT '日志ID',
  `module` VARCHAR(50) DEFAULT NULL COMMENT '模块标题',
  `business_type` TINYINT DEFAULT NULL COMMENT '业务类型（0-其它，1-新增，2-修改，3-删除，4-授权，5-导出，6-导入，7-强退，8-生成代码，9-清空数据）',
  `method` VARCHAR(100) DEFAULT NULL COMMENT '方法名称',
  `request_method` VARCHAR(10) DEFAULT NULL COMMENT '请求方式',
  `operator_type` TINYINT DEFAULT '0' COMMENT '操作类别（0-其它，1-后台用户，2-手机端用户）',
  `oper_name` VARCHAR(50) DEFAULT NULL COMMENT '操作人员',
  `dept_name` VARCHAR(50) DEFAULT NULL COMMENT '部门名称',
  `oper_url` VARCHAR(255) DEFAULT NULL COMMENT '请求URL',
  `oper_ip` VARCHAR(128) DEFAULT NULL COMMENT '主机地址',
  `oper_location` VARCHAR(255) DEFAULT NULL COMMENT '操作地点',
  `oper_param` TEXT DEFAULT NULL COMMENT '请求参数',
  `json_result` TEXT DEFAULT NULL COMMENT '返回参数',
  `status` TINYINT NOT NULL DEFAULT '0' COMMENT '操作状态（0-正常，1-异常）',
  `error_msg` VARCHAR(2000) DEFAULT NULL COMMENT '错误消息',
  `oper_time` DATETIME DEFAULT NULL COMMENT '操作时间',
  `cost_time` BIGINT DEFAULT NULL COMMENT '消耗时间（毫秒）',
  `deleted` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  PRIMARY KEY (`id`),
  KEY `idx_oper_time` (`oper_time`),
  KEY `idx_oper_name` (`oper_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';

-- 插入初始数据
-- 管理员用户（密码：admin123，使用BCrypt加密）
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `mobile`, `status`, `created_by`, `create_time`) VALUES
(1, 'admin', '$2a$10$7JB720yubVSqy5x3x3xqOeS5v6v7v8v9x1x2x3x4x5x6x7x8x9x0x', '管理员', 'admin@openscm.com', '13800138000', 1, 1, NOW());

-- 超级管理员角色
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `data_scope`, `status`, `sort_order`, `remark`, `created_by`, `create_time`) VALUES
(1, '超级管理员', 'admin', 1, 1, 1, '超级管理员，拥有所有权限', 1, NOW());

-- 用户角色关联
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `created_by`, `create_time`) VALUES
(1, 1, 1, 1, NOW());

-- 根部门
INSERT INTO `sys_dept` (`id`, `parent_id`, `dept_name`, `dept_code`, `leader`, `phone`, `email`, `sort_order`, `status`, `created_by`, `create_time`) VALUES
(1, 0, 'OpenSCM', 'openscm', 'admin', '13800138000', 'admin@openscm.com', 1, 1, 1, NOW());

-- 系统菜单
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_type`, `menu_code`, `path`, `component`, `icon`, `permission`, `sort_order`, `visible`, `status`, `created_by`, `create_time`) VALUES
(1, 0, '系统管理', 1, 'system', '/system', NULL, 'system', NULL, 1, 1, 1, 1, NOW()),
(2, 1, '用户管理', 2, 'user', '/system/user', 'system/user/index', 'user', 'system:user:list', 1, 1, 1, 1, NOW()),
(3, 1, '角色管理', 2, 'role', '/system/role', 'system/role/index', 'role', 'system:role:list', 2, 1, 1, 1, NOW()),
(4, 1, '菜单管理', 2, 'menu', '/system/menu', 'system/menu/index', 'menu', 'system:menu:list', 3, 1, 1, 1, NOW()),
(5, 1, '部门管理', 2, 'dept', '/system/dept', 'system/dept/index', 'dept', 'system:dept:list', 4, 1, 1, 1, NOW());

-- ========================================
-- 供应商管理相关表
-- ========================================

-- 供应商表
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  `supplier_code` VARCHAR(50) NOT NULL COMMENT '供应商编码',
  `supplier_name` VARCHAR(100) NOT NULL COMMENT '供应商名称',
  `supplier_type` TINYINT NOT NULL COMMENT '供应商类型（1-原料 2-辅料 3-设备 4-服务）',
  `contact_person` VARCHAR(50) DEFAULT NULL COMMENT '联系人',
  `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
  `contact_email` VARCHAR(100) DEFAULT NULL COMMENT '联系邮箱',
  `address` VARCHAR(255) DEFAULT NULL COMMENT '供应商地址',
  `supplier_grade` TINYINT DEFAULT '2' COMMENT '供应商等级（1-A级 2-B级 3-C级 4-D级）',
  `credit_limit` DECIMAL(15,2) DEFAULT '0.00' COMMENT '信用额度',
  `cooperation_start_date` DATE DEFAULT NULL COMMENT '合作开始日期',
  `license_number` VARCHAR(50) DEFAULT NULL COMMENT '营业执照号',
  `tax_number` VARCHAR(50) DEFAULT NULL COMMENT '税务登记号',
  `bank_account` VARCHAR(50) DEFAULT NULL COMMENT '银行账号',
  `bank_name` VARCHAR(100) DEFAULT NULL COMMENT '开户银行',
  `status` TINYINT NOT NULL DEFAULT '1' COMMENT '供应商状态（0-停用 1-正常 2-审核中 3-黑名单）',
  `audit_status` TINYINT NOT NULL DEFAULT '0' COMMENT '审核状态（0-待审核 1-已通过 2-已拒绝）',
  `auditor_id` BIGINT DEFAULT NULL COMMENT '审核人ID',
  `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
  `audit_remark` VARCHAR(500) DEFAULT NULL COMMENT '审核意见',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `del_flag` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` BIGINT DEFAULT NULL COMMENT '更新人ID',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_supplier_code` (`supplier_code`),
  KEY `idx_supplier_name` (`supplier_name`),
  KEY `idx_supplier_type` (`supplier_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='供应商表';

-- 供应商分类表
DROP TABLE IF EXISTS `supplier_category`;
CREATE TABLE `supplier_category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_code` VARCHAR(50) NOT NULL COMMENT '分类编码',
  `category_name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `parent_id` BIGINT NOT NULL DEFAULT '0' COMMENT '上级分类ID',
  `level` TINYINT NOT NULL DEFAULT '1' COMMENT '分类层级',
  `sort_order` INT DEFAULT '0' COMMENT '排序',
  `status` TINYINT NOT NULL DEFAULT '1' COMMENT '状态（0-停用 1-正常）',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `del_flag` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` BIGINT DEFAULT NULL COMMENT '更新人ID',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_category_code` (`category_code`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='供应商分类表';

-- 供应商绩效评估表
DROP TABLE IF EXISTS `supplier_assessment`;
CREATE TABLE `supplier_assessment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评估ID',
  `supplier_id` BIGINT NOT NULL COMMENT '供应商ID',
  `assessment_period` VARCHAR(7) NOT NULL COMMENT '评估周期（年月）',
  `quality_score` DECIMAL(5,2) DEFAULT '0.00' COMMENT '质量得分',
  `delivery_score` DECIMAL(5,2) DEFAULT '0.00' COMMENT '交付得分',
  `service_score` DECIMAL(5,2) DEFAULT '0.00' COMMENT '服务得分',
  `price_score` DECIMAL(5,2) DEFAULT '0.00' COMMENT '价格得分',
  `total_score` DECIMAL(5,2) DEFAULT '0.00' COMMENT '综合得分',
  `assessment_grade` CHAR(1) DEFAULT NULL COMMENT '评估等级（A/B/C/D）',
  `assessment_remark` VARCHAR(500) DEFAULT NULL COMMENT '评估说明',
  `assessor_id` BIGINT DEFAULT NULL COMMENT '评估人ID',
  `del_flag` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` BIGINT DEFAULT NULL COMMENT '更新人ID',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_assessment_period` (`assessment_period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='供应商绩效评估表';

-- 供应商黑名单表
DROP TABLE IF EXISTS `supplier_blacklist`;
CREATE TABLE `supplier_blacklist` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '黑名单ID',
  `supplier_id` BIGINT NOT NULL COMMENT '供应商ID',
  `supplier_name` VARCHAR(100) NOT NULL COMMENT '供应商名称',
  `reason` VARCHAR(500) NOT NULL COMMENT '列入黑名单原因',
  `blacklist_date` DATE NOT NULL COMMENT '列入黑名单日期',
  `expected_release_date` DATE DEFAULT NULL COMMENT '预计解除日期',
  `release_date` DATE DEFAULT NULL COMMENT '解除日期',
  `status` TINYINT NOT NULL DEFAULT '1' COMMENT '状态（0-已解除 1-生效中）',
  `handler_id` BIGINT DEFAULT NULL COMMENT '处理人ID',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `del_flag` TINYINT NOT NULL DEFAULT '0' COMMENT '删除标记（0-正常，1-删除）',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` BIGINT DEFAULT NULL COMMENT '更新人ID',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='供应商黑名单表';

-- 插入供应商菜单
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_type`, `menu_code`, `path`, `component`, `icon`, `permission`, `sort_order`, `visible`, `status`, `created_by`, `create_time`) VALUES
(10, 0, '供应商管理', 1, 'supplier', '/supplier', NULL, 'shopping', NULL, 2, 1, 1, 1, NOW()),
(11, 10, '供应商信息', 2, 'supplier-info', '/supplier/info', 'supplier/info/index', 'list', 'supplier:info:list', 1, 1, 1, 1, NOW()),
(12, 10, '供应商分类', 2, 'supplier-category', '/supplier/category', 'supplier/category/index', 'tree', 'supplier:category:list', 2, 1, 1, 1, NOW()),
(13, 10, '绩效评估', 2, 'supplier-assessment', '/supplier/assessment', 'supplier/assessment/index', 'chart', 'supplier:assessment:list', 3, 1, 1, 1, NOW()),
(14, 10, '黑名单', 2, 'supplier-blacklist', '/supplier/blacklist', 'supplier/blacklist/index', 'warning', 'supplier:blacklist:list', 4, 1, 1, 1, NOW());

-- 插入示例数据
INSERT INTO `supplier` (`supplier_code`, `supplier_name`, `supplier_type`, `contact_person`, `contact_phone`, `contact_email`, `address`, `supplier_grade`, `credit_limit`, `cooperation_start_date`, `license_number`, `tax_number`, `bank_account`, `bank_name`, `status`, `audit_status`, `created_by`) VALUES
('SUP202602150001', 'XX原料供应商', 1, '张三', '13800138001', 'zhangsan@example.com', '北京市朝阳区XX路XX号', 1, 1000000.00, '2025-01-01', '91110000XXXXXXXXXX', '110000000000000', '1234567890123456', '工商银行', 1, 1, 1),
('SUP202602150002', 'YY辅料供应商', 2, '李四', '13800138002', 'lisi@example.com', '上海市浦东新区XX路XX号', 2, 500000.00, '2025-03-01', '91310000XXXXXXXXXX', '310000000000000', '9876543210987654', '建设银行', 2, 0, 1),
('SUP202602150003', 'ZZ设备供应商', 3, '王五', '13800138003', 'wangwu@example.com', '广州市天河区XX路XX号', 1, 2000000.00, '2024-06-01', '91440000XXXXXXXXXX', '440000000000000', '5555444433332222', '农业银行', 1, 1, 1);

