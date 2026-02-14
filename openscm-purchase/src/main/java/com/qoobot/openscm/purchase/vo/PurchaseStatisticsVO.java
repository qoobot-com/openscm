package com.qoobot.openscm.purchase.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 采购统计VO
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Data
public class PurchaseStatisticsVO {

    /**
     * 统计周期
     */
    private String period;

    /**
     * 采购订单数量
     */
    private Integer orderCount;

    /**
     * 采购总金额
     */
    private BigDecimal totalAmount;

    /**
     * 已付款金额
     */
    private BigDecimal paidAmount;

    /**
     * 未付款金额
     */
    private BigDecimal unpaidAmount;

    /**
     * 供应商数量
     */
    private Integer supplierCount;

    /**
     * 平均订单金额
     */
    private BigDecimal avgOrderAmount;

    /**
     * 按物料分类统计
     */
    private Map<String, BigDecimal> materialCategoryStatistics;

    /**
     * 按供应商统计
     */
    private Map<String, BigDecimal> supplierStatistics;

    /**
     * 月度趋势数据
     */
    private Map<String, BigDecimal> monthlyTrend;
}
