package com.qoobot.openscm.purchase.service;

import com.qoobot.openscm.purchase.dto.PurchaseOrderDTO;
import com.qoobot.openscm.purchase.dto.PurchaseOrderItemDTO;
import com.qoobot.openscm.purchase.entity.PurchaseOrder;
import com.qoobot.openscm.purchase.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 采购订单服务测试
 */
@SpringBootTest
@Transactional
public class PurchaseOrderServiceTest {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
     * 测试创建采购订单
     */
    @Test
    public void testCreatePurchaseOrder() {
        // 准备测试数据
        PurchaseOrderDTO orderDTO = new PurchaseOrderDTO();
        orderDTO.setOrderCode("PO20260215001");
        orderDTO.setSupplierId(1L);
        orderDTO.setOrderDate(LocalDate.now());
        orderDTO.setExpectedDeliveryDate(LocalDate.now().plusDays(7));
        orderDTO.setOrderAmount(new BigDecimal("100000.00"));
        orderDTO.setPaymentTerm("30天");
        orderDTO.setDeliveryAddress("北京市朝阳区");
        orderDTO.setRemark("单元测试数据");

        List<PurchaseOrderItemDTO> items = new ArrayList<>();
        PurchaseOrderItemDTO itemDTO = new PurchaseOrderItemDTO();
        itemDTO.setMaterialCode("M001");
        itemDTO.setMaterialName("钢材");
        itemDTO.setSpecification("规格A");
        itemDTO.setUnit("吨");
        itemDTO.setQuantity(new BigDecimal("10.00"));
        itemDTO.setUnitPrice(new BigDecimal("10000.00"));
        itemDTO.setAmount(new BigDecimal("100000.00"));
        items.add(itemDTO);
        orderDTO.setItems(items);

        // 执行创建
        Long orderId = purchaseOrderService.createPurchaseOrder(orderDTO);

        // 验证结果
        assertNotNull(orderId);
        
        PurchaseOrder createdOrder = purchaseOrderService.getPurchaseOrderById(orderId);
        assertNotNull(createdOrder);
        assertEquals("PO20260215001", createdOrder.getOrderCode());
        assertEquals(OrderStatus.PENDING, createdOrder.getStatus());
        assertEquals(new BigDecimal("100000.00"), createdOrder.getOrderAmount());
    }

    /**
     * 测试分页查询采购订单
     */
    @Test
    public void testGetPurchaseOrdersByPage() {
        // 执行查询
        Page<PurchaseOrder> page = purchaseOrderService.getPurchaseOrdersByPage(1, 10, null, null, null, null);

        // 验证结果
        assertNotNull(page);
        assertTrue(page.getContent().size() >= 0);
    }

    /**
     * 测试确认订单
     */
    @Test
    public void testConfirmOrder() {
        // 创建订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        Long orderId = purchaseOrderService.createPurchaseOrder(orderDTO);

        // 确认订单
        purchaseOrderService.confirmOrder(orderId);

        // 验证状态
        PurchaseOrder order = purchaseOrderService.getPurchaseOrderById(orderId);
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
        assertNotNull(order.getConfirmedAt());
    }

    /**
     * 测试取消订单
     */
    @Test
    public void testCancelOrder() {
        // 创建订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        Long orderId = purchaseOrderService.createPurchaseOrder(orderDTO);

        // 取消订单
        purchaseOrderService.cancelOrder(orderId, "不需要了");

        // 验证状态
        PurchaseOrder order = purchaseOrderService.getPurchaseOrderById(orderId);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertEquals("不需要了", order.getCancelReason());
        assertNotNull(order.getCancelledAt());
    }

    /**
     * 测试发货
     */
    @Test
    public void testShipOrder() {
        // 创建并确认订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        Long orderId = purchaseOrderService.createPurchaseOrder(orderDTO);
        purchaseOrderService.confirmOrder(orderId);

        // 发货
        purchaseOrderService.shipOrder(orderId, "SF123456789", "2026-02-20");

        // 验证状态
        PurchaseOrder order = purchaseOrderService.getPurchaseOrderById(orderId);
        assertEquals(OrderStatus.SHIPPED, order.getStatus());
        assertEquals("SF123456789", order.getTrackingNumber());
        assertNotNull(order.getShippedAt());
    }

