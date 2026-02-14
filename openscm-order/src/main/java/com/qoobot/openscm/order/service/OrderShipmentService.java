package com.qoobot.openscm.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.order.entity.OrderShipment;

import java.util.List;

/**
 * 订单发货服务接口
 */
public interface OrderShipmentService extends IService<OrderShipment> {

    /**
     * 创建发货记录
     */
    Long createShipment(OrderShipment shipment);

    /**
     * 根据订单ID查询发货记录
     */
    List<OrderShipment> getShipmentsByOrderId(Long orderId);

    /**
     * 根据发货单号查询
     */
    OrderShipment getByShipmentNo(String shipmentNo);

    /**
     * 根据运单号查询
     */
    OrderShipment getByTrackingNo(String trackingNo);

    /**
     * 签收
     */
    void signForShipment(Long shipmentId);

    /**
     * 拒收
     */
    void rejectShipment(Long shipmentId, String reason);
}
