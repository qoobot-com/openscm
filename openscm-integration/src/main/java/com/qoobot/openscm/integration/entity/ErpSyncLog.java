package com.qoobot.openscm.integration.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * ERP同步日志实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("erp_sync_log")
public class ErpSyncLog extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * ERP配置ID
     */
    private Long erpConfigId;

    /**
     * 同步类型
     */
    private String syncType;

    /**
     * 同步方向
     */
    private String syncDirection;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 业务单号
     */
    private String businessNo;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 同步状态
     */
    private String syncStatus;

    /**
     * 成功数量
     */
    private Integer successCount;

    /**
     * 失败数量
     */
    private Integer failureCount;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 同步数据内容
     */
    private String syncContent;

    /**
     * 租户ID
     */
    private Long tenantId;
}
