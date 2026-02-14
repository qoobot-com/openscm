package com.qoobot.openscm.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.order.entity.OrderShipment;
import com.qoobot.openscm.order.mapper.OrderShipmentMapper;
import com.qoobot.openscm.order.service.OrderShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单发货服务实现
 */
@Service
public class OrderShipmentServiceImpl extends ServiceImpl<OrderShipmentMapper, OrderShipment> implements OrderShipmentService {

    @Autowired
    private OrderShipmentMapper orderShipmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createShipment(OrderShipment shipment) {
        // 生成发货单号
        String shipmentNo = generateShipmentNo();
        shipment.setShipmentNo(shipmentNo);
        shipment.setStatus(0); // 待发货
        shipment.setShipmentTime(java.time.LocalDateTime.now());
        
        orderShipmentMapper.insert(shipment);
        return shipment.getId();
    }

    @Override
    public List<OrderShipment> getShipmentsByOrderId(Long orderId) {
        return orderShipmentMapper.selectByOrderId(orderId);
    }

    @Override
    public OrderShipment getByShipmentNo(String shipmentNo) {
        return orderShipmentMapper.selectByShipmentNo(shipmentNo);
    }

    @Override
    public OrderShipment getByTrackingNo(String trackingNo) {
        return orderShipmentMapper.selectByTrackingNo(trackingNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void signForShipment(Long shipmentId) {
        OrderShipment shipment = orderShipmentMapper.selectById(shipmentId);
        if (shipment == null) {
            throw new RuntimeException("发货记录不存在");
        }
        if (shipment.getStatus() != 1) {
            throw new RuntimeException("发货状态不正确");
        }
        shipment.setStatus(2); // 已签收
        shipment.setActualDeliveryTime(java.time.LocalDateTime.now());
        orderShipmentMapper.updateById(shipment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectShipment(Long shipmentId, String reason) {
        OrderShipment shipment = orderShipmentMapper.selectById(shipmentId);
        if (shipment == null) {
            throw new RuntimeException("发货记录不存在");
        }
        shipment.setStatus(3); // 已拒收
        shipment.setRemark(reason);
        orderShipmentMapper.updateById(shipment);
    }

    private String generateShipmentNo() {
        return "SHIP" + System.currentTimeMillis();
    }
}
