package com.qoobot.openscm.auth.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 租户实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tenant")
public class Tenant {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 租户类型（0-企业版，1-个人版，2-试用版）
     */
    private Integer tenantType;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 所在行业
     */
    private String industry;

    /**
     * 公司规模
     */
    private String companyScale;

    /**
     * 租户状态（0-禁用，1-启用，2-过期）
     */
    private Integer status;

    /**
     * 到期日期
     */
    private LocalDate expireDate;

    /**
     * 最大用户数
     */
    private Integer maxUsers;

    /**
     * 当前用户数
     */
    private Integer currentUsers;

    /**
     * 存储配额（GB）
     */
    private Integer storageQuota;

    /**
     * 已用存储（GB）
     */
    private Integer usedStorage;

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
