package com.qoobot.openscm.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.finance.entity.AccountsReceivable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 应收账款Mapper接口
 */
@Mapper
public interface AccountsReceivableMapper extends BaseMapper<AccountsReceivable> {

    /**
     * 分页查询应收账款
     */
    IPage<AccountsReceivable> selectReceivablePage(Page<AccountsReceivable> page,
                                                    @Param("receivableNo") String receivableNo,
                                                    @Param("customerId") Long customerId,
                                                    @Param("settlementStatus") Integer settlementStatus,
                                                    @Param("startDate") String startDate,
                                                    @Param("endDate") String endDate);

    /**
     * 根据订单ID查询应收账款
     */
    AccountsReceivable selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 统计应收总额
     */
    BigDecimal sumTotalAmount();

    /**
     * 统计已收总额
     */
    BigDecimal sumReceivedAmount();

    /**
     * 统计未收总额
     */
    BigDecimal sumUnpaidAmount();

    /**
     * 客户应收汇总
     */
    List<Map<String, Object>> summaryByCustomer();
}
