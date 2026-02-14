package com.qoobot.openscm.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 采购订单实体类
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("purchase_order")
public class PurchaseOrder extends BaseEntity {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 采购计划ID
     */
    private Long planId;

    /**
     * 采购计划编号
     */
    private String planNo;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 已付款金额
     */
    private BigDecimal paidAmount;

    /**
     * 订单状态: 0-待确认 1-已确认 2-已发货 3-已收货 4-已完成 5-已取消
     */
    private Integer status;

    /**
     * 下单人ID
     */
    private Long purchaserId;

    /**
     * 下单人姓名
     */
    private String purchaserName;

    /**
     * 计划交货日期
     */
    private LocalDate expectedDeliveryDate;

    /**
     * 实际交货日期
     */
    private LocalDate actualDeliveryDate;

    /**
     * 收货地址
     */
    private String deliveryAddress;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 付款方式: 1-货到付款 2-预付款 3-月结 4-分期付款
     */
    private Integer paymentMethod;

    /**
     * 备注
     */
    private String remark;
}
