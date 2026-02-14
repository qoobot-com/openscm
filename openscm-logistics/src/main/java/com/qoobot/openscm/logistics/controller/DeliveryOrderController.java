package com.qoobot.openscm.logistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.common.core.domain.Result;
import com.qoobot.openscm.logistics.entity.DeliveryOrder;
import com.qoobot.openscm.logistics.service.DeliveryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 配送单控制器
 */
@RestController
@RequestMapping("/logistics/delivery")
public class DeliveryOrderController {

    @Autowired
    private DeliveryOrderService deliveryOrderService;

    /**
     * 创建配送单
     */
    @PostMapping("/create")
    public Result<Long> createDelivery(@RequestBody DeliveryOrder delivery) {
        Long deliveryId = deliveryOrderService.createDelivery(delivery);
        return Result.success(deliveryId);
    }

    /**
     * 分页查询配送单
     */
    @GetMapping("/page")
    public Result<IPage<DeliveryOrder>> deliveryPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String deliveryNo,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        Page<DeliveryOrder> page = new Page<>(current, size);
        IPage<DeliveryOrder> result = deliveryOrderService.deliveryPage(page, deliveryNo, orderId, status, startDate, endDate);
        return Result.success(result);
    }

    /**
     * 开始配送
     */
    @PostMapping("/start/{deliveryId}")
    public Result<Void> startDelivery(@PathVariable Long deliveryId) {
        deliveryOrderService.startDelivery(deliveryId);
        return Result.success();
    }

    /**
     * 签收
     */
    @PostMapping("/sign/{deliveryId}")
    public Result<Void> signDelivery(@PathVariable Long deliveryId, 
                                       @RequestParam String signer,
                                       @RequestParam(required = false) String signPhoto) {
        deliveryOrderService.signDelivery(deliveryId, signer, signPhoto);
        return Result.success();
    }

    /**
     * 拒收
     */
    @PostMapping("/reject/{deliveryId}")
    public Result<Void> rejectDelivery(@PathVariable Long deliveryId, @RequestParam String reason) {
        deliveryOrderService.rejectDelivery(deliveryId, reason);
        return Result.success();
    }

    /**
     * 取消配送单
     */
    @PostMapping("/cancel/{deliveryId}")
    public Result<Void> cancelDelivery(@PathVariable Long deliveryId) {
        deliveryOrderService.cancelDelivery(deliveryId);
        return Result.success();
    }
}
