package com.qoobot.openscm.integration.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * ERP系统集成实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("erp_integration")
public class ErpIntegration extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 集成配置名称
     */
    private String integrationName;

    /**
     * ERP系统类型
     */
    private String erpType;

    /**
     * ERP系统版本
     */
    private String erpVersion;

    /**
     * 接口地址
     */
    private String apiUrl;

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 访问秘钥
     */
    private String secretKey;

    /**
     * 数据同步模式
     */
    private String syncMode;

    /**
     * 最后同步时间
     */
    private LocalDateTime lastSyncTime;

    /**
     * 同步状态
     */
    private String syncStatus;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 同步频率
     */
    private String syncFrequency;

    /**
     * 是否启用
     */
    @TableField("enabled")
    private Boolean enabled;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 备注
     */
    private String remark;
}
