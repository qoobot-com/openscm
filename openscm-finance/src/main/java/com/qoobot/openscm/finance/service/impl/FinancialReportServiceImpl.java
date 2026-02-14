package com.qoobot.openscm.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.finance.entity.FinancialReport;
import com.qoobot.openscm.finance.mapper.FinancialReportMapper;
import com.qoobot.openscm.finance.service.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 财务报表服务实现
 */
@Service
public class FinancialReportServiceImpl extends ServiceImpl<FinancialReportMapper, FinancialReport> implements FinancialReportService {

    @Autowired
    private FinancialReportMapper financialReportMapper;

    @Autowired
    private com.qoobot.openscm.finance.mapper.AccountsReceivableMapper accountsReceivableMapper;

    @Autowired
    private com.qoobot.openscm.finance.mapper.AccountsPayableMapper accountsPayableMapper;

    @Autowired
    private com.qoobot.openscm.finance.mapper.CostAccountingMapper costAccountingMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long generateReport(Integer reportType, String reportPeriod) {
        // 检查是否已存在该期间的报表
        FinancialReport existing = financialReportMapper.selectByPeriodAndType(reportPeriod, reportType);
        if (existing != null) {
            throw new RuntimeException("该期间报表已存在");
        }

        FinancialReport report = new FinancialReport();
        report.setReportNo(generateReportNo());
        report.setReportType(reportType);
        report.setReportPeriod(reportPeriod);
        report.setStatus(0); // 草稿
        report.setGenerateTime(java.time.LocalDateTime.now());
        report.setGeneratedBy("system");

        // 根据报表类型生成数据
        switch (reportType) {
            case 0: // 利润表
                generateProfitReport(report, reportPeriod);
                break;
            case 1: // 资产负债表
                generateBalanceSheet(report, reportPeriod);
                break;
            case 2: // 现金流量表
                generateCashFlowReport(report, reportPeriod);
                break;
        }

        financialReportMapper.insert(report);
        return report.getId();
    }

    @Override
    public IPage<FinancialReport> reportPage(Page<FinancialReport> page, Integer reportType,
                                              String reportPeriod, Integer status) {
        return financialReportMapper.selectReportPage(page, reportType, reportPeriod, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveReport(Long reportId) {
        FinancialReport report = financialReportMapper.selectById(reportId);
        if (report == null) {
            throw new RuntimeException("财务报表不存在");
        }
        if (report.getStatus() != 0) {
            throw new RuntimeException("报表状态不正确");
        }
        report.setStatus(1); // 已生成
        report.setApprovedBy("current_user");
        report.setApproveTime(java.time.LocalDateTime.now());
        financialReportMapper.updateById(report);
    }

    @Override
    public Map<String, Object> getFinancialOverview(String reportPeriod) {
        Map<String, Object> result = new HashMap<>();

        // 应收账款
        BigDecimal totalReceivable = accountsReceivableMapper.sumTotalAmount();
        result.put("totalReceivable", totalReceivable != null ? totalReceivable : BigDecimal.ZERO);

        // 应付账款
        BigDecimal totalPayable = accountsPayableMapper.sumTotalAmount();
        result.put("totalPayable", totalPayable != null ? totalPayable : BigDecimal.ZERO);

        // 总成本
        BigDecimal totalCost = costAccountingMapper.sumTotalCostByPeriod(reportPeriod);
        result.put("totalCost", totalCost != null ? totalCost : BigDecimal.ZERO);

        return result;
    }

    private void generateProfitReport(FinancialReport report, String reportPeriod) {
        report.setReportName("利润表");
        // 简化处理，实际应该从各业务模块汇总数据
        report.setIncome(new BigDecimal("1000000"));
        report.setCost(new BigDecimal("600000"));
        report.setExpense(new BigDecimal("100000"));
        report.setProfit(new BigDecimal("300000"));
    }

    private void generateBalanceSheet(FinancialReport report, String reportPeriod) {
        report.setReportName("资产负债表");
        // 简化处理
        report.setAssets(new BigDecimal("2000000"));
        report.setLiabilities(new BigDecimal("800000"));
        report.setEquity(new BigDecimal("1200000"));
    }

    private void generateCashFlowReport(FinancialReport report, String reportPeriod) {
        report.setReportName("现金流量表");
        // 简化处理
        report.setOperatingCashFlow(new BigDecimal("500000"));
        report.setInvestingCashFlow(new BigDecimal("-100000"));
        report.setFinancingCashFlow(new BigDecimal("-50000"));
    }

    private String generateReportNo() {
        return "RPT" + System.currentTimeMillis();
    }
}
