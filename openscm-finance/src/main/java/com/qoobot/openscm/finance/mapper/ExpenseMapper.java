package com.qoobot.openscm.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.finance.entity.Expense;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 费用报销Mapper接口
 */
@Mapper
public interface ExpenseMapper extends BaseMapper<Expense> {

    /**
     * 分页查询费用报销
     */
    IPage<Expense> selectExpensePage(Page<Expense> page,
                                      @Param("expenseNo") String expenseNo,
                                      @Param("applicant") String applicant,
                                      @Param("expenseType") Integer expenseType,
                                      @Param("status") Integer status,
                                      @Param("startDate") String startDate,
                                      @Param("endDate") String endDate);

    /**
     * 根据报销单号查询
     */
    Expense selectByExpenseNo(@Param("expenseNo") String expenseNo);

    /**
     * 按申请人汇总
     */
    List<Map<String, Object>> summaryByApplicant(@Param("startDate") String startDate,
                                                 @Param("endDate") String endDate);

    /**
     * 按费用类型汇总
     */
    List<Map<String, Object>> summaryByExpenseType(@Param("startDate") String startDate,
                                                   @Param("endDate") String endDate);

    /**
     * 统计报销总额
     */
    BigDecimal sumTotalAmount(@Param("startDate") String startDate,
                              @Param("endDate") String endDate);
}
