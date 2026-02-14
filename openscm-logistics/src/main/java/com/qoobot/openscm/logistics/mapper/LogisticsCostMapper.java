package com.qoobot.openscm.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 物流费用Mapper接口
 */
@Mapper
public interface LogisticsCostMapper extends BaseMapper<com.qoobot.openscm.logistics.entity.LogisticsCost> {

    /**
     * 根据运输计划ID查询费用列表
     */
    List<com.qoobot.openscm.logistics.entity.LogisticsCost> selectByPlanId(@Param("planId") Long planId);

    /**
     * 根据配送单ID查询费用列表
     */
    List<com.qoobot.openscm.logistics.entity.LogisticsCost> selectByDeliveryId(@Param("deliveryId") Long deliveryId);

    /**
     * 根据费用编号查询
     */
    com.qoobot.openscm.logistics.entity.LogisticsCost selectByCostNo(@Param("costNo") String costNo);

    /**
     * 统计运输计划总费用
     */
    BigDecimal sumCostByPlanId(@Param("planId") Long planId);

    /**
     * 统计配送单总费用
     */
    BigDecimal sumCostByDeliveryId(@Param("deliveryId") Long deliveryId);
}
