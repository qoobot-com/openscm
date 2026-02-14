package com.qoobot.openscm.logistics.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 配送单实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("delivery_order")
public class DeliveryOrder {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 配送单号
     */
    private String deliveryNo;

    /**
     * 运输计划ID
     */
    private Long planId;

    /**
     * 关联订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 配送类型（0-普通配送，1-加急配送，2-预约配送）
     */
    private Integer deliveryType;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话
     */
    private String receiverPhone;

    /**
     * 收货地址
     */
    private String deliveryAddress;

    /**
     * 预约配送时间
     */
    private LocalDateTime scheduledDeliveryTime;

    /**
     * 实际配送时间
     */
    private LocalDateTime actualDeliveryTime;

    /**
     * 配送状态（0-待配送，1-配送中，2-已签收，3-已拒收，4-已取消）
     */
    private Integer status;

    /**
     * 配送员
     */
    private String deliveryMan;

    /**
     * 配送费用
     */
    private BigDecimal deliveryFee;

    /**
     * 签收人
     */
    private String signer;

    /**
     * 签收时间
     */
    private LocalDateTime signTime;

    /**
     * 签收照片
     */
    private String signPhoto;

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
