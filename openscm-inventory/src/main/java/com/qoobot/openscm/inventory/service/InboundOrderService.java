package com.qoobot.openscm.inventory.service;

import com.qoobot.openscm.inventory.entity.InboundOrder;
import org.springframework.data.domain.Page;

/**
 * 入库单服务接口
 */
public interface InboundOrderService {

    /**
     * 创建入库单
     */
    Long createInboundOrder(InboundOrder inboundOrder);

    /**
     * 更新入库单
     */
    void updateInboundOrder(Long id, InboundOrder inboundOrder);

    /**
     * 删除入库单
     */
    void deleteInboundOrder(Long id);

    /**
     * 根据ID查询入库单
     */
    InboundOrder getInboundOrderById(Long id);

    /**
     * 分页查询入库单
     */
    Page<InboundOrder> getInboundOrdersByPage(int page, int size, Long warehouseId, String inboundCode, Integer status);

    /**
     * 提交入库
     */
    void submitInbound(Long id);

    /**
     * 完成入库
     */
    void completeInbound(Long id);

    /**
     * 审核入库单
     */
    void auditInbound(Long id, String auditor, String auditRemark);
}
