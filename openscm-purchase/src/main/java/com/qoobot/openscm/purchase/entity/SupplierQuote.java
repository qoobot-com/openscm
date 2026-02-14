package com.qoobot.openscm.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 供应商报价实体类
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("supplier_quote")
public class SupplierQuote extends BaseEntity {

    /**
     * 报价单编号
     */
    private String quoteNo;

    /**
     * 询价单ID
     */
    private Long inquiryId;

    /**
     * 询价单编号
     */
    private String inquiryNo;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 报价总金额
     */
    private BigDecimal totalAmount;

    /**
     * 报价日期
     */
    private LocalDate quoteDate;

    /**
     * 报价有效期至
     */
    private LocalDate validUntil;

    /**
     * 交货周期(天)
     */
    private Integer deliveryDays;

    /**
     * 付款方式: 1-货到付款 2-预付款 3-月结 4-分期付款
     */
    private Integer paymentMethod;

    /**
     * 是否被采纳: 0-未采纳 1-已采纳
     */
    private Integer isAdopted;

    /**
     * 报价状态: 0-待审核 1-已审核 2-已拒绝
     */
    private Integer status;

    /**
     * 审核人ID
     */
    private Long approverId;

    /**
     * 审核人姓名
     */
    private String approverName;

    /**
     * 审核时间
     */
    private LocalDate approvalDate;

    /**
     * 审核意见
     */
    private String approvalRemark;

    /**
     * 备注
     */
    private String remark;
}
