package com.qoobot.openscm.integration.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.integration.entity.MessageIntegration;
import com.qoobot.openscm.integration.service.MessageIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 消息集成控制器
 */
@Tag(name = "消息集成管理", description = "消息集成相关接口")
@RestController
@RequestMapping("/api/integration/message")
@RequiredArgsConstructor
@Validated
public class MessageIntegrationController {

    private final MessageIntegrationService messageIntegrationService;

    @Operation(summary = "分页查询消息记录")
    @GetMapping("/page")
    public Result<Page<MessageIntegration>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String messageType,
            @RequestParam(required = false) String sendMethod,
            @RequestParam(required = false) String sendStatus,
            @RequestParam(required = false) String receiver) {
        Page<MessageIntegration> page = new Page<>(current, size);
        return Result.success(messageIntegrationService.selectPage(page, messageType, sendMethod, sendStatus, receiver));
    }

    @Operation(summary = "根据ID查询消息记录")
    @GetMapping("/{id}")
    public Result<MessageIntegration> getById(@PathVariable Long id) {
        return Result.success(messageIntegrationService.getById(id));
    }

    @Operation(summary = "发送邮件")
    @PostMapping("/email")
    public Result<Boolean> sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String content,
            @RequestParam(required = false) String templateParams) {
        Map<String, Object> params = templateParams != null ?
                com.qoobot.openscm.common.util.JacksonUtil.parseMap(templateParams) : null;
        return Result.success(messageIntegrationService.sendEmail(to, subject, content, params));
    }

    @Operation(summary = "发送短信")
    @PostMapping("/sms")
    public Result<Boolean> sendSms(
            @RequestParam String phone,
            @RequestParam String templateId,
            @RequestParam(required = false) String params) {
        Map<String, Object> paramMap = params != null ?
                com.qoobot.openscm.common.util.JacksonUtil.parseMap(params) : null;
        return Result.success(messageIntegrationService.sendSms(phone, templateId, paramMap));
    }

    @Operation(summary = "发送站内信")
    @PostMapping("/station")
    public Result<Boolean> sendStationMessage(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam String content) {
        return Result.success(messageIntegrationService.sendStationMessage(userId, title, content));
    }

    @Operation(summary = "批量发送短信")
    @PostMapping("/sms/batch")
    public Result<Boolean> batchSendSms(
            @RequestParam String[] phones,
            @RequestParam String templateId,
            @RequestParam(required = false) String params) {
        Map<String, Object> paramMap = params != null ?
                com.qoobot.openscm.common.util.JacksonUtil.parseMap(params) : null;
        return Result.success(messageIntegrationService.batchSendSms(phones, templateId, paramMap));
    }

    @Operation(summary = "重新发送消息")
    @PostMapping("/{id}/resend")
    public Result<Boolean> resendMessage(@PathVariable Long id) {
        return Result.success(messageIntegrationService.resendMessage(id));
    }

    @Operation(summary = "查询消息发送状态")
    @GetMapping("/status")
    public Result<String> queryMessageStatus(@RequestParam String messageId) {
        return Result.success(messageIntegrationService.queryMessageStatus(messageId));
    }
}
