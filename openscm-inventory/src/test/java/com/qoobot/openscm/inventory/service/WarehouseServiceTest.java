package com.qoobot.openscm.inventory.service;

import com.qoobot.openscm.inventory.entity.Warehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 仓库服务测试
 */
@SpringBootTest
@Transactional
public class WarehouseServiceTest {

    @Autowired
    private WarehouseService warehouseService;

    /**
     * 测试创建仓库
     */
    @Test
    public void testCreateWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode("WH001");
        warehouse.setWarehouseName("原材料仓库");
        warehouse.setWarehouseType(1);
        warehouse.setAddress("北京市朝阳区");
        warehouse.setContactPerson("张三");
        warehouse.setContactPhone("13800138000");
        warehouse.setCapacity(new BigDecimal("10000.00"));
        warehouse.setStatus(1);
        warehouse.setRemark("测试仓库");

        Long id = warehouseService.createWarehouse(warehouse);

        assertNotNull(id);
        
        Warehouse created = warehouseService.getWarehouseById(id);
        assertNotNull(created);
        assertEquals("WH001", created.getWarehouseCode());
        assertEquals("原材料仓库", created.getWarehouseName());
        assertEquals(1, created.getStatus());
    }

    /**
     * 测试分页查询仓库
     */
    @Test
    public void testGetWarehousesByPage() {
        Page<Warehouse> page = warehouseService.getWarehousesByPage(1, 10, null, null, null);
        
        assertNotNull(page);
        assertTrue(page.getContent().size() >= 0);
    }

    /**
     * 测试更新仓库
     */
    @Test
    public void testUpdateWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode("WH002");
        warehouse.setWarehouseName("测试仓库");
        warehouse.setWarehouseType(1);
        warehouse.setStatus(1);
        Long id = warehouseService.createWarehouse(warehouse);

        Warehouse update = new Warehouse();
        update.setWarehouseName("更新后的仓库名称");
        warehouseService.updateWarehouse(id, update);

        Warehouse updated = warehouseService.getWarehouseById(id);
        assertEquals("更新后的仓库名称", updated.getWarehouseName());
    }

    /**
     * 测试删除仓库
     */
    @Test
    public void testDeleteWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode("WH003");
        warehouse.setWarehouseName("待删除仓库");
        warehouse.setWarehouseType(1);
        warehouse.setStatus(1);
        Long id = warehouseService.createWarehouse(warehouse);

        warehouseService.deleteWarehouse(id);

        Warehouse deleted = warehouseService.getWarehouseById(id);
        assertNull(deleted);
    }

    /**
     * 测试更新仓库状态
     */
    @Test
    public void testUpdateWarehouseStatus() {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode("WH004");
        warehouse.setWarehouseName("状态测试仓库");
        warehouse.setWarehouseType(1);
        warehouse.setStatus(1);
        Long id = warehouseService.createWarehouse(warehouse);

        warehouseService.updateWarehouseStatus(id, 0);

        Warehouse updated = warehouseService.getWarehouseById(id);
        assertEquals(0, updated.getStatus());
    }
}
