package com.qoobot.openscm.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 采购计划实体类
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("purchase_plan")
public class PurchasePlan extends BaseEntity {

    /**
     * 计划编号
     */
    private String planNo;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 计划年度
     */
    private Integer planYear;

    /**
     * 计划月份
     */
    private Integer planMonth;

    /**
     * 计划开始日期
     */
    private LocalDate startDate;

    /**
     * 计划结束日期
     */
    private LocalDate endDate;

    /**
     * 计划总金额
     */
    private BigDecimal totalAmount;

    /**
     * 实际执行金额
     */
    private BigDecimal actualAmount;

    /**
     * 计划状态: 0-草稿 1-待审批 2-已审批 3-执行中 4-已完成 5-已驳回
     */
    private Integer status;

    /**
     * 申请人ID
     */
    private Long applicantId;

    /**
     * 申请人姓名
     */
    private String applicantName;

    /**
     * 审批人ID
     */
    private Long approverId;

    /**
     * 审批人姓名
     */
    private String approverName;

    /**
     * 审批时间
     */
    private LocalDate approvalDate;

    /**
     * 审批意见
     */
    private String approvalRemark;

    /**
     * 备注
     */
    private String remark;
}
