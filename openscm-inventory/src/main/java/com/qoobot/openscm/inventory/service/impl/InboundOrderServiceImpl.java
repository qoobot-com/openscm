package com.qoobot.openscm.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.inventory.entity.InboundOrder;
import com.qoobot.openscm.inventory.entity.InboundOrderItem;
import com.qoobot.openscm.inventory.mapper.InboundOrderItemMapper;
import com.qoobot.openscm.inventory.mapper.InboundOrderMapper;
import com.qoobot.openscm.inventory.service.InboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 入库单服务实现
 */
@Service
public class InboundOrderServiceImpl implements InboundOrderService {

    @Autowired
    private InboundOrderMapper inboundOrderMapper;

    @Autowired
    private InboundOrderItemMapper inboundOrderItemMapper;

    @Override
    @Transactional
    public Long createInboundOrder(InboundOrder inboundOrder) {
        inboundOrderMapper.insert(inboundOrder);
        return inboundOrder.getId();
    }

    @Override
    @Transactional
    public void updateInboundOrder(Long id, InboundOrder inboundOrder) {
        inboundOrder.setId(id);
        inboundOrderMapper.updateById(inboundOrder);
    }

    @Override
    @Transactional
    public void deleteInboundOrder(Long id) {
        inboundOrderMapper.deleteById(id);
        // 删除关联的明细
        LambdaQueryWrapper<InboundOrderItem> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.eq(InboundOrderItem::getInboundId, id);
        inboundOrderItemMapper.delete(itemWrapper);
    }

    @Override
    public InboundOrder getInboundOrderById(Long id) {
        return inboundOrderMapper.selectById(id);
    }

    @Override
    public Page<InboundOrder> getInboundOrdersByPage(int page, int size, Long warehouseId, String inboundCode, Integer status) {
        Page<InboundOrder> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<InboundOrder> queryWrapper = new LambdaQueryWrapper<>();
        
        if (warehouseId != null) {
            queryWrapper.eq(InboundOrder::getWarehouseId, warehouseId);
        }
        if (StringUtils.hasText(inboundCode)) {
            queryWrapper.like(InboundOrder::getInboundCode, inboundCode);
        }
        if (status != null) {
            queryWrapper.eq(InboundOrder::getStatus, status);
        }
        
        queryWrapper.orderByDesc(InboundOrder::getCreatedAt);
        IPage<InboundOrder> result = inboundOrderMapper.selectPage(pageParam, queryWrapper);
        
        return new org.springframework.data.domain.PageImpl<>(result.getRecords(), 
                org.springframework.data.domain.PageRequest.of(page - 1, size), 
                result.getTotal());
    }

    @Override
    @Transactional
    public void submitInbound(Long id) {
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setId(id);
        inboundOrder.setStatus(1); // 入库中
        inboundOrderMapper.updateById(inboundOrder);
    }

    @Override
    @Transactional
    public void completeInbound(Long id) {
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setId(id);
        inboundOrder.setStatus(2); // 已入库
        inboundOrderMapper.updateById(inboundOrder);
    }

    @Override
    @Transactional
    public void auditInbound(Long id, String auditor, String auditRemark) {
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setId(id);
        inboundOrder.setStatus(3); // 已完成
        inboundOrder.setAuditor(auditor);
        inboundOrder.setAuditRemark(auditRemark);
        inboundOrder.setAuditTime(java.time.LocalDateTime.now());
        inboundOrderMapper.updateById(inboundOrder);
    }
}
