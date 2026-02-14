package com.qoobot.openscm.purchase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qoobot.openscm.purchase.dto.PurchaseOrderDTO;
import com.qoobot.openscm.purchase.dto.PurchaseOrderItemDTO;
import com.qoobot.openscm.common.util.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 采购订单控制器测试
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PurchaseOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 测试创建采购订单
     */
    @Test
    public void testCreatePurchaseOrder() throws Exception {
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        String json = objectMapper.writeValueAsString(orderDTO);

        MvcResult result = mockMvc.perform(post("/api/purchase/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Result resultObj = objectMapper.readValue(response, Result.class);
        assertNotNull(resultObj.getData());
    }

    /**
     * 测试分页查询采购订单
     */
    @Test
    public void testGetPurchaseOrdersByPage() throws Exception {
        mockMvc.perform(get("/api/purchase/order/page")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试根据ID查询采购订单
     */
    @Test
    public void testGetPurchaseOrderById() throws Exception {
        // 先创建一个采购订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        String json = objectMapper.writeValueAsString(orderDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long orderId = ((Number) createResultObj.getData()).longValue();

        // 查询采购订单
        mockMvc.perform(get("/api/purchase/order/" + orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(orderId));
    }

    /**
     * 测试更新采购订单
     */
    @Test
    public void testUpdatePurchaseOrder() throws Exception {
        // 先创建一个采购订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        String createJson = objectMapper.writeValueAsString(orderDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long orderId = ((Number) createResultObj.getData()).longValue();

        // 更新采购订单
        PurchaseOrderDTO updateDTO = new PurchaseOrderDTO();
        updateDTO.setRemark("更新备注");
        String updateJson = objectMapper.writeValueAsString(updateDTO);

        mockMvc.perform(put("/api/purchase/order/" + orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试删除采购订单
     */
    @Test
    public void testDeletePurchaseOrder() throws Exception {
        // 先创建一个采购订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        String createJson = objectMapper.writeValueAsString(orderDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long orderId = ((Number) createResultObj.getData()).longValue();

        // 删除采购订单
        mockMvc.perform(delete("/api/purchase/order/" + orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试确认订单
     */
    @Test
    public void testConfirmOrder() throws Exception {
        // 先创建一个采购订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        String createJson = objectMapper.writeValueAsString(orderDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long orderId = ((Number) createResultObj.getData()).longValue();

        // 确认订单
        mockMvc.perform(put("/api/purchase/order/" + orderId + "/confirm"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试取消订单
     */
    @Test
    public void testCancelOrder() throws Exception {
        // 先创建一个采购订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        String createJson = objectMapper.writeValueAsString(orderDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long orderId = ((Number) createResultObj.getData()).longValue();

        // 取消订单
        String cancelJson = "{\"cancelReason\":\"不需要了\"}";
        mockMvc.perform(put("/api/purchase/order/" + orderId + "/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cancelJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试发货
     */
    @Test
    public void testShipOrder() throws Exception {
        // 创建并确认订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        String createJson = objectMapper.writeValueAsString(orderDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long orderId = ((Number) createResultObj.getData()).longValue();

        mockMvc.perform(put("/api/purchase/order/" + orderId + "/confirm")).andReturn();

        // 发货
        String shipJson = "{\"trackingNumber\":\"SF123456789\",\"shippedDate\":\"2026-02-20\"}";
        mockMvc.perform(put("/api/purchase/order/" + orderId + "/ship")
                .contentType(MediaType.APPLICATION_JSON)
                .content(shipJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试收货
     */
    @Test
    public void testReceiveOrder() throws Exception {
        // 创建、确认并发货订单
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        String createJson = objectMapper.writeValueAsString(orderDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long orderId = ((Number) createResultObj.getData()).longValue();

        mockMvc.perform(put("/api/purchase/order/" + orderId + "/confirm")).andReturn();
        
        String shipJson = "{\"trackingNumber\":\"SF123456789\",\"shippedDate\":\"2026-02-20\"}";
        mockMvc.perform(put("/api/purchase/order/" + orderId + "/ship")
                .contentType(MediaType.APPLICATION_JSON)
                .content(shipJson))
                .andReturn();

        // 收货
        String receiveJson = "{\"receivedQuantity\":10.00,\"receiveRemark\":\"包装完好\"}";
        mockMvc.perform(put("/api/purchase/order/" + orderId + "/receive")
                .contentType(MediaType.APPLICATION_JSON)
                .content(receiveJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    /**
     * 测试完成订单
     */
    @Test
    public void testCompleteOrder() throws Exception {
        // 创建并完成整个流程
        PurchaseOrderDTO orderDTO = createTestOrderDTO();
        String createJson = objectMapper.writeValueAsString(orderDTO);
        
        MvcResult createResult = mockMvc.perform(post("/api/purchase/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andReturn();
        
        Result createResultObj = objectMapper.readValue(createResult.getResponse().getContentAsString(), Result.class);
        Long orderId = ((Number) createResultObj.getData()).longValue();

        mockMvc.perform(put("/api/purchase/order/" + orderId + "/confirm")).andReturn();
        
        String shipJson = "{\"trackingNumber\":\"SF123456789\",\"shippedDate\":\"2026-02-20\"}";
        mockMvc.perform(put("/api/purchase/order/" + orderId + "/ship")
                .contentType(MediaType.APPLICATION_JSON)
                .content(shipJson))
                .andReturn();

        String receiveJson = "{\"receivedQuantity\":10.00,\"receiveRemark\":\"包装完好\"}";
        mockMvc.perform(put("/api/purchase/order/" + orderId + "/receive")
                .contentType(MediaType.APPLICATION_JSON)
                .content(receiveJson))
                .andReturn();

        // 完成订单
        mockMvc.perform(put("/api/purchase/order/" + orderId + "/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
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
