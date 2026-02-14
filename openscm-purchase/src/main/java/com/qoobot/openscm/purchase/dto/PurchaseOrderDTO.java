package com.qoobot.openscm.purchase.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 采购订单DTO
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
public class PurchaseOrderDTO {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 采购计划ID
     */
    private Long planId;

    /**
     * 供应商ID
     */
    @NotNull(message = "供应商不能为空")
    private Long supplierId;

    /**
     * 计划交货日期
     */
    @NotNull(message = "计划交货日期不能为空")
    private LocalDate expectedDeliveryDate;

    /**
     * 收货地址
     */
    @NotBlank(message = "收货地址不能为空")
    private String deliveryAddress;

    /**
     * 联系人
     */
    @NotBlank(message = "联系人不能为空")
    private String contactPerson;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String contactPhone;

    /**
     * 付款方式
     */
    @NotNull(message = "付款方式不能为空")
    private Integer paymentMethod;

    /**
     * 订单明细列表
     */
    @NotNull(message = "订单明细不能为空")
    private List<PurchaseOrderItemDTO> items;

    /**
     * 备注
     */
    private String remark;
}
