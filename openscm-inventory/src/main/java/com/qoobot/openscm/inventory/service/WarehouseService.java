package com.qoobot.openscm.inventory.service;

import com.qoobot.openscm.inventory.entity.Warehouse;
import org.springframework.data.domain.Page;

/**
 * 仓库信息服务接口
 */
public interface WarehouseService {

    /**
     * 创建仓库
     */
    Long createWarehouse(Warehouse warehouse);

    /**
     * 更新仓库
     */
    void updateWarehouse(Long id, Warehouse warehouse);

    /**
     * 删除仓库
     */
    void deleteWarehouse(Long id);

    /**
     * 根据ID查询仓库
     */
    Warehouse getWarehouseById(Long id);

    /**
     * 分页查询仓库
     */
    Page<Warehouse> getWarehousesByPage(int page, int size, String warehouseCode, String warehouseName, Integer status);

    /**
     * 启用/禁用仓库
     */
    void updateWarehouseStatus(Long id, Integer status);
}
