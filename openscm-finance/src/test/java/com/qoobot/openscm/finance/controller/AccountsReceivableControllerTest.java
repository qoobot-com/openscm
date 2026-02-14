package com.qoobot.openscm.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 应收账款控制器测试
 */
@SpringBootTest
@AutoConfigureMockMvc
class AccountsReceivableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateReceivable() throws Exception {
        com.qoobot.openscm.finance.entity.AccountsReceivable receivable = 
            new com.qoobot.openscm.finance.entity.AccountsReceivable();
        receivable.setOrderId(1L);
        receivable.setOrderNo("SO001");
        receivable.setCustomerId(1L);
        receivable.setCustomerName("测试客户");
        receivable.setAmount(new BigDecimal("10000.00"));
        receivable.setReceivableDate(java.time.LocalDate.now());
        receivable.setDueDate(java.time.LocalDate.now().plusDays(30));
        receivable.setBusinessType(0);
        receivable.setCreatedBy("test");

        mockMvc.perform(post("/finance/receivable/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(receivable)))
                .andExpect(status().isOk());
    }

    @Test
    void testReceivablePage() throws Exception {
        mockMvc.perform(get("/finance/receivable/page")
                .param("current", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testReceivePayment() throws Exception {
        mockMvc.perform(post("/finance/receivable/receive/1")
                .param("amount", "5000.00"))
                .andExpect(status().isOk());
    }

    @Test
    void testWriteOff() throws Exception {
        mockMvc.perform(post("/finance/receivable/writeOff/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testStatistics() throws Exception {
        mockMvc.perform(get("/finance/receivable/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testSummaryByCustomer() throws Exception {
        mockMvc.perform(get("/finance/receivable/summaryByCustomer"))
                .andExpect(status().isOk());
    }
}
