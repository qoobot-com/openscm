package com.qoobot.openscm.integration.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.integration.entity.MessageIntegration;

import java.util.Map;

/**
 * 消息集成服务接口
 */
public interface MessageIntegrationService extends IService<MessageIntegration> {

    /**
     * 分页查询消息记录
     */
    Page<MessageIntegration> selectPage(Page<MessageIntegration> page, String messageType, String sendMethod,
                                        String sendStatus, String receiver);

    /**
     * 发送邮件
     */
    Boolean sendEmail(String to, String subject, String content, Map<String, Object> templateParams);

    /**
     * 发送短信
     */
    Boolean sendSms(String phone, String templateId, Map<String, Object> params);

    /**
     * 发送站内信
     */
    Boolean sendStationMessage(Long userId, String title, String content);

    /**
     * 批量发送短信
     */
    Boolean batchSendSms(String[] phones, String templateId, Map<String, Object> params);

    /**
     * 重新发送消息
     */
    Boolean resendMessage(Long id);

    /**
     * 查询消息发送状态
     */
    String queryMessageStatus(String messageId);
}
