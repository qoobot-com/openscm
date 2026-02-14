package com.qoobot.openscm.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.finance.entity.CostAccounting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 成本核算Mapper接口
 */
@Mapper
public interface CostAccountingMapper extends BaseMapper<CostAccounting> {

    /**
     * 分页查询成本核算
     */
    IPage<CostAccounting> selectAccountingPage(Page<CostAccounting> page,
                                               @Param("accountingNo") String accountingNo,
                                               @Param("costType") Integer costType,
                                               @Param("accountingPeriod") String accountingPeriod,
                                               @Param("startDate") String startDate,
                                               @Param("endDate") String endDate);

    /**
     * 根据核算单号查询
     */
    CostAccounting selectByAccountingNo(@Param("accountingNo") String accountingNo);

    /**
     * 按成本类型汇总
     */
    List<Map<String, Object>> summaryByCostType(@Param("accountingPeriod") String accountingPeriod);

    /**
     * 统核算期间总成本
     */
    BigDecimal sumTotalCostByPeriod(@Param("accountingPeriod") String accountingPeriod);
}
