package com.qoobot.openscm.logistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.logistics.entity.DeliveryOrder;

/**
 * 配送单服务接口
 */
public interface DeliveryOrderService extends IService<DeliveryOrder> {

    /**
     * 创建配送单
     */
    Long createDelivery(DeliveryOrder delivery);

    /**
     * 分页查询配送单
     */
    IPage<DeliveryOrder> deliveryPage(Page<DeliveryOrder> page, String deliveryNo, Long orderId,
                                        Integer status, String startDate, String endDate);

    /**
     * 开始配送
     */
    void startDelivery(Long deliveryId);

    /**
     * 签收
     */
    void signDelivery(Long deliveryId, String signer, String signPhoto);

    /**
     * 拒收
     */
    void rejectDelivery(Long deliveryId, String reason);

    /**
     * 取消配送单
     */
    void cancelDelivery(Long deliveryId);
}
