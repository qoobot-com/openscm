package com.qoobot.openscm.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.finance.entity.FinancialReport;

import java.util.Map;

/**
 * 财务报表服务接口
 */
public interface FinancialReportService extends IService<FinancialReport> {

    /**
     * 生成财务报表
     */
    Long generateReport(Integer reportType, String reportPeriod);

    /**
     * 分页查询财务报表
     */
    IPage<FinancialReport> reportPage(Page<FinancialReport> page, Integer reportType,
                                       String reportPeriod, Integer status);

    /**
     * 审核财务报表
     */
    void approveReport(Long reportId);

    /**
     * 获取财务概况
     */
    Map<String, Object> getFinancialOverview(String reportPeriod);
}
