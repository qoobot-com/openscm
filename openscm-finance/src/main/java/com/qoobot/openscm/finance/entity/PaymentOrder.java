package com.qoobot.openscm.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收付款单实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("payment_order")
public class PaymentOrder {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 收付款单号
     */
    private String paymentNo;

    /**
     * 单据类型（0-收款单，1-付款单）
     */
    private Integer orderType;

    /**
     * 关联应收应付ID
     */
    private Long accountId;

    /**
     * 关联订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 客户/供应商ID
     */
    private Long partnerId;

    /**
     * 客户/供应商名称
     */
    private String partnerName;

    /**
     * 收付款金额
     */
    private BigDecimal amount;

    /**
     * 收付款方式（0-现金，1-银行转账，2-支票，3-承兑汇票）
     */
    private Integer paymentMethod;

    /**
     * 收付款日期
     */
    private LocalDateTime paymentDate;

    /**
     * 收付款账户
     */
    private String account;

    /**
     * 凭证号
     */
    private String voucherNo;

    /**
     * 收付款状态（0-待审核，1-已审核，2-已拒绝）
     */
    private Integer status;

    /**
     * 审核人
     */
    private String approvedBy;

    /**
     * 审核时间
     */
    private LocalDateTime approveTime;

    /**
     * 审核意见
     */
    private String approveRemark;

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
