package com.qoobot.openscm.supplier.service;

import com.qoobot.openscm.supplier.dto.SupplierDTO;
import com.qoobot.openscm.supplier.entity.Supplier;
import com.qoobot.openscm.supplier.vo.SupplierStatisticsVO;
import com.qoobot.openscm.supplier.vo.SupplierVO;

import java.util.List;

/**
 * 供应商服务接口
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
public interface SupplierService {

    /**
     * 分页查询供应商列表
     *
     * @param current       当前页
     * @param size          每页大小
     * @param supplierName  供应商名称（模糊）
     * @param supplierType  供应商类型
     * @param supplierStatus        状态
     * @return              供应商分页列表
     */
    List<SupplierVO> pageSuppliers(Long current, Long size, String supplierName, Integer supplierType, Integer supplierStatus);

    /**
     * 创建供应商
     *
     * @param supplierDTO   供应商DTO
     * @return              供应商ID
     */
    Long createSupplier(SupplierDTO supplierDTO);

    /**
     * 更新供应商
     *
     * @param id            供应商ID
     * @param supplierDTO   供应商DTO
     */
    void updateSupplier(Long id, SupplierDTO supplierDTO);

    /**
     * 删除供应商
     *
     * @param id            供应商ID
     */
    void deleteSupplier(Long id);

    /**
     * 审核供应商
     *
     * @param id            供应商ID
     * @param auditStatus   审核状态（1-通过 2-拒绝）
     * @param auditRemark   审核意见
     */
    void auditSupplier(Long id, Integer auditStatus, String auditRemark);

    /**
     * 获取供应商统计信息
     *
     * @return              供应商统计VO
     */
    SupplierStatisticsVO getSupplierStatistics();

    /**
     * 生成供应商编码
     *
     * @return              供应商编码
     */
    String generateSupplierCode();

    /**
     * 根据ID获取供应商
     *
     * @param id    供应商ID
     * @return      供应商实体
     */
    Supplier getById(Long id);
}
