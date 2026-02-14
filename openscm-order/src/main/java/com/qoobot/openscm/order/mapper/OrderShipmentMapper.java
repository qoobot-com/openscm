package com.qoobot.openscm.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单发货记录Mapper接口
 */
@Mapper
public interface OrderShipmentMapper extends BaseMapper<OrderShipment> {

    /**
     * 根据订单ID查询发货记录
     */
    List<OrderShipment> selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据发货单号查询
     */
    OrderShipment selectByShipmentNo(@Param("shipmentNo") String shipmentNo);

    /**
     * 根据运单号查询
     */
    OrderShipment selectByTrackingNo(@Param("trackingNo") String trackingNo);
}
