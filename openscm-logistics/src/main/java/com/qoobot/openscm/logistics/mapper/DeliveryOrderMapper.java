package com.qoobot.openscm.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.logistics.entity.DeliveryOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配送单Mapper接口
 */
@Mapper
public interface DeliveryOrderMapper extends BaseMapper<DeliveryOrder> {

    /**
     * 分页查询配送单
     */
    IPage<DeliveryOrder> selectDeliveryPage(Page<DeliveryOrder> page,
                                            @Param("deliveryNo") String deliveryNo,
                                            @Param("orderId") Long orderId,
                                            @Param("status") Integer status,
                                            @Param("startDate") String startDate,
                                            @Param("endDate") String endDate);

    /**
     * 根据配送单号查询
     */
    DeliveryOrder selectByDeliveryNo(@Param("deliveryNo") String deliveryNo);

    /**
     * 根据订单ID查询配送单
     */
    List<DeliveryOrder> selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据运输计划ID查询配送单
     */
    List<DeliveryOrder> selectByPlanId(@Param("planId") Long planId);
}
