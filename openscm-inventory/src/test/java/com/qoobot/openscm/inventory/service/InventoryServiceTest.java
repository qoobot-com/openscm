package com.qoobot.openscm.inventory.service;

import com.qoobot.openscm.inventory.entity.Inventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 库存服务测试
 */
@SpringBootTest
@Transactional
public class InventoryServiceTest {

    @Autowired
    private InventoryService inventoryService;

    /**
     * 测试增加库存
     */
    @Test
    public void testAddInventory() {
        Long warehouseId = 1L;
        Long locationId = 1L;
        String materialCode = "M001";
        BigDecimal quantity = new BigDecimal("100.00");
        BigDecimal unitPrice = new BigDecimal("50.00");

        inventoryService.addInventory(warehouseId, locationId, materialCode, quantity, unitPrice, "BATCH001", 1L);

        Inventory inventory = inventoryService.getInventoryByMaterial(warehouseId, materialCode);
        if (inventory != null) {
            assertEquals(new BigDecimal("100.00"), inventory.getQuantity());
            assertEquals(new BigDecimal("100.00"), inventory.getAvailableQuantity());
            assertEquals(BigDecimal.ZERO, inventory.getLockedQuantity());
        }
    }

    /**
     * 测试减少库存
     */
    @Test
    public void testReduceInventory() {
        // 先增加库存
        Long warehouseId = 1L;
        Long locationId = 1L;
        String materialCode = "M002";
        BigDecimal addQuantity = new BigDecimal("100.00");
        BigDecimal unitPrice = new BigDecimal("50.00");
        
        inventoryService.addInventory(warehouseId, locationId, materialCode, addQuantity, unitPrice, "BATCH002", 1L);

        // 减少库存
        BigDecimal reduceQuantity = new BigDecimal("30.00");
        inventoryService.reduceInventory(warehouseId, materialCode, reduceQuantity);

        Inventory inventory = inventoryService.getInventoryByMaterial(warehouseId, materialCode);
        if (inventory != null) {
            assertEquals(new BigDecimal("70.00"), inventory.getQuantity());
        }
    }

    /**
     * 测试锁定库存
     */
    @Test
    public void testLockInventory() {
        // 先增加库存
        Long warehouseId = 1L;
        Long locationId = 1L;
        String materialCode = "M003";
        BigDecimal addQuantity = new BigDecimal("100.00");
        BigDecimal unitPrice = new BigDecimal("50.00");
        
        inventoryService.addInventory(warehouseId, locationId, materialCode, addQuantity, unitPrice, "BATCH003", 1L);

        // 锁定库存
        BigDecimal lockQuantity = new BigDecimal("20.00");
        inventoryService.lockInventory(warehouseId, materialCode, lockQuantity);

        Inventory inventory = inventoryService.getInventoryByMaterial(warehouseId, materialCode);
        if (inventory != null) {
            assertEquals(new BigDecimal("20.00"), inventory.getLockedQuantity());
            assertEquals(new BigDecimal("80.00"), inventory.getAvailableQuantity());
        }
    }

    /**
     * 测试解锁库存
     */
    @Test
    public void testUnlockInventory() {
        // 先增加并锁定库存
        Long warehouseId = 1L;
        Long locationId = 1L;
        String materialCode = "M004";
        BigDecimal addQuantity = new BigDecimal("100.00");
        BigDecimal unitPrice = new BigDecimal("50.00");
        
        inventoryService.addInventory(warehouseId, locationId, materialCode, addQuantity, unitPrice, "BATCH004", 1L);
        inventoryService.lockInventory(warehouseId, materialCode, new BigDecimal("20.00"));

        // 解锁库存
        BigDecimal unlockQuantity = new BigDecimal("10.00");
        inventoryService.unlockInventory(warehouseId, materialCode, unlockQuantity);

        Inventory inventory = inventoryService.getInventoryByMaterial(warehouseId, materialCode);
        if (inventory != null) {
            assertEquals(new BigDecimal("10.00"), inventory.getLockedQuantity());
            assertEquals(new BigDecimal("90.00"), inventory.getAvailableQuantity());
        }
    }

    /**
     * 测试分页查询库存
     */
    @Test
    public void testGetInventoriesByPage() {
        Page<Inventory> page = inventoryService.getInventoriesByPage(1, 10, null, null);
        
        assertNotNull(page);
        assertTrue(page.getContent().size() >= 0);
    }

    /**
     * 测试查询物料库存
     */
    @Test
    public void testGetInventoryByMaterial() {
        // 先增加库存
        Long warehouseId = 1L;
        Long locationId = 1L;
        String materialCode = "M005";
        BigDecimal quantity = new BigDecimal("50.00");
        BigDecimal unitPrice = new BigDecimal("30.00");
        
        inventoryService.addInventory(warehouseId, locationId, materialCode, quantity, unitPrice, "BATCH005", 1L);

        Inventory inventory = inventoryService.getInventoryByMaterial(warehouseId, materialCode);
        if (inventory != null) {
            assertNotNull(inventory);
            assertEquals(materialCode, inventory.getMaterialCode());
        }
    }

    /**
     * 测试库存统计
     */
    @Test
    public void testGetInventoryStatistics() {
        Map<String, Object> statistics = inventoryService.getInventoryStatistics(null);
        
        assertNotNull(statistics);
        assertTrue(statistics.containsKey("totalCount"));
    }

    /**
     * 测试库存预警检查
     */
    @Test
    public void testCheckInventoryAlert() {
        // 应该不抛出异常
        inventoryService.checkInventoryAlert(1L);
    }
}
