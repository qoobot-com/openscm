package com.qoobot.openscm.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.finance.entity.FinancialReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 财务报表Mapper接口
 */
@Mapper
public interface FinancialReportMapper extends BaseMapper<FinancialReport> {

    /**
     * 分页查询财务报表
     */
    IPage<FinancialReport> selectReportPage(Page<FinancialReport> page,
                                            @Param("reportType") Integer reportType,
                                            @Param("reportPeriod") String reportPeriod,
                                            @Param("status") Integer status);

    /**
     * 根据报表期间和类型查询
     */
    FinancialReport selectByPeriodAndType(@Param("reportPeriod") String reportPeriod,
                                          @Param("reportType") Integer reportType);

    /**
     * 查询最新报表
     */
    List<FinancialReport> selectLatestReports(@Param("reportType") Integer reportType);
}
