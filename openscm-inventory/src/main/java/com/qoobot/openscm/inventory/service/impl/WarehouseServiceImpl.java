package com.qoobot.openscm.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.inventory.entity.Warehouse;
import com.qoobot.openscm.inventory.mapper.WarehouseMapper;
import com.qoobot.openscm.inventory.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 仓库信息服务实现
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    @Transactional
    public Long createWarehouse(Warehouse warehouse) {
        warehouseMapper.insert(warehouse);
        return warehouse.getId();
    }

    @Override
    @Transactional
    public void updateWarehouse(Long id, Warehouse warehouse) {
        warehouse.setId(id);
        warehouseMapper.updateById(warehouse);
    }

    @Override
    @Transactional
    public void deleteWarehouse(Long id) {
        warehouseMapper.deleteById(id);
    }

    @Override
    public Warehouse getWarehouseById(Long id) {
        return warehouseMapper.selectById(id);
    }

    @Override
    public Page<Warehouse> getWarehousesByPage(int page, int size, String warehouseCode, String warehouseName, Integer status) {
        Page<Warehouse> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Warehouse> queryWrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(warehouseCode)) {
            queryWrapper.like(Warehouse::getWarehouseCode, warehouseCode);
        }
        if (StringUtils.hasText(warehouseName)) {
            queryWrapper.like(Warehouse::getWarehouseName, warehouseName);
        }
        if (status != null) {
            queryWrapper.eq(Warehouse::getStatus, status);
        }
        
        queryWrapper.orderByDesc(Warehouse::getCreatedAt);
        IPage<Warehouse> result = warehouseMapper.selectPage(pageParam, queryWrapper);
        
        return new org.springframework.data.domain.PageImpl<>(result.getRecords(), 
                org.springframework.data.domain.PageRequest.of(page - 1, size), 
                result.getTotal());
    }

    @Override
    @Transactional
    public void updateWarehouseStatus(Long id, Integer status) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(id);
        warehouse.setStatus(status);
        warehouseMapper.updateById(warehouse);
    }
}
