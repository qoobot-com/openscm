-- ================================================================
-- OpenSCM 认证授权模块数据库表结构
-- ================================================================

-- OAuth客户端表
CREATE TABLE IF NOT EXISTS `oauth_client` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `client_id` VARCHAR(100) NOT NULL COMMENT '客户端ID',
    `client_secret` VARCHAR(200) NOT NULL COMMENT '客户端密钥',
    `client_name` VARCHAR(100) NOT NULL COMMENT '客户端名称',
    `grant_types` VARCHAR(200) NOT NULL COMMENT '授权类型',
    `scopes` VARCHAR(200) NOT NULL COMMENT '授权范围',
    `redirect_uris` VARCHAR(500) COMMENT '重定向URI',
    `access_token_validity` INT NOT NULL DEFAULT 3600 COMMENT '访问令牌有效期（秒）',
    `refresh_token_validity` INT NOT NULL DEFAULT 2592000 COMMENT '刷新令牌有效期（秒）',
    `trusted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否信任',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '客户端状态（0-禁用，1-启用）',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_client_id` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='OAuth客户端表';

-- 租户表
CREATE TABLE IF NOT EXISTS `tenant` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_code` VARCHAR(50) NOT NULL COMMENT '租户编码',
    `tenant_name` VARCHAR(100) NOT NULL COMMENT '租户名称',
    `tenant_type` TINYINT NOT NULL DEFAULT 0 COMMENT '租户类型（0-企业版，1-个人版，2-试用版）',
    `contact_person` VARCHAR(50) COMMENT '联系人',
    `contact_phone` VARCHAR(20) COMMENT '联系电话',
    `contact_email` VARCHAR(100) COMMENT '联系邮箱',
    `industry` VARCHAR(100) COMMENT '所在行业',
    `company_scale` VARCHAR(50) COMMENT '公司规模',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '租户状态（0-禁用，1-启用，2-过期）',
    `expire_date` DATE COMMENT '到期日期',
    `max_users` INT COMMENT '最大用户数',
    `current_users` INT NOT NULL DEFAULT 0 COMMENT '当前用户数',
    `storage_quota` INT COMMENT '存储配额（GB）',
    `used_storage` INT NOT NULL DEFAULT 0 COMMENT '已用存储（GB）',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_code` (`tenant_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='租户表';

-- 第三方登录表
CREATE TABLE IF NOT EXISTS `third_party_login` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `tenant_id` BIGINT COMMENT '租户ID',
    `login_type` TINYINT NOT NULL COMMENT '登录类型（0-微信，1-钉钉，2-企业微信）',
    `third_party_user_id` VARCHAR(100) NOT NULL COMMENT '第三方用户ID',
    `third_party_nickname` VARCHAR(100) COMMENT '第三方用户昵称',
    `third_party_avatar` VARCHAR(500) COMMENT '第三方用户头像',
    `union_id` VARCHAR(100) COMMENT '联合ID',
    `access_token` VARCHAR(500) COMMENT '访问令牌',
    `refresh_token` VARCHAR(500) COMMENT '刷新令牌',
    `token_expire_time` DATETIME COMMENT '令牌过期时间',
    `bind_status` TINYINT NOT NULL DEFAULT 0 COMMENT '绑定状态（0-未绑定，1-已绑定）',
    `last_login_time` DATETIME COMMENT '最后登录时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_third_party` (`login_type`, `third_party_user_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='第三方登录表';

-- 登录日志表
CREATE TABLE IF NOT EXISTS `login_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT COMMENT '用户ID',
    `tenant_id` BIGINT COMMENT '租户ID',
    `username` VARCHAR(50) COMMENT '用户名',
    `login_ip` VARCHAR(50) COMMENT '登录IP',
    `login_location` VARCHAR(200) COMMENT '登录地点',
    `browser` VARCHAR(100) COMMENT '浏览器',
    `os` VARCHAR(100) COMMENT '操作系统',
    `login_type` TINYINT NOT NULL COMMENT '登录类型（0-账号密码，1-微信，2-钉钉，3-企业微信）',
    `status` TINYINT NOT NULL COMMENT '登录状态（0-成功，1-失败）',
    `message` VARCHAR(500) COMMENT '登录消息',
    `login_time` DATETIME NOT NULL COMMENT '登录时间',
    `logout_time` DATETIME COMMENT '登出时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_login_time` (`login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录日志表';

-- 数据权限表
CREATE TABLE IF NOT EXISTS `data_permission` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `permission_name` VARCHAR(100) NOT NULL COMMENT '权限名称',
    `permission_code` VARCHAR(100) NOT NULL COMMENT '权限编码',
    `permission_type` TINYINT NOT NULL COMMENT '权限类型（0-部门数据，1-个人数据，2-全部数据，3-自定义）',
    `dept_id` BIGINT COMMENT '部门ID',
    `dept_ids` VARCHAR(500) COMMENT '部门ID列表',
    `user_ids` VARCHAR(500) COMMENT '用户ID列表',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_permission_code` (`permission_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据权限表';

-- 登录安全配置表
CREATE TABLE IF NOT EXISTS `login_security_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` BIGINT COMMENT '租户ID',
    `config_type` TINYINT NOT NULL DEFAULT 0 COMMENT '配置类型（0-全局，1-租户）',
    `enable_mfa` TINYINT NOT NULL DEFAULT 0 COMMENT '是否启用多因素认证',
    `max_login_attempts` INT NOT NULL DEFAULT 5 COMMENT '最大登录失败次数',
    `lockout_duration` INT NOT NULL DEFAULT 30 COMMENT '账号锁定时间（分钟）',
    `min_password_length` INT NOT NULL DEFAULT 6 COMMENT '密码最小长度',
    `max_password_length` INT NOT NULL DEFAULT 20 COMMENT '密码最大长度',
    `require_special_char` TINYINT NOT NULL DEFAULT 0 COMMENT '密码是否包含特殊字符',
    `require_number` TINYINT NOT NULL DEFAULT 1 COMMENT '密码是否包含数字',
    `require_uppercase` TINYINT NOT NULL DEFAULT 0 COMMENT '密码是否包含大写字母',
    `require_lowercase` TINYINT NOT NULL DEFAULT 1 COMMENT '密码是否包含小写字母',
    `password_expire_days` INT COMMENT '密码有效期（天）',
    `session_timeout` INT NOT NULL DEFAULT 120 COMMENT '会话超时时间（分钟）',
    `enable_login_log` TINYINT NOT NULL DEFAULT 1 COMMENT '是否记录登录日志',
    `enable_ip_whitelist` TINYINT NOT NULL DEFAULT 0 COMMENT '是否启用IP白名单',
    `ip_whitelist` VARCHAR(1000) COMMENT 'IP白名单',
    `enable_ip_blacklist` TINYINT NOT NULL DEFAULT 0 COMMENT '是否启用IP黑名单',
    `ip_blacklist` VARCHAR(1000) COMMENT 'IP黑名单',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录安全配置表';

-- ================================================================
-- 初始化字典数据
-- ================================================================

-- 租户类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('tenant_type', '企业版', '0', 1, '0', NOW()),
('tenant_type', '个人版', '1', 2, '0', NOW()),
('tenant_type', '试用版', '2', 3, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 租户状态字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('tenant_status', '禁用', '0', 1, '0', NOW()),
('tenant_status', '启用', '1', 2, '0', NOW()),
('tenant_status', '过期', '2', 3, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 第三方登录类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('third_party_type', '微信', '0', 1, '0', NOW()),
('third_party_type', '钉钉', '1', 2, '0', NOW()),
('third_party_type', '企业微信', '2', 3, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 登录类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('login_type', '账号密码', '0', 1, '0', NOW()),
('login_type', '微信', '1', 2, '0', NOW()),
('login_type', '钉钉', '2', 3, '0', NOW()),
('login_type', '企业微信', '3', 4, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 数据权限类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('data_permission_type', '部门数据', '0', 1, '0', NOW()),
('data_permission_type', '个人数据', '1', 2, '0', NOW()),
('data_permission_type', '全部数据', '2', 3, '0', NOW()),
('data_permission_type', '自定义', '3', 4, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);

-- 配置类型字典
INSERT INTO `sys_dict_data` (`dict_type`, `dict_label`, `dict_value`, `dict_sort`, `status`, `create_time`) VALUES
('config_type', '全局', '0', 1, '0', NOW()),
('config_type', '租户', '1', 2, '0', NOW())
ON DUPLICATE KEY UPDATE `dict_label` = VALUES(`dict_label`);
