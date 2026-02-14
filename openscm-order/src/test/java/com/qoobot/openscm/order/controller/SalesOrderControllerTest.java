package com.qoobot.openscm.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 销售订单控制器测试
 */
@SpringBootTest
@AutoConfigureMockMvc
class SalesOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateOrder() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", 1L);
        params.put("customerName", "测试客户");
        params.put("paymentMethod", 1);
        params.put("deliveryAddress", "测试地址");
        params.put("contactPerson", "张三");
        params.put("contactPhone", "13800138000");
        params.put("remark", "测试订单");
        params.put("createdBy", "test");
        params.put("items", List.of());

        mockMvc.perform(post("/order/sales/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk());
    }

    @Test
    void testOrderPage() throws Exception {
        mockMvc.perform(get("/order/sales/page")
                .param("current", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testGetOrderDetail() throws Exception {
        mockMvc.perform(get("/order/sales/detail/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testConfirmOrder() throws Exception {
        mockMvc.perform(post("/order/sales/confirm/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCancelOrder() throws Exception {
        mockMvc.perform(post("/order/sales/cancel/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateOrderStatus() throws Exception {
        mockMvc.perform(post("/order/sales/status/1")
                .param("status", "2"))
                .andExpect(status().isOk());
    }

    @Test
    void testOrderStatistics() throws Exception {
        mockMvc.perform(get("/order/sales/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
