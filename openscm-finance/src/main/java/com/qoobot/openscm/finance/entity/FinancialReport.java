package com.qoobot.openscm.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 财务报表实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("financial_report")
public class FinancialReport {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 报表编号
     */
    private String reportNo;

    /**
     * 报表类型（0-利润表，1-资产负债表，2-现金流量表）
     */
    private Integer reportType;

    /**
     * 报表期间
     */
    private String reportPeriod;

    /**
     * 报表名称
     */
    private String reportName;

    /**
     * 收入
     */
    private BigDecimal income;

    /**
     * 成本
     */
    private BigDecimal cost;

    /**
     * 费用
     */
    private BigDecimal expense;

    /**
     * 利润
     */
    private BigDecimal profit;

    /**
     * 资产
     */
    private BigDecimal assets;

    /**
     * 负债
     */
    private BigDecimal liabilities;

    /**
     * 所有者权益
     */
    private BigDecimal equity;

    /**
     * 经营活动现金流
     */
    private BigDecimal operatingCashFlow;

    /**
     * 投资活动现金流
     */
    private BigDecimal investingCashFlow;

    /**
     * 筹资活动现金流
     */
    private BigDecimal financingCashFlow;

    /**
     * 报表状态（0-草稿，1-已生成，2-已审核）
     */
    private Integer status;

    /**
     * 生成人
     */
    private String generatedBy;

    /**
     * 生成时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime generateTime;

    /**
     * 审核人
     */
    private String approvedBy;

    /**
     * 审核时间
     */
    private LocalDateTime approveTime;

    /**
     * 备注
     */
    private String remark;
}