    /**
     * 测试收货
     */
    @Test
    public void testReceiveOrder() {
        // 创建、确认并发货订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        Long orderId = purchaseOrderService.createPurchaseOrder(orderDTO);
        purchaseOrderService.confirmOrder(orderId);
        purchaseOrderService.shipOrder(orderId, "SF123456789", "2026-02-20");

        // 收货
        purchaseOrderService.receiveOrder(orderId, new BigDecimal("10.00"), "包装完好");

        // 验证状态
        PurchaseOrder order = purchaseOrderService.getPurchaseOrderById(orderId);
        assertEquals(OrderStatus.RECEIVED, order.getStatus());
        assertEquals(new BigDecimal("10.00"), order.getReceivedQuantity());
        assertNotNull(order.getReceivedAt());
    }

    /**
     * 测试完成订单
     */
    @Test
    public void testCompleteOrder() {
        // 创建并完成整个流程
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        Long orderId = purchaseOrderService.createPurchaseOrder(orderDTO);
        purchaseOrderService.confirmOrder(orderId);
        purchaseOrderService.shipOrder(orderId, "SF123456789", "2026-02-20");
        purchaseOrderService.receiveOrder(orderId, new BigDecimal("10.00"), "包装完好");

        // 完成订单
        purchaseOrderService.completeOrder(orderId);

        // 验证状态
        PurchaseOrder order = purchaseOrderService.getPurchaseOrderById(orderId);
        assertEquals(OrderStatus.COMPLETED, order.getStatus());
        assertNotNull(order.getCompletedAt());
    }

    /**
     * 测试删除订单
     */
    @Test
    public void testDeletePurchaseOrder() {
        // 创建订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        Long orderId = purchaseOrderService.createPurchaseOrder(orderDTO);

        // 删除订单
        purchaseOrderService.deletePurchaseOrder(orderId);

        // 验证删除结果
        assertThrows(RuntimeException.class, () -> {
            purchaseOrderService.getPurchaseOrderById(orderId);
        });
    }

    /**
     * 测试更新已收货数量
     */
    @Test
    public void testUpdateReceivedQuantity() {
        // 创建并收货订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        Long orderId = purchaseOrderService.createPurchaseOrder(orderDTO);
        purchaseOrderService.confirmOrder(orderId);
        purchaseOrderService.receiveOrder(orderId, new BigDecimal("5.00"), "部分收货");

        // 更新收货数量
        purchaseOrderService.updateReceivedQuantity(orderId, new BigDecimal("8.00"));

        // 验证结果
        PurchaseOrder order = purchaseOrderService.getPurchaseOrderById(orderId);
        assertEquals(new BigDecimal("8.00"), order.getReceivedQuantity());
    }

    /**
     * 创建测试用的采购订单DTO
     */
    private PurchaseOrderDTO createTestOrderDTO() {
        PurchaseOrderDTO orderDTO = new PurchaseOrderDTO();
        orderDTO.setOrderCode("PO20260215TEST");
        orderDTO.setSupplierId(1L);
        orderDTO.setOrderDate(LocalDate.now());
        orderDTO.setExpectedDeliveryDate(LocalDate.now().plusDays(7));
        orderDTO.setOrderAmount(new BigDecimal("50000.00"));
        orderDTO.setPaymentTerm("30天");
        orderDTO.setDeliveryAddress("测试地址");
        orderDTO.setRemark("测试数据");

        List<PurchaseOrderItemDTO> items = new ArrayList<>();
        PurchaseOrderItemDTO itemDTO = new PurchaseOrderItemDTO();
        itemDTO.setMaterialCode("M001");
        itemDTO.setMaterialName("测试物料");
        itemDTO.setSpecification("测试规格");
        itemDTO.setUnit("件");
        itemDTO.setQuantity(new BigDecimal("5.00"));
        itemDTO.setUnitPrice(new BigDecimal("10000.00"));
        itemDTO.setAmount(new BigDecimal("50000.00"));
        items.add(itemDTO);
        orderDTO.setItems(items);

        return orderDTO;
    }
}
