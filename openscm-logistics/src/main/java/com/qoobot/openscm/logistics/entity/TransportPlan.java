package com.qoobot.openscm.logistics.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 运输计划实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("transport_plan")
public class TransportPlan {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 运输计划编号
     */
    private String planNo;

    /**
     * 关联订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 运输类型（0-自提，1-配送，2-快递）
     */
    private Integer transportType;

    /**
     * 起始地址
     */
    private String startAddress;

    /**
     * 目的地址
     */
    private String destinationAddress;

    /**
     * 预计出发时间
     */
    private LocalDateTime estimatedDepartureTime;

    /**
     * 预计到达时间
     */
    private LocalDateTime estimatedArrivalTime;

    /**
     * 实际出发时间
     */
    private LocalDateTime actualDepartureTime;

    /**
     * 实际到达时间
     */
    private LocalDateTime actualArrivalTime;

    /**
     * 运输距离（公里）
     */
    private BigDecimal distance;

    /**
     * 运输费用
     */
    private BigDecimal transportFee;

    /**
     * 运输状态（0-待出发，1-运输中，2-已送达，3-已取消）
     */
    private Integer status;

    /**
     * 运输车辆信息
     */
    private String vehicleInfo;

    /**
     * 司机信息
     */
    private String driverInfo;

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
