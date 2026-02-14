package com.qoobot.openscm.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 应收账款实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("accounts_receivable")
public class AccountsReceivable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 应收单号
     */
    private String receivableNo;

    /**
     * 关联订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 应收金额
     */
    private BigDecimal amount;

    /**
     * 已收金额
     */
    private BigDecimal receivedAmount;

    /**
     * 未收金额
     */
    private BigDecimal unpaidAmount;

    /**
     * 应收日期
     */
    private LocalDate receivableDate;

    /**
     * 到期日期
     */
    private LocalDate dueDate;

    /**
     * 结算状态（0-未结算，1-部分结算，2-已结算）
     */
    private Integer settlementStatus;

    /**
     * 业务类型（0-销售订单，1-退货退款）
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
