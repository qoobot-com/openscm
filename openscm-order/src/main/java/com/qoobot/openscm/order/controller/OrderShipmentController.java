package com.qoobot.openscm.order.controller;

import com.qoobot.openscm.order.entity.OrderShipment;
import com.qoobot.openscm.order.service.OrderShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单发货控制器
 */
@RestController
@RequestMapping("/order/shipment")
public class OrderShipmentController {

    @Autowired
    private OrderShipmentService orderShipmentService;

    /**
     * 创建发货记录
     */
    @PostMapping("/create")
    public Result<Long> createShipment(@RequestBody OrderShipment shipment) {
        Long shipmentId = orderShipmentService.createShipment(shipment);
        return Result.success(shipmentId);
    }

    /**
     * 根据订单ID查询发货记录
     */
    @GetMapping("/order/{orderId}")
    public Result<List<OrderShipment>> getShipmentsByOrderId(@PathVariable Long orderId) {
        List<OrderShipment> shipments = orderShipmentService.getShipmentsByOrderId(orderId);
        return Result.success(shipments);
    }

    /**
     * 根据发货单号查询
     */
    @GetMapping("/shipmentNo/{shipmentNo}")
    public Result<OrderShipment> getByShipmentNo(@PathVariable String shipmentNo) {
        OrderShipment shipment = orderShipmentService.getByShipmentNo(shipmentNo);
        return Result.success(shipment);
    }

    /**
     * 根据运单号查询
     */
    @GetMapping("/trackingNo/{trackingNo}")
    public Result<OrderShipment> getByTrackingNo(@PathVariable String trackingNo) {
        OrderShipment shipment = orderShipmentService.getByTrackingNo(trackingNo);
        return Result.success(shipment);
    }

    /**
     * 签收
     */
    @PostMapping("/sign/{shipmentId}")
    public Result<Void> signForShipment(@PathVariable Long shipmentId) {
        orderShipmentService.signForShipment(shipmentId);
        return Result.success();
    }

    /**
     * 拒收
     */
    @PostMapping("/reject/{shipmentId}")
    public Result<Void> rejectShipment(@PathVariable Long shipmentId, @RequestParam String reason) {
        orderShipmentService.rejectShipment(shipmentId, reason);
        return Result.success();
    }
}
