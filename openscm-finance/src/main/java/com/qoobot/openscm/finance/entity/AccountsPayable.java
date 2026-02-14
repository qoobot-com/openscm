package com.qoobot.openscm.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 应付账款实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("accounts_payable")
public class AccountsPayable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 应付单号
     */
    private String payableNo;

    /**
     * 关联采购订单ID
     */
    private Long purchaseOrderId;

    /**
     * 采购订单编号
     */
    private String purchaseOrderNo;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 应付金额
     */
    private BigDecimal amount;

    /**
     * 已付金额
     */
    private BigDecimal paidAmount;

    /**
     * 未付金额
     */
    private BigDecimal unpaidAmount;

    /**
     * 应付日期
     */
    private LocalDate payableDate;

    /**
     * 到期日期
     */
    private LocalDate dueDate;

    /**
     * 结算状态（0-未结算，1-部分结算，2-已结算）
     */
    private Integer settlementStatus;

    /**
     * 业务类型（0-采购订单，1-退货退款）
     */
    private Integer businessType;

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
