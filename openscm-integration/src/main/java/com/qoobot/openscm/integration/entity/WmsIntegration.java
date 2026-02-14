package com.qoobot.openscm.integration.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * WMS系统集成实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_integration")
public class WmsIntegration extends BaseEntity {

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
     * WMS系统类型
     */
    private String wmsType;

    /**
     * WMS系统版本
     */
    private String wmsVersion;

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
     * 仓库ID映射
     */
    private String warehouseMapping;

    /**
     * 货位ID映射
     */
    private String locationMapping;

    /**
     * 物料ID映射
     */
    private String materialMapping;

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
