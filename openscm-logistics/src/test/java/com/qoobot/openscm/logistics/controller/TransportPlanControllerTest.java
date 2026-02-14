package com.qoobot.openscm.logistics.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 运输计划控制器测试
 */
@SpringBootTest
@AutoConfigureMockMvc
class TransportPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPlanPage() throws Exception {
        mockMvc.perform(get("/logistics/transport/page")
                .param("current", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testStatistics() throws Exception {
        mockMvc.perform(get("/logistics/transport/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testGetTrackingByWaybillNo() throws Exception {
        mockMvc.perform(get("/logistics/transport/tracking/TP1234567890"))
                .andExpect(status().isOk());
    }

    @Test
    void testStartTransport() throws Exception {
        mockMvc.perform(post("/logistics/transport/start/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCompleteTransport() throws Exception {
        mockMvc.perform(post("/logistics/transport/complete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCancelPlan() throws Exception {
        mockMvc.perform(post("/logistics/transport/cancel/1"))
                .andExpect(status().isOk());
    }
}
