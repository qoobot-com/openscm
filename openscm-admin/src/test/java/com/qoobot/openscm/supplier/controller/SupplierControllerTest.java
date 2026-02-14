package com.qoobot.openscm.supplier.controller;

import com.qoobot.openscm.admin.OpenScmApplication;
import com.qoobot.openscm.supplier.dto.SupplierDTO;
import com.qoobot.openscm.supplier.entity.Supplier;
import com.qoobot.openscm.supplier.service.SupplierService;
import com.qoobot.openscm.supplier.vo.SupplierStatisticsVO;
import com.qoobot.openscm.supplier.vo.SupplierVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 供应商控制器测试
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@SpringBootTest(classes = OpenScmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("供应商控制器测试")
public class SupplierControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SupplierService supplierService;

    private SupplierDTO testSupplierDTO;

    @BeforeEach
    void setUp() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

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
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SupplierDTO> request = new HttpEntity<>(testSupplierDTO, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/api/supplier", request, String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("测试根据ID获取供应商")
    void testGetSupplier() {
        // Given - 先创建一个供应商
        Long supplierId = supplierService.createSupplier(testSupplierDTO);

        try {
            // When
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<SupplierVO> response = restTemplate.exchange(
                    "/api/supplier/" + supplierId,
                    HttpMethod.GET,
                    request,
                    SupplierVO.class
            );

            // Then
            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertNotNull(response.getBody());
        } finally {
            // 清理测试数据
            supplierService.deleteSupplier(supplierId);
        }
    }

    @Test
    @DisplayName("测试分页查询供应商列表")
    void testPageSuppliers() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/supplier/page?current=1&size=10",
                HttpMethod.GET,
                request,
                String.class
        );

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("测试更新供应商")
    void testUpdateSupplier() {
        // Given - 先创建一个供应商
        Long supplierId = supplierService.createSupplier(testSupplierDTO);
        testSupplierDTO.setSupplierName("更新后的供应商");

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<SupplierDTO> request = new HttpEntity<>(testSupplierDTO, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    "/api/supplier/" + supplierId,
                    HttpMethod.PUT,
                    request,
                    String.class
            );

            assertTrue(response.getStatusCode().is2xxSuccessful());

            // 验证更新
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

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/supplier/" + supplierId,
                HttpMethod.DELETE,
                request,
                String.class
        );

        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("测试审核供应商")
    void testAuditSupplier() {
        // Given - 先创建一个供应商
        Long supplierId = supplierService.createSupplier(testSupplierDTO);

        try {
            HttpHeaders headers = new HttpHeaders();

            ResponseEntity<String> response = restTemplate.exchange(
                    "/api/supplier/" + supplierId + "/audit?auditStatus=1&auditRemark=审核通过",
                    HttpMethod.PUT,
                    new HttpEntity<>(headers),
                    String.class
            );

            assertTrue(response.getStatusCode().is2xxSuccessful());
        } finally {
            // 清理测试数据
            supplierService.deleteSupplier(supplierId);
        }
    }

    @Test
    @DisplayName("测试获取供应商统计信息")
    void testGetSupplierStatistics() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<SupplierStatisticsVO> response = restTemplate.exchange(
                "/api/supplier/statistics",
                HttpMethod.GET,
                request,
                SupplierStatisticsVO.class
        );

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }
}
