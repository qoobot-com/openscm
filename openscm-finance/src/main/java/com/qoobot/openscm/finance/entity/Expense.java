package com.qoobot.openscm.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 费用报销实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("expense")
public class Expense {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 报销单号
     */
    private String expenseNo;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 所属部门
     */
    private String department;

    /**
     * 费用类型（0-差旅费，1-招待费，2-办公费，3-交通费，4-其他）
     */
    private Integer expenseType;

    /**
     * 费用金额
     */
    private BigDecimal amount;

    /**
     * 费用日期
     */
    private LocalDate expenseDate;

    /**
     * 费用说明
     */
    private String expenseDesc;

    /**
     * 报销状态（0-待审核，1-审核中，2-已审核，3-已拒绝，4-已支付）
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
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 附件
     */
    private String attachment;

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
