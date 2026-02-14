package com.qoobot.openscm.logistics.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 物流轨迹实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("logistics_tracking")
public class LogisticsTracking {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 轨迹编号
     */
    private String trackingNo;

    /**
     * 关联运输计划ID
     */
    private Long planId;

    /**
     * 关联配送单ID
     */
    private Long deliveryId;

    /**
     * 运单号
     */
    private String waybillNo;

    /**
     * 物流节点（0-已下单，1-已取件，2-运输中，3-派送中，4-已签收，5-异常）
     */
    private Integer node;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点描述
     */
    private String nodeDesc;

    /**
     * 当前位置
     */
    private String currentLocation;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 节点时间
     */
    private LocalDateTime nodeTime;

    /**
     * 轨迹说明
     */
    private String description;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
