package com.qoobot.openscm.integration.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息集成服务测试
 */
@SpringBootTest
class MessageIntegrationServiceTest {

    @Autowired
    private MessageIntegrationService messageIntegrationService;

    @Test
    void testSelectPage() {
        Page<com.qoobot.openscm.integration.entity.MessageIntegration> page = new Page<>(1, 10);
        Page<com.qoobot.openscm.integration.entity.MessageIntegration> result =
                messageIntegrationService.selectPage(page, null, null, null, null);
        System.out.println("查询消息记录成功，记录数: " + result.getRecords().size());
    }

    @Test
    void testSendEmail() {
        try {
            boolean result = messageIntegrationService.sendEmail(
                    "test@example.com", "测试邮件", "这是一封测试邮件", null);
            System.out.println("发送邮件: " + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("发送邮件异常: " + e.getMessage());
        }
    }

    @Test
    void testSendSms() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("code", "123456");
            params.put("time", "5分钟");

            boolean result = messageIntegrationService.sendSms(
                    "13800138000", "SMS001", params);
            System.out.println("发送短信: " + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("发送短信异常: " + e.getMessage());
        }
    }

    @Test
    void testSendStationMessage() {
        try {
            boolean result = messageIntegrationService.sendStationMessage(
                    1L, "测试消息", "这是一条测试站内消息");
            System.out.println("发送站内信: " + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("发送站内信异常: " + e.getMessage());
        }
    }
}
