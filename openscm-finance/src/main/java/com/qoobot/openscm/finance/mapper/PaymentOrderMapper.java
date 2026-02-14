package com.qoobot.openscm.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.finance.entity.PaymentOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 收付款单Mapper接口
 */
@Mapper
public interface PaymentOrderMapper extends BaseMapper<PaymentOrder> {

    /**
     * 分页查询收付款单
     */
    IPage<PaymentOrder> selectPaymentPage(Page<PaymentOrder> page,
                                          @Param("paymentNo") String paymentNo,
                                          @Param("orderType") Integer orderType,
                                          @Param("status") Integer status,
                                          @Param("startDate") String startDate,
                                          @Param("endDate") String endDate);

    /**
     * 根据收付款单号查询
     */
    PaymentOrder selectByPaymentNo(@Param("paymentNo") String paymentNo);

    /**
     * 根据关联应收应付ID查询
     */
    List<PaymentOrder> selectByAccountId(@Param("accountId") Long accountId);

    /**
     * 统计收款总额
     */
    BigDecimal sumReceiptAmount();

    /**
     * 统计付款总额
     */
    BigDecimal sumPaymentAmount();
}
