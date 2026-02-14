package com.qoobot.openscm.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.common.core.domain.Result;
import com.qoobot.openscm.order.entity.SalesOrder;
import com.qoobot.openscm.order.entity.SalesOrderItem;
import com.qoobot.openscm.order.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 销售订单控制器
 */
@RestController
@RequestMapping("/order/sales")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    /**
     * 创建销售订单
     */
    @PostMapping("/create")
    public Result<Long> createOrder(@RequestBody Map<String, Object> params) {
        SalesOrder order = new SalesOrder();
        // 简化处理，实际应该从参数中解析
        order.setCustomerId(Long.valueOf(params.get("customerId").toString()));
        order.setCustomerName(params.get("customerName").toString());
        order.setOrderDate(java.time.LocalDate.now());
        order.setPaymentMethod(Integer.valueOf(params.get("paymentMethod").toString()));
        order.setDeliveryAddress(params.get("deliveryAddress").toString());
        order.setContactPerson(params.get("contactPerson").toString());
        order.setContactPhone(params.get("contactPhone").toString());
        order.setRemark((String) params.get("remark"));
        order.setCreatedBy(params.get("createdBy").toString());
        
        List<SalesOrderItem> items = (List<SalesOrderItem>) params.get("items");
        Long orderId = salesOrderService.createOrder(order, items);
        return Result.success(orderId);
    }

    /**
     * 分页查询销售订单
     */
    @GetMapping("/page")
    public Result<IPage<SalesOrder>> orderPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        Page<SalesOrder> page = new Page<>(current, size);
        IPage<SalesOrder> result = salesOrderService.orderPage(page, orderNo, customerId, status, startDate, endDate);
        return Result.success(result);
    }

    /**
     * 查询订单详情
     */
    @GetMapping("/detail/{orderId}")
    public Result<SalesOrder> getOrderDetail(@PathVariable Long orderId) {
        SalesOrder order = salesOrderService.getOrderDetail(orderId);
        return Result.success(order);
    }

    /**
     * 确认订单
     */
    @PostMapping("/confirm/{orderId}")
    public Result<Void> confirmOrder(@PathVariable Long orderId) {
        salesOrderService.confirmOrder(orderId);
        return Result.success();
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel/{orderId}")
    public Result<Void> cancelOrder(@PathVariable Long orderId) {
        salesOrderService.cancelOrder(orderId);
        return Result.success();
    }

    /**
     * 更新订单状态
     */
    @PostMapping("/status/{orderId}")
    public Result<Void> updateOrderStatus(@PathVariable Long orderId, @RequestParam Integer status) {
        salesOrderService.updateOrderStatus(orderId, status);
        return Result.success();
    }

    /**
     * 订单统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> orderStatistics() {
        Map<String, Object> statistics = salesOrderService.orderStatistics();
        return Result.success(statistics);
    }
}
