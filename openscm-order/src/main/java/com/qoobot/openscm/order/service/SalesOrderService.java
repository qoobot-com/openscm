package com.qoobot.openscm.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.order.entity.SalesOrder;
import com.qoobot.openscm.order.entity.SalesOrderItem;

import java.util.List;

/**
 * 销售订单服务接口
 */
public interface SalesOrderService extends IService<SalesOrder> {

    /**
     * 创建销售订单
     */
    Long createOrder(SalesOrder order, List<SalesOrderItem> items);

    /**
     * 分页查询销售订单
     */
    IPage<SalesOrder> orderPage(Page<SalesOrder> page, String orderNo, Long customerId, 
                                 Integer status, String startDate, String endDate);

    /**
     * 查询订单详情（包含明细）
     */
    SalesOrder getOrderDetail(Long orderId);

    /**
     * 确认订单
     */
    void confirmOrder(Long orderId);

    /**
     * 取消订单
     */
    void cancelOrder(Long orderId);

    /**
     * 更新订单状态
     */
    void updateOrderStatus(Long orderId, Integer status);

    /**
     * 订单统计
     */
    java.util.Map<String, Object> orderStatistics();
}
