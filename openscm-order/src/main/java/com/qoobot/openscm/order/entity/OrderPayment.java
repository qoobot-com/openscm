package com.qoobot.openscm.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单支付记录实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_payment")
public class OrderPayment {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 支付编号
     */
    private String paymentNo;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 支付方式（0-货到付款，1-银行转账，2-在线支付，3-月结）
     */
    private Integer paymentMethod;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 支付状态（0-待支付，1-已支付，2-支付失败）
     */
    private Integer status;

    /**
     * 支付凭证
     */
    private String voucher;

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
}
