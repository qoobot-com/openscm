package com.qoobot.openscm.logistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.logistics.entity.DeliveryOrder;
import com.qoobot.openscm.logistics.entity.LogisticsTracking;
import com.qoobot.openscm.logistics.mapper.DeliveryOrderMapper;
import com.qoobot.openscm.logistics.service.DeliveryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 配送单服务实现
 */
@Service
public class DeliveryOrderServiceImpl extends ServiceImpl<DeliveryOrderMapper, DeliveryOrder> implements DeliveryOrderService {

    @Autowired
    private DeliveryOrderMapper deliveryOrderMapper;

    @Autowired
    private com.qoobot.openscm.logistics.mapper.LogisticsTrackingMapper logisticsTrackingMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDelivery(DeliveryOrder delivery) {
        // 生成配送单号
        String deliveryNo = generateDeliveryNo();
        delivery.setDeliveryNo(deliveryNo);
        delivery.setStatus(0); // 待配送

        deliveryOrderMapper.insert(delivery);

        // 创建初始轨迹
        LogisticsTracking tracking = new LogisticsTracking();
        tracking.setTrackingNo("TRK" + System.currentTimeMillis());
        tracking.setDeliveryId(delivery.getId());
        tracking.setWaybillNo(deliveryNo);
        tracking.setNode(0);
        tracking.setNodeName("已下单");
        tracking.setNodeDesc("配送单已创建");
        tracking.setNodeTime(java.time.LocalDateTime.now());
        logisticsTrackingMapper.insert(tracking);

        return delivery.getId();
    }

    @Override
    public IPage<DeliveryOrder> deliveryPage(Page<DeliveryOrder> page, String deliveryNo, Long orderId,
                                                Integer status, String startDate, String endDate) {
        return deliveryOrderMapper.selectDeliveryPage(page, deliveryNo, orderId, status, startDate, endDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startDelivery(Long deliveryId) {
        DeliveryOrder delivery = deliveryOrderMapper.selectById(deliveryId);
        if (delivery == null) {
            throw new RuntimeException("配送单不存在");
        }
        if (delivery.getStatus() != 0) {
            throw new RuntimeException("配送状态不正确");
        }
        delivery.setStatus(1); // 配送中
        deliveryOrderMapper.updateById(delivery);

        // 记录轨迹
        LogisticsTracking tracking = new LogisticsTracking();
        tracking.setTrackingNo("TRK" + System.currentTimeMillis());
        tracking.setDeliveryId(deliveryId);
        tracking.setWaybillNo(delivery.getDeliveryNo());
        tracking.setNode(3);
        tracking.setNodeName("派送中");
        tracking.setNodeDesc("配送员已出发，开始派送");
        tracking.setNodeTime(java.time.LocalDateTime.now());
        logisticsTrackingMapper.insert(tracking);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void signDelivery(Long deliveryId, String signer, String signPhoto) {
        DeliveryOrder delivery = deliveryOrderMapper.selectById(deliveryId);
        if (delivery == null) {
            throw new RuntimeException("配送单不存在");
        }
        if (delivery.getStatus() != 1) {
            throw new RuntimeException("配送状态不正确");
        }
        delivery.setStatus(2); // 已签收
        delivery.setSigner(signer);
        delivery.setSignTime(java.time.LocalDateTime.now());
        delivery.setSignPhoto(signPhoto);
        deliveryOrderMapper.updateById(delivery);

        // 记录轨迹
        LogisticsTracking tracking = new LogisticsTracking();
        tracking.setTrackingNo("TRK" + System.currentTimeMillis());
        tracking.setDeliveryId(deliveryId);
        tracking.setWaybillNo(delivery.getDeliveryNo());
        tracking.setNode(4);
        tracking.setNodeName("已签收");
        tracking.setNodeDesc("货物已签收，配送完成");
        tracking.setNodeTime(java.time.LocalDateTime.now());
        logisticsTrackingMapper.insert(tracking);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectDelivery(Long deliveryId, String reason) {
        DeliveryOrder delivery = deliveryOrderMapper.selectById(deliveryId);
        if (delivery == null) {
            throw new RuntimeException("配送单不存在");
        }
        delivery.setStatus(3); // 已拒收
        delivery.setRemark(reason);
        deliveryOrderMapper.updateById(delivery);

        // 记录轨迹
        LogisticsTracking tracking = new LogisticsTracking();
        tracking.setTrackingNo("TRK" + System.currentTimeMillis());
        tracking.setDeliveryId(deliveryId);
        tracking.setWaybillNo(delivery.getDeliveryNo());
        tracking.setNode(5);
        tracking.setNodeName("已拒收");
        tracking.setNodeDesc("收货人拒收：" + reason);
        tracking.setNodeTime(java.time.LocalDateTime.now());
        logisticsTrackingMapper.insert(tracking);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelDelivery(Long deliveryId) {
        DeliveryOrder delivery = deliveryOrderMapper.selectById(deliveryId);
        if (delivery == null) {
            throw new RuntimeException("配送单不存在");
        }
        if (delivery.getStatus() >= 2) {
            throw new RuntimeException("配送已完成，无法取消");
        }
        delivery.setStatus(4); // 已取消
        deliveryOrderMapper.updateById(delivery);
    }

    private String generateDeliveryNo() {
        return "DLV" + System.currentTimeMillis();
    }
}
