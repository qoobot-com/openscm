package com.qoobot.openscm.supplier.service;

import com.qoobot.openscm.admin.OpenScmApplication;
import com.qoobot.openscm.supplier.dto.SupplierDTO;
import com.qoobot.openscm.supplier.entity.Supplier;
import com.qoobot.openscm.supplier.vo.SupplierStatisticsVO;
import com.qoobot.openscm.supplier.vo.SupplierVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 供应商服务测试
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@SpringBootTest(classes = OpenScmApplication.class)
@DisplayName("供应商服务测试")
public class SupplierServiceTest {

    @Autowired
    private com.qoobot.openscm.supplier.service.SupplierService supplierService;

    private SupplierDTO testSupplierDTO;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        testSupplierDTO = new SupplierDTO();
        testSupplierDTO.setSupplierName("测试供应商_" + System.currentTimeMillis());
        testSupplierDTO.setSupplierType(2);
        testSupplierDTO.setContactPerson("李四");
        testSupplierDTO.setContactPhone("13900139000");
        testSupplierDTO.setContactEmail("new@example.com");
        testSupplierDTO.setAddress("新地址");
        testSupplierDTO.setCreditLimit(new BigDecimal("200000"));
        testSupplierDTO.setCooperationStartDate(LocalDate.of(2024, 2, 1));
        testSupplierDTO.setLicenseNumber("1111111111");
        testSupplierDTO.setTaxNumber("2222222222");
        testSupplierDTO.setBankAccount("6222022222222222");
        testSupplierDTO.setBankName("新银行");
        testSupplierDTO.setRemark("备注信息");
    }

    @Test
    @DisplayName("测试创建供应商")
    void testCreateSupplier() {
        // When
        Long supplierId = supplierService.createSupplier(testSupplierDTO);

        // Then
        assertNotNull(supplierId);
        assertTrue(supplierId > 0);

        // 清理测试数据
        supplierService.deleteSupplier(supplierId);
    }

    @Test
    @DisplayName("测试根据ID获取供应商")
    void testGetById() {
        // Given - 先创建一个供应商
        Long supplierId = supplierService.createSupplier(testSupplierDTO);

        try {
            // When
            Supplier supplier = supplierService.getById(supplierId);

            // Then
            assertNotNull(supplier);
            assertEquals(supplierId, supplier.getId());
        } finally {
            // 清理测试数据
            supplierService.deleteSupplier(supplierId);
        }
    }

    @Test
    @DisplayName("测试更新供应商")
    void testUpdateSupplier() {
        // Given - 先创建一个供应商
        Long supplierId = supplierService.createSupplier(testSupplierDTO);
        testSupplierDTO.setSupplierName("更新后的供应商");

        try {
            // When
            supplierService.updateSupplier(supplierId, testSupplierDTO);

            // Then
            Supplier supplier = supplierService.getById(supplierId);
            assertEquals("更新后的供应商", supplier.getSupplierName());
        } finally {
            // 清理测试数据
            supplierService.deleteSupplier(supplierId);
        }
    }

    @Test
    @DisplayName("测试删除供应商")
    void testDeleteSupplier() {
        // Given - 先创建一个供应商
        Long supplierId = supplierService.createSupplier(testSupplierDTO);

        // When
        supplierService.deleteSupplier(supplierId);

        // Then
        Supplier supplier = supplierService.getById(supplierId);
        assertNull(supplier);
    }

    @Test
    @DisplayName("测试审核供应商-通过")
    void testAuditSupplierPass() {
        // Given - 先创建一个供应商
        Long supplierId = supplierService.createSupplier(testSupplierDTO);

        try {
            // When
            supplierService.auditSupplier(supplierId, 1, "审核通过");

            // Then
            Supplier supplier = supplierService.getById(supplierId);
            assertNotNull(supplier);
            assertEquals(1, supplier.getAuditStatus());
        } finally {
            // 清理测试数据
            supplierService.deleteSupplier(supplierId);
        }
    }

    @Test
    @DisplayName("测试审核供应商-拒绝")
    void testAuditSupplierReject() {
        // Given - 先创建一个供应商
        Long supplierId = supplierService.createSupplier(testSupplierDTO);

        try {
            // When
            supplierService.auditSupplier(supplierId, 2, "审核拒绝");

            // Then
            Supplier supplier = supplierService.getById(supplierId);
            assertNotNull(supplier);
            assertEquals(2, supplier.getAuditStatus());
        } finally {
            // 清理测试数据
            supplierService.deleteSupplier(supplierId);
        }
    }

    @Test
    @DisplayName("测试获取供应商统计信息")
    void testGetSupplierStatistics() {
        // When
        SupplierStatisticsVO statistics = supplierService.getSupplierStatistics();

        // Then
        assertNotNull(statistics);
        assertNotNull(statistics.getTotalCount());
        assertTrue(statistics.getTotalCount() >= 0);
    }

    @Test
    @DisplayName("测试生成供应商编码")
    void testGenerateSupplierCode() {
        // When
        String code = supplierService.generateSupplierCode();

        // Then
        assertNotNull(code);
        assertTrue(code.startsWith("SUP"));
        assertTrue(code.length() >= 15);
    }

    @Test
    @DisplayName("测试分页查询供应商列表")
    void testPageSuppliers() {
        // When
        java.util.List<SupplierVO> result = supplierService.pageSuppliers(1L, 10L, null, null, null);

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("测试根据名称查询供应商")
    void testPageSuppliersByName() {
        // When
        java.util.List<SupplierVO> result = supplierService.pageSuppliers(1L, 10L, "测试", null, null);

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("测试审核供应商-供应商不存在")
    void testAuditSupplierNotFound() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            supplierService.auditSupplier(999999L, 1, "审核通过");
        });
    }
}
