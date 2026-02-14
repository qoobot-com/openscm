package com.qoobot.openscm.integration.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * TMS系统集成实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tms_integration")
public class TmsIntegration extends BaseEntity {

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
     * TMS系统类型
     */
    private String tmsType;

    /**
     * TMS系统版本
     */
    private String tmsVersion;

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
     * 承运商ID映射
     */
    private String carrierMapping;

    /**
     * 车辆ID映射
     */
    private String vehicleMapping;

    /**
     * 驾驶员ID映射
     */
    private String driverMapping;

    /**
     * 最后同步时间
     */
    private LocalDateTime lastSyncTime;

    /**
     * 同步状态
     */
    private String syncStatus;

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
