package com.qoobot.openscm.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.inventory.entity.InventoryCheck;
import com.qoobot.openscm.inventory.entity.InventoryCheckItem;
import com.qoobot.openscm.inventory.mapper.InventoryCheckItemMapper;
import com.qoobot.openscm.inventory.mapper.InventoryCheckMapper;
import com.qoobot.openscm.inventory.service.InventoryCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存盘点服务实现
 */
@Service
public class InventoryCheckServiceImpl implements InventoryCheckService {

    @Autowired
    private InventoryCheckMapper inventoryCheckMapper;

    @Autowired
    private InventoryCheckItemMapper inventoryCheckItemMapper;

    @Override
    @Transactional
    public Long createInventoryCheck(InventoryCheck inventoryCheck) {
        inventoryCheckMapper.insert(inventoryCheck);
        return inventoryCheck.getId();
    }

    @Override
    @Transactional
    public void updateInventoryCheck(Long id, InventoryCheck inventoryCheck) {
        inventoryCheck.setId(id);
        inventoryCheckMapper.updateById(inventoryCheck);
    }

    @Override
    @Transactional
    public void deleteInventoryCheck(Long id) {
        inventoryCheckMapper.deleteById(id);
        // 删除关联的明细
        LambdaQueryWrapper<InventoryCheckItem> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.eq(InventoryCheckItem::getCheckId, id);
        inventoryCheckItemMapper.delete(itemWrapper);
    }

    @Override
    public InventoryCheck getInventoryCheckById(Long id) {
        return inventoryCheckMapper.selectById(id);
    }

    @Override
    public Page<InventoryCheck> getInventoryChecksByPage(int page, int size, Long warehouseId, Integer status) {
        Page<InventoryCheck> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<InventoryCheck> queryWrapper = new LambdaQueryWrapper<>();
        
        if (warehouseId != null) {
            queryWrapper.eq(InventoryCheck::getWarehouseId, warehouseId);
        }
        if (status != null) {
            queryWrapper.eq(InventoryCheck::getStatus, status);
        }
        
        queryWrapper.orderByDesc(InventoryCheck::getCreatedAt);
        IPage<InventoryCheck> result = inventoryCheckMapper.selectPage(pageParam, queryWrapper);
        
        return new org.springframework.data.domain.PageImpl<>(result.getRecords(), 
                org.springframework.data.domain.PageRequest.of(page - 1, size), 
                result.getTotal());
    }

    @Override
    @Transactional
    public void startCheck(Long id) {
        InventoryCheck inventoryCheck = new InventoryCheck();
        inventoryCheck.setId(id);
        inventoryCheck.setStatus(1); // 盘点中
        inventoryCheckMapper.updateById(inventoryCheck);
    }

    @Override
    @Transactional
    public void completeCheck(Long id) {
        InventoryCheck inventoryCheck = new InventoryCheck();
        inventoryCheck.setId(id);
        inventoryCheck.setStatus(2); // 盘点完成
        inventoryCheckMapper.updateById(inventoryCheck);
    }

    @Override
    @Transactional
    public void auditCheck(Long id, String auditor) {
        InventoryCheck inventoryCheck = new InventoryCheck();
        inventoryCheck.setId(id);
        inventoryCheck.setStatus(3); // 已审核
        inventoryCheck.setAuditor(auditor);
        inventoryCheck.setAuditTime(java.time.LocalDateTime.now());
        inventoryCheckMapper.updateById(inventoryCheck);
    }
}
