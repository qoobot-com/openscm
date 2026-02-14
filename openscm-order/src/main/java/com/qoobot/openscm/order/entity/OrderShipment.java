package com.qoobot.openscm.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 订单发货记录实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_shipment")
public class OrderShipment {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 发货单号
     */
    private String shipmentNo;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    /**
     * 运单号
     */
    private String trackingNo;

    /**
     * 发货时间
     */
    private LocalDateTime shipmentTime;

    /**
     * 预计送达时间
     */
    private LocalDateTime estimatedDeliveryTime;

    /**
     * 实际送达时间
     */
    private LocalDateTime actualDeliveryTime;

    /**
     * 收货地址
     */
    private String deliveryAddress;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 收货电话
     */
    private String receiverPhone;

    /**
     * 发货状态（0-待发货，1-已发货，2-已签收，3-已拒收）
     */
    private Integer status;

    /**
     * 发货人
     */
    private String shippedBy;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
