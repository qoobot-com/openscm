package com.qoobot.openscm.purchase.service;

import com.qoobot.openscm.purchase.vo.PurchaseStatisticsVO;

import java.time.LocalDate;
import java.util.Map;

/**
 * 采购统计服务接口
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
public interface PurchaseStatisticsService {

    /**
     * 获取采购统计信息
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 统计信息
     */
    PurchaseStatisticsVO getStatistics(LocalDate startDate, LocalDate endDate);

    /**
     * 按月统计采购数据
     *
     * @param year 年份
     * @return 月度统计数据
     */
    Map<String, Object> getMonthlyStatistics(Integer year);

    /**
     * 按供应商统计采购数据
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 供应商统计数据
     */
    Map<String, Object> getSupplierStatistics(LocalDate startDate, LocalDate endDate);

    /**
     * 按物料统计采购数据
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 物料统计数据
     */
    Map<String, Object> getMaterialStatistics(LocalDate startDate, LocalDate endDate);

    /**
     * 获取采购成本分析
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 成本分析数据
     */
    Map<String, Object> getCostAnalysis(LocalDate startDate, LocalDate endDate);
}
