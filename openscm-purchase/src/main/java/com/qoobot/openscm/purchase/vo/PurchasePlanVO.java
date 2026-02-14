package com.qoobot.openscm.purchase.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 采购计划VO
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
public class PurchasePlanVO {

    /**
     * 计划ID
     */
    private Long id;

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
     * 计划状态
     */
    private Integer status;

    /**
     * 计划状态名称
     */
    private String statusName;

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

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 计划明细列表
     */
    private List<PurchasePlanItemVO> items;
}
