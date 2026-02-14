package com.qoobot.openscm.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.inventory.entity.OutboundOrder;
import com.qoobot.openscm.inventory.entity.OutboundOrderItem;
import com.qoobot.openscm.inventory.mapper.OutboundOrderItemMapper;
import com.qoobot.openscm.inventory.mapper.OutboundOrderMapper;
import com.qoobot.openscm.inventory.service.OutboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 出库单服务实现
 */
@Service
public class OutboundOrderServiceImpl implements OutboundOrderService {

    @Autowired
    private OutboundOrderMapper outboundOrderMapper;

    @Autowired
    private OutboundOrderItemMapper outboundOrderItemMapper;

    @Override
    @Transactional
    public Long createOutboundOrder(OutboundOrder outboundOrder) {
        outboundOrderMapper.insert(outboundOrder);
        return outboundOrder.getId();
    }

    @Override
    @Transactional
    public void updateOutboundOrder(Long id, OutboundOrder outboundOrder) {
        outboundOrder.setId(id);
        outboundOrderMapper.updateById(outboundOrder);
    }

    @Override
    @Transactional
    public void deleteOutboundOrder(Long id) {
        outboundOrderMapper.deleteById(id);
        // 删除关联的明细
        LambdaQueryWrapper<OutboundOrderItem> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.eq(OutboundOrderItem::getOutboundId, id);
        outboundOrderItemMapper.delete(itemWrapper);
    }

    @Override
    public OutboundOrder getOutboundOrderById(Long id) {
        return outboundOrderMapper.selectById(id);
    }

    @Override
    public Page<OutboundOrder> getOutboundOrdersByPage(int page, int size, Long warehouseId, String outboundCode, Integer status) {
        Page<OutboundOrder> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<OutboundOrder> queryWrapper = new LambdaQueryWrapper<>();
        
        if (warehouseId != null) {
            queryWrapper.eq(OutboundOrder::getWarehouseId, warehouseId);
        }
        if (StringUtils.hasText(outboundCode)) {
            queryWrapper.like(OutboundOrder::getOutboundCode, outboundCode);
        }
        if (status != null) {
            queryWrapper.eq(OutboundOrder::getStatus, status);
        }
        
        queryWrapper.orderByDesc(OutboundOrder::getCreatedAt);
        IPage<OutboundOrder> result = outboundOrderMapper.selectPage(pageParam, queryWrapper);
        
        return new org.springframework.data.domain.PageImpl<>(result.getRecords(), 
                org.springframework.data.domain.PageRequest.of(page - 1, size), 
                result.getTotal());
    }

    @Override
    @Transactional
    public void submitOutbound(Long id) {
        OutboundOrder outboundOrder = new OutboundOrder();
        outboundOrder.setId(id);
        outboundOrder.setStatus(1); // 出库中
        outboundOrderMapper.updateById(outboundOrder);
    }

    @Override
    @Transactional
    public void completeOutbound(Long id) {
        OutboundOrder outboundOrder = new OutboundOrder();
        outboundOrder.setId(id);
        outboundOrder.setStatus(2); // 已出库
        outboundOrderMapper.updateById(outboundOrder);
    }

    @Override
    @Transactional
    public void auditOutbound(Long id, String auditor, String auditRemark) {
        OutboundOrder outboundOrder = new OutboundOrder();
        outboundOrder.setId(id);
        outboundOrder.setStatus(3); // 已完成
        outboundOrder.setAuditor(auditor);
        outboundOrder.setAuditRemark(auditRemark);
        outboundOrder.setAuditTime(java.time.LocalDateTime.now());
        outboundOrderMapper.updateById(outboundOrder);
    }
}
