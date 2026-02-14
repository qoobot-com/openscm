package com.qoobot.openscm.integration.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qoobot.openscm.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 消息集成实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("message_integration")
public class MessageIntegration extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 发送方式
     */
    private String sendMethod;

    /**
     * 接收方
     */
    private String receiver;

    /**
     * 接收方类型
     */
    private String receiverType;

    /**
     * 消息标题
     */
    private String messageTitle;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 模板参数
     */
    private String templateParams;

    /**
     * 发送状态
     */
    private String sendStatus;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 失败次数
     */
    private Integer failureCount;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 发送IP
     */
    private String sendIp;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务单号
     */
    private String businessNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户ID
     */
    private Long tenantId;
}
