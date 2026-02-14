package com.qoobot.openscm.inventory.service;

import com.qoobot.openscm.inventory.entity.InventoryCheck;
import org.springframework.data.domain.Page;

/**
 * 库存盘点服务接口
 */
public interface InventoryCheckService {

    /**
     * 创建盘点单
     */
    Long createInventoryCheck(InventoryCheck inventoryCheck);

    /**
     * 更新盘点单
     */
    void updateInventoryCheck(Long id, InventoryCheck inventoryCheck);

    /**
     * 删除盘点单
     */
    void deleteInventoryCheck(Long id);

    /**
     * 根据ID查询盘点单
     */
    InventoryCheck getInventoryCheckById(Long id);

    /**
     * 分页查询盘点单
     */
    Page<InventoryCheck> getInventoryChecksByPage(int page, int size, Long warehouseId, Integer status);

    /**
     * 开始盘点
     */
    void startCheck(Long id);

    /**
     * 完成盘点
     */
    void completeCheck(Long id);

    /**
     * 审核盘点单
     */
    void auditCheck(Long id, String auditor);
}
