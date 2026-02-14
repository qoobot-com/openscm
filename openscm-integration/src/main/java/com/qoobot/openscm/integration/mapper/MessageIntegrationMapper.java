package com.qoobot.openscm.integration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.integration.entity.MessageIntegration;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息集成Mapper
 */
@Mapper
public interface MessageIntegrationMapper extends BaseMapper<MessageIntegration> {
}
