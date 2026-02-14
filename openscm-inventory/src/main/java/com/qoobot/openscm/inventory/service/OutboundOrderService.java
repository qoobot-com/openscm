package com.qoobot.openscm.inventory.service;

import com.qoobot.openscm.inventory.entity.OutboundOrder;
import org.springframework.data.domain.Page;

/**
 * 出库单服务接口
 */
public interface OutboundOrderService {

    /**
     * 创建出库单
     */
    Long createOutboundOrder(OutboundOrder outboundOrder);

    /**
     * 更新出库单
     */
    void updateOutboundOrder(Long id, OutboundOrder outboundOrder);

    /**
     * 删除出库单
     */
    void deleteOutboundOrder(Long id);

    /**
     * 根据ID查询出库单
     */
    OutboundOrder getOutboundOrderById(Long id);

    /**
     * 分页查询出库单
     */
    Page<OutboundOrder> getOutboundOrdersByPage(int page, int size, Long warehouseId, String outboundCode, Integer status);

    /**
     * 提交出库
     */
    void submitOutbound(Long id);

    /**
     * 完成出库
     */
    void completeOutbound(Long id);

    /**
     * 审核出库单
     */
    void auditOutbound(Long id, String auditor, String auditRemark);
}
