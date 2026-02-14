package com.qoobot.openscm.purchase.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 采购订单VO
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
public class PurchaseOrderVO {

    /**
     * 订单ID
     */
    private Long id;

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
     * 订单状态
     */
    private Integer status;

    /**
     * 订单状态名称
     */
    private String statusName;

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
     * 付款方式
     */
    private Integer paymentMethod;

    /**
     * 付款方式名称
     */
    private String paymentMethodName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 订单明细列表
     */
    private List<PurchaseOrderItemVO> items;
}
