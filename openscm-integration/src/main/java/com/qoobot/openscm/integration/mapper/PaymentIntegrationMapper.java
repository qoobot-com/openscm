package com.qoobot.openscm.integration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.integration.entity.PaymentIntegration;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付集成Mapper
 */
@Mapper
public interface PaymentIntegrationMapper extends BaseMapper<PaymentIntegration> {
}
