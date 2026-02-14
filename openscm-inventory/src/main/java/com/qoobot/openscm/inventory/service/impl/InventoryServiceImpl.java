package com.qoobot.openscm.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.inventory.entity.Inventory;
import com.qoobot.openscm.inventory.mapper.InventoryMapper;
import com.qoobot.openscm.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 库存信息服务实现
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    @Transactional
    public void addInventory(Long warehouseId, Long locationId, String materialCode, BigDecimal quantity,
                             BigDecimal unitPrice, String batchNumber, Long supplierId) {
        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Inventory::getWarehouseId, warehouseId)
                  .eq(Inventory::getMaterialCode, materialCode)
                  .eq(Inventory::getLocationId, locationId)
                  .eq(Inventory::getBatchNumber, batchNumber);
        
        Inventory inventory = inventoryMapper.selectOne(queryWrapper);
        if (inventory == null) {
            inventory = new Inventory();
            inventory.setWarehouseId(warehouseId);
            inventory.setLocationId(locationId);
            inventory.setMaterialCode(materialCode);
            inventory.setQuantity(quantity);
            inventory.setAvailableQuantity(quantity);
            inventory.setLockedQuantity(BigDecimal.ZERO);
            inventory.setUnitPrice(unitPrice);
            inventory.setAmount(quantity.multiply(unitPrice != null ? unitPrice : BigDecimal.ZERO));
            inventory.setBatchNumber(batchNumber);
            inventory.setSupplierId(supplierId);
            inventoryMapper.insert(inventory);
        } else {
            BigDecimal newQuantity = inventory.getQuantity().add(quantity);
            BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().add(quantity);
            inventory.setQuantity(newQuantity);
            inventory.setAvailableQuantity(newAvailableQuantity);
            inventory.setAmount(newQuantity.multiply(inventory.getUnitPrice() != null ? inventory.getUnitPrice() : BigDecimal.ZERO));
            inventoryMapper.updateById(inventory);
        }
    }

    @Override
    @Transactional
    public void reduceInventory(Long warehouseId, String materialCode, BigDecimal quantity) {
        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Inventory::getWarehouseId, warehouseId)
                  .eq(Inventory::getMaterialCode, materialCode)
                  .gt(Inventory::getAvailableQuantity, quantity);
        
        Inventory inventory = inventoryMapper.selectOne(queryWrapper);
        if (inventory != null) {
            BigDecimal newQuantity = inventory.getQuantity().subtract(quantity);
            BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().subtract(quantity);
            inventory.setQuantity(newQuantity);
            inventory.setAvailableQuantity(newAvailableQuantity);
            inventoryMapper.updateById(inventory);
        }
    }

    @Override
    @Transactional
    public void lockInventory(Long warehouseId, String materialCode, BigDecimal quantity) {
        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Inventory::getWarehouseId, warehouseId)
                  .eq(Inventory::getMaterialCode, materialCode)
                  .gt(Inventory::getAvailableQuantity, quantity);
        
        Inventory inventory = inventoryMapper.selectOne(queryWrapper);
        if (inventory != null) {
            BigDecimal newLockedQuantity = inventory.getLockedQuantity().add(quantity);
            BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().subtract(quantity);
            inventory.setLockedQuantity(newLockedQuantity);
            inventory.setAvailableQuantity(newAvailableQuantity);
            inventoryMapper.updateById(inventory);
        }
    }

    @Override
    @Transactional
    public void unlockInventory(Long warehouseId, String materialCode, BigDecimal quantity) {
        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Inventory::getWarehouseId, warehouseId)
                  .eq(Inventory::getMaterialCode, materialCode);
        
        Inventory inventory = inventoryMapper.selectOne(queryWrapper);
        if (inventory != null) {
            BigDecimal newLockedQuantity = inventory.getLockedQuantity().subtract(quantity);
            BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().add(quantity);
            inventory.setLockedQuantity(newLockedQuantity);
            inventory.setAvailableQuantity(newAvailableQuantity);
            inventoryMapper.updateById(inventory);
        }
    }

    @Override
    public Page<Inventory> getInventoriesByPage(int page, int size, Long warehouseId, String materialCode) {
        Page<Inventory> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
        
        if (warehouseId != null) {
            queryWrapper.eq(Inventory::getWarehouseId, warehouseId);
        }
        if (materialCode != null) {
            queryWrapper.eq(Inventory::getMaterialCode, materialCode);
        }
        
        queryWrapper.orderByDesc(Inventory::getCreatedAt);
        IPage<Inventory> result = inventoryMapper.selectPage(pageParam, queryWrapper);
        
        return new org.springframework.data.domain.PageImpl<>(result.getRecords(), 
                org.springframework.data.domain.PageRequest.of(page - 1, size), 
                result.getTotal());
    }

    @Override
    public Inventory getInventoryByMaterial(Long warehouseId, String materialCode) {
        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Inventory::getWarehouseId, warehouseId)
                  .eq(Inventory::getMaterialCode, materialCode);
        return inventoryMapper.selectOne(queryWrapper);
    }

    @Override
    public Map<String, Object> getInventoryStatistics(Long warehouseId) {
        Map<String, Object> statistics = new HashMap<>();
        
        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
        if (warehouseId != null) {
            queryWrapper.eq(Inventory::getWarehouseId, warehouseId);
        }
        
        long totalCount = inventoryMapper.selectCount(queryWrapper);
        statistics.put("totalCount", totalCount);
        
        return statistics;
    }

    @Override
    public void checkInventoryAlert(Long warehouseId) {
        // TODO: 实现库存预警检查逻辑
    }
}
