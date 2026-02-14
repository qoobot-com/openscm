package com.qoobot.openscm.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.finance.entity.AccountsPayable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 应付账款Mapper接口
 */
@Mapper
public interface AccountsPayableMapper extends BaseMapper<AccountsPayable> {

    /**
     * 分页查询应付账款
     */
    IPage<AccountsPayable> selectPayablePage(Page<AccountsPayable> page,
                                              @Param("payableNo") String payableNo,
                                              @Param("supplierId") Long supplierId,
                                              @Param("settlementStatus") Integer settlementStatus,
                                              @Param("startDate") String startDate,
                                              @Param("endDate") String endDate);

    /**
     * 根据采购订单ID查询应付账款
     */
    AccountsPayable selectByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);

    /**
     * 统计应付总额
     */
    BigDecimal sumTotalAmount();

    /**
     * 统计已付总额
     */
    BigDecimal sumPaidAmount();

    /**
     * 统计未付总额
     */
    BigDecimal sumUnpaidAmount();

    /**
     * 供应商应付汇总
     */
    List<Map<String, Object>> summaryBySupplier();
}
