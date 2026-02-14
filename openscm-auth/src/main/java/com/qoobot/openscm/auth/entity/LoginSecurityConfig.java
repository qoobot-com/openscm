package com.qoobot.openscm.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 登录安全配置实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("login_security_config")
public class LoginSecurityConfig {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 配置类型（0-全局，1-租户）
     */
    private Integer configType;

    /**
     * 是否启用多因素认证
     */
    private Boolean enableMfa;

    /**
     * 最大登录失败次数
     */
    private Integer maxLoginAttempts;

    /**
     * 账号锁定时间（分钟）
     */
    private Integer lockoutDuration;

    /**
     * 密码最小长度
     */
    private Integer minPasswordLength;

    /**
     * 密码最大长度
     */
    private Integer maxPasswordLength;

    /**
     * 密码是否包含特殊字符
     */
    private Boolean requireSpecialChar;

    /**
     * 密码是否包含数字
     */
    private Boolean requireNumber;

    /**
     * 密码是否包含大写字母
     */
    private Boolean requireUppercase;

    /**
     * 密码是否包含小写字母
     */
    private Boolean requireLowercase;

    /**
     * 密码有效期（天）
     */
    private Integer passwordExpireDays;

    /**
     * 会话超时时间（分钟）
     */
    private Integer sessionTimeout;

    /**
     * 是否记录登录日志
     */
    private Boolean enableLoginLog;

    /**
     * 是否启用IP白名单
     */
    private Boolean enableIpWhitelist;

    /**
     * IP白名单
     */
    private String ipWhitelist;

    /**
     * 是否启用IP黑名单
     */
    private Boolean enableIpBlacklist;

    /**
     * IP黑名单
     */
    private String ipBlacklist;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
