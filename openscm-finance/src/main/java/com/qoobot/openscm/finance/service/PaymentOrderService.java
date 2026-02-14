package com.qoobot.openscm.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.finance.entity.PaymentOrder;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 收付款单服务接口
 */
public interface PaymentOrderService extends IService<PaymentOrder> {

    /**
     * 创建收付款单
     */
    Long createPayment(PaymentOrder payment);

    /**
     * 分页查询收付款单
     */
    IPage<PaymentOrder> paymentPage(Page<PaymentOrder> page, String paymentNo, Integer orderType,
                                     Integer status, String startDate, String endDate);

    /**
     * 审核收付款单
     */
    void approvePayment(Long paymentId, Boolean approve, String approveRemark);

    /**
     * 收付款统计
     */
    Map<String, Object> statistics();
}
