package com.qoobot.openscm.integration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.common.exception.BusinessException;
import com.qoobot.openscm.common.util.SecurityUtils;
import com.qoobot.openscm.integration.entity.MessageIntegration;
import com.qoobot.openscm.integration.mapper.MessageIntegrationMapper;
import com.qoobot.openscm.integration.service.MessageIntegrationService;
import com.qoobot.openscm.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * 消息集成服务实现
 */
@Slf4j
@Service
public class MessageIntegrationServiceImpl extends ServiceImpl<MessageIntegrationMapper, MessageIntegration> implements MessageIntegrationService {

    @Override
    public Page<MessageIntegration> selectPage(Page<MessageIntegration> page, String messageType, String sendMethod,
                                              String sendStatus, String receiver) {
        LambdaQueryWrapper<MessageIntegration> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MessageIntegration::getTenantId, SecurityUtils.getTenantId());
        if (messageType != null && !messageType.isEmpty()) {
            wrapper.eq(MessageIntegration::getMessageType, messageType);
        }
        if (sendMethod != null && !sendMethod.isEmpty()) {
            wrapper.eq(MessageIntegration::getSendMethod, sendMethod);
        }
        if (sendStatus != null && !sendStatus.isEmpty()) {
            wrapper.eq(MessageIntegration::getSendStatus, sendStatus);
        }
        if (receiver != null && !receiver.isEmpty()) {
            wrapper.like(MessageIntegration::getReceiver, receiver);
        }
        wrapper.orderByDesc(MessageIntegration::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean sendEmail(String to, String subject, String content, Map<String, Object> templateParams) {
        try {
            String messageId = "MSG" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            MessageIntegration message = new MessageIntegration();
            message.setMessageId(messageId);
            message.setMessageType("EMAIL");
            message.setSendMethod("EMAIL");
            message.setReceiver(to);
            message.setReceiverType("EMAIL");
            message.setMessageTitle(subject);
            message.setMessageContent(content);
            message.setSendStatus("PENDING");
            message.setSendTime(LocalDateTime.now());
            message.setTenantId(SecurityUtils.getTenantId());
            if (templateParams != null) {
                message.setTemplateParams(JacksonUtil.toJsonString(templateParams));
            }

            this.save(message);

            // TODO: 实现实际的邮件发送逻辑
            log.info("发送邮件: to={}, subject={}", to, subject);

            // 模拟发送成功
            message.setSendStatus("SUCCESS");
            this.updateById(message);

            return true;
        } catch (Exception e) {
            log.error("发送邮件失败", e);
            throw new BusinessException("发送邮件失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean sendSms(String phone, String templateId, Map<String, Object> params) {
        try {
            String messageId = "MSG" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            MessageIntegration message = new MessageIntegration();
            message.setMessageId(messageId);
            message.setMessageType("SMS");
            message.setSendMethod("SMS");
            message.setReceiver(phone);
            message.setReceiverType("PHONE");
            message.setTemplateId(templateId);
            message.setSendStatus("PENDING");
            message.setSendTime(LocalDateTime.now());
            message.setTenantId(SecurityUtils.getTenantId());
            if (params != null) {
                message.setTemplateParams(JacksonUtil.toJsonString(params));
            }

            this.save(message);

            // TODO: 实现实际的短信发送逻辑
            log.info("发送短信: phone={}, templateId={}", phone, templateId);

            // 模拟发送成功
            message.setSendStatus("SUCCESS");
            this.updateById(message);

            return true;
        } catch (Exception e) {
            log.error("发送短信失败", e);
            throw new BusinessException("发送短信失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean sendStationMessage(Long userId, String title, String content) {
        try {
            String messageId = "MSG" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            MessageIntegration message = new MessageIntegration();
            message.setMessageId(messageId);
            message.setMessageType("STATION");
            message.setSendMethod("STATION");
            message.setReceiver(userId.toString());
            message.setReceiverType("USER");
            message.setMessageTitle(title);
            message.setMessageContent(content);
            message.setSendStatus("SUCCESS");
            message.setSendTime(LocalDateTime.now());
            message.setTenantId(SecurityUtils.getTenantId());

            this.save(message);

            log.info("发送站内信: userId={}, title={}", userId, title);

            return true;
        } catch (Exception e) {
            log.error("发送站内信失败", e);
            throw new BusinessException("发送站内信失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchSendSms(String[] phones, String templateId, Map<String, Object> params) {
        try {
            for (String phone : phones) {
                sendSms(phone, templateId, params);
            }
            return true;
        } catch (Exception e) {
            log.error("批量发送短信失败", e);
            throw new BusinessException("批量发送短信失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean resendMessage(Long id) {
        MessageIntegration message = this.getById(id);
        if (message == null) {
            throw new BusinessException("消息记录不存在");
        }

        if ("SUCCESS".equals(message.getSendStatus())) {
            throw new BusinessException("消息已发送成功，无需重发");
        }

        try {
            // TODO: 实现实际的消息重发逻辑
            log.info("重新发送消息: messageId={}", message.getMessageId());

            message.setSendStatus("SUCCESS");
            message.setSendTime(LocalDateTime.now());
            this.updateById(message);

            return true;
        } catch (Exception e) {
            log.error("重新发送消息失败", e);
            message.setFailureCount(message.getFailureCount() + 1);
            message.setErrorMessage(e.getMessage());
            this.updateById(message);
            throw new BusinessException("重新发送消息失败: " + e.getMessage());
        }
    }

    @Override
    public String queryMessageStatus(String messageId) {
        MessageIntegration message = this.lambdaQuery()
                .eq(MessageIntegration::getMessageId, messageId)
                .one();

        if (message == null) {
            throw new BusinessException("消息记录不存在");
        }

        return message.getSendStatus();
    }
}
