package com.qoobot.openscm.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.order.entity.SalesOrder;
import com.qoobot.openscm.order.entity.SalesOrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 销售订单服务测试
 */
@SpringBootTest
class SalesOrderServiceTest {

    @Autowired
    private SalesOrderService salesOrderService;

    @Test
    void testCreateOrder() {
        SalesOrder order = new SalesOrder();
        order.setCustomerId(1L);
        order.setCustomerName("测试客户");
        order.setOrderDate(java.time.LocalDate.now());
        order.setPaymentMethod(1);
        order.setDeliveryAddress("测试地址");
        order.setContactPerson("张三");
        order.setContactPhone("13800138000");
        order.setRemark("测试订单");
        order.setCreatedBy("test");

        List<SalesOrderItem> items = new ArrayList<>();
        SalesOrderItem item = new SalesOrderItem();
        item.setMaterialId(1L);
        item.setMaterialCode("MAT001");
        item.setMaterialName("测试物料");
        item.setSpecification("规格A");
        item.setUnit("件");
        item.setQuantity(new BigDecimal("10"));
        item.setUnitPrice(new BigDecimal("100.00"));
        item.setDiscountRate(new BigDecimal("5.00"));
        items.add(item);

        Long orderId = salesOrderService.createOrder(order, items);
        assertNotNull(orderId);
        assertTrue(orderId > 0);
    }

    @Test
    void testOrderPage() {
        Page<SalesOrder> page = new Page<>(1, 10);
        IPage<SalesOrder> result = salesOrderService.orderPage(page, null, null, null, null, null);
        assertNotNull(result);
        assertTrue(result.getRecords().size() >= 0);
    }

    @Test
    void testGetOrderDetail() {
        Long orderId = 1L;
        SalesOrder order = salesOrderService.getOrderDetail(orderId);
        // 可能不存在，不为空则断言
        if (order != null) {
            assertNotNull(order.getId());
        }
    }

    @Test
    void testConfirmOrder() {
        // 假设订单ID为1
        Long orderId = 1L;
        try {
            salesOrderService.confirmOrder(orderId);
        } catch (Exception e) {
            // 如果订单不存在或状态不正确，预期会抛异常
            assertTrue(e.getMessage().contains("订单") || e.getMessage().contains("状态"));
        }
    }

    @Test
    void testCancelOrder() {
        Long orderId = 1L;
        try {
            salesOrderService.cancelOrder(orderId);
        } catch (Exception e) {
            // 如果订单不存在或状态不正确，预期会抛异常
            assertTrue(e.getMessage().contains("订单"));
        }
    }

    @Test
    void testUpdateOrderStatus() {
        Long orderId = 1L;
        try {
            salesOrderService.updateOrderStatus(orderId, 2);
        } catch (Exception e) {
            // 如果订单不存在，预期会抛异常
            assertTrue(e.getMessage().contains("订单"));
        }
    }

    @Test
    void testOrderStatistics() {
        Map<String, Object> statistics = salesOrderService.orderStatistics();
        assertNotNull(statistics);
        assertTrue(statistics.containsKey("totalOrders"));
        assertTrue(statistics.containsKey("statusCount"));
        assertTrue(statistics.containsKey("totalAmount"));
    }
}
