package com.qoobot.openscm.inventory.service;

import com.qoobot.openscm.inventory.entity.Inventory;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 库存信息服务接口
 */
public interface InventoryService {

    /**
     * 增加库存
     */
    void addInventory(Long warehouseId, Long locationId, String materialCode, BigDecimal quantity, 
                      BigDecimal unitPrice, String batchNumber, Long supplierId);

    /**
     * 减少库存
     */
    void reduceInventory(Long warehouseId, String materialCode, BigDecimal quantity);

    /**
     * 锁定库存
     */
    void lockInventory(Long warehouseId, String materialCode, BigDecimal quantity);

    /**
     * 解锁库存
     */
    void unlockInventory(Long warehouseId, String materialCode, BigDecimal quantity);

    /**
     * 分页查询库存
     */
    Page<Inventory> getInventoriesByPage(int page, int size, Long warehouseId, String materialCode);

    /**
     * 查询物料库存
     */
    Inventory getInventoryByMaterial(Long warehouseId, String materialCode);

    /**
     * 库存统计
     */
    Map<String, Object> getInventoryStatistics(Long warehouseId);

    /**
     * 库存预警检查
     */
    void checkInventoryAlert(Long warehouseId);
}
