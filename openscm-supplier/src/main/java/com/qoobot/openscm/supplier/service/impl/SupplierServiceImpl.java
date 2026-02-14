package com.qoobot.openscm.supplier.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.supplier.dto.SupplierDTO;
import com.qoobot.openscm.supplier.entity.Supplier;
import com.qoobot.openscm.supplier.mapper.SupplierMapper;
import com.qoobot.openscm.supplier.service.SupplierService;
import com.qoobot.openscm.supplier.vo.SupplierStatisticsVO;
import com.qoobot.openscm.supplier.vo.SupplierVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 供应商服务实现
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

    private final SupplierMapper supplierMapper;

    @Override
    public List<SupplierVO> pageSuppliers(Long current, Long size, String supplierName, Integer supplierType, Integer supplierStatus) {
        LambdaQueryWrapper<Supplier> wrapper = new LambdaQueryWrapper<>();

        if (supplierName != null && !supplierName.isEmpty()) {
            wrapper.like(Supplier::getSupplierName, supplierName);
        }
        if (supplierType != null) {
            wrapper.eq(Supplier::getSupplierType, supplierType);
        }
        if (supplierStatus != null) {
            wrapper.eq(Supplier::getSupplierStatus, supplierStatus);
        }

        wrapper.orderByDesc(Supplier::getCreateTime);
        wrapper.last("LIMIT " + size + " OFFSET " + ((current - 1) * size));

        List<Supplier> suppliers = list(wrapper);
        return suppliers.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(supplierDTO, supplier);
        supplier.setSupplierCode(generateSupplierCode());
        supplier.setAuditStatus(0); // 待审核
        supplier.setCreateTime(LocalDateTime.now());
        supplier.setUpdateTime(LocalDateTime.now());
        save(supplier);
        return supplier.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSupplier(Long id, SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(supplierDTO, supplier);
        supplier.setId(id);
        supplier.setUpdateTime(LocalDateTime.now());
        updateById(supplier);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSupplier(Long id) {
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditSupplier(Long id, Integer auditStatus, String auditRemark) {
        Supplier supplier = getById(id);
        if (supplier == null) {
            throw new IllegalArgumentException("供应商不存在");
        }
        supplier.setAuditStatus(auditStatus);
        supplier.setAuditRemark(auditRemark);
        supplier.setAuditTime(LocalDateTime.now());
        if (auditStatus == 1) {
            supplier.setSupplierStatus(1); // 审核通过后设为正常
        }
        updateById(supplier);
    }

    @Override
    public SupplierStatisticsVO getSupplierStatistics() {
        SupplierStatisticsVO statistics = new SupplierStatisticsVO();
        Long total = count();
        Long normalCount = lambdaQuery().eq(Supplier::getSupplierStatus, 1).count();
        Long auditingCount = lambdaQuery().eq(Supplier::getAuditStatus, 0).count();
        Long blacklistCount = lambdaQuery().eq(Supplier::getSupplierStatus, 3).count();
        statistics.setTotalCount(total != null ? total.intValue() : 0);
        statistics.setNormalCount(normalCount != null ? normalCount.intValue() : 0);
        statistics.setAuditingCount(auditingCount != null ? auditingCount.intValue() : 0);
        statistics.setBlacklistCount(blacklistCount != null ? blacklistCount.intValue() : 0);
        return statistics;
    }

    @Override
    public String generateSupplierCode() {
        String datePrefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = lambdaQuery()
                .likeRight(Supplier::getSupplierCode, "SUP" + datePrefix)
                .count();
        return String.format("SUP%s%06d", datePrefix, count + 1);
    }

    @Override
    public Supplier getById(Long id) {
        return super.getById(id);
    }

    private SupplierVO convertToVO(Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        SupplierVO vo = new SupplierVO();
        BeanUtils.copyProperties(supplier, vo);
        return vo;
    }
}
