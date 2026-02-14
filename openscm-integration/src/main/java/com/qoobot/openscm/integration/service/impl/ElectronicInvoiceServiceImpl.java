package com.qoobot.openscm.integration.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.common.exception.BusinessException;
import com.qoobot.openscm.common.util.SecurityUtils;
import com.qoobot.openscm.integration.entity.ElectronicInvoice;
import com.qoobot.openscm.integration.mapper.ElectronicInvoiceMapper;
import com.qoobot.openscm.integration.service.ElectronicInvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 电子发票服务实现
 */
@Slf4j
@Service
public class ElectronicInvoiceServiceImpl extends ServiceImpl<ElectronicInvoiceMapper, ElectronicInvoice> implements ElectronicInvoiceService {

    @Override
    public Page<ElectronicInvoice> selectPage(Page<ElectronicInvoice> page, String invoiceCode, String invoiceNo,
                                               String invoiceType, String invoiceStatus) {
        LambdaQueryWrapper<ElectronicInvoice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ElectronicInvoice::getTenantId, SecurityUtils.getTenantId());
        if (invoiceCode != null && !invoiceCode.isEmpty()) {
            wrapper.eq(ElectronicInvoice::getInvoiceCode, invoiceCode);
        }
        if (invoiceNo != null && !invoiceNo.isEmpty()) {
            wrapper.eq(ElectronicInvoice::getInvoiceNo, invoiceNo);
        }
        if (invoiceType != null && !invoiceType.isEmpty()) {
            wrapper.eq(ElectronicInvoice::getInvoiceType, invoiceType);
        }
        if (invoiceStatus != null && !invoiceStatus.isEmpty()) {
            wrapper.eq(ElectronicInvoice::getInvoiceStatus, invoiceStatus);
        }
        wrapper.orderByDesc(ElectronicInvoice::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean issueInvoice(Long id) {
        ElectronicInvoice invoice = this.getById(id);
        if (invoice == null) {
            throw new BusinessException("电子发票不存在");
        }

        if ("ISSUED".equals(invoice.getInvoiceStatus())) {
            throw new BusinessException("电子发票已开具");
        }

        try {
            // TODO: 实现实际的开票逻辑
            log.info("开具电子发票: {}, 金额: {}", invoice.getInvoiceNo(), invoice.getTotalAmount());

            invoice.setInvoiceStatus("ISSUED");
            invoice.setIssueStatus("SUCCESS");
            invoice.setIssueTime(LocalDateTime.now());
            this.updateById(invoice);

            return true;
        } catch (Exception e) {
            log.error("开具电子发票失败", e);
            invoice.setIssueStatus("FAILED");
            this.updateById(invoice);
            throw new BusinessException("开具电子发票失败: " + e.getMessage());
        }
    }

    @Override
    public String queryInvoiceStatus(Long id) {
        ElectronicInvoice invoice = this.getById(id);
        if (invoice == null) {
            throw new BusinessException("电子发票不存在");
        }

        try {
            // TODO: 实现实际的查询逻辑
            return invoice.getInvoiceStatus();
        } catch (Exception e) {
            log.error("查询电子发票状态失败", e);
            throw new BusinessException("查询电子发票状态失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean reissueInvoice(Long id) {
        ElectronicInvoice invoice = this.getById(id);
        if (invoice == null) {
            throw new BusinessException("电子发票不存在");
        }

        if ("ISSUED".equals(invoice.getInvoiceStatus())) {
            throw new BusinessException("电子发票已开具，无法重新开具");
        }

        try {
            // TODO: 实现实际的重新开票逻辑
            log.info("重新开具电子发票: {}", invoice.getInvoiceNo());

            invoice.setInvoiceStatus("ISSUED");
            invoice.setIssueStatus("SUCCESS");
            invoice.setIssueTime(LocalDateTime.now());
            this.updateById(invoice);

            return true;
        } catch (Exception e) {
            log.error("重新开具电子发票失败", e);
            invoice.setIssueStatus("FAILED");
            this.updateById(invoice);
            throw new BusinessException("重新开具电子发票失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelInvoice(Long id) {
        ElectronicInvoice invoice = this.getById(id);
        if (invoice == null) {
            throw new BusinessException("电子发票不存在");
        }

        if (!"ISSUED".equals(invoice.getInvoiceStatus())) {
            throw new BusinessException("电子发票状态不正确，无法作废");
        }

        try {
            // TODO: 实现实际的作废逻辑
            log.info("作废电子发票: {}", invoice.getInvoiceNo());

            invoice.setInvoiceStatus("CANCELLED");
            this.updateById(invoice);

            return true;
        } catch (Exception e) {
            log.error("作废电子发票失败", e);
            throw new BusinessException("作废电子发票失败: " + e.getMessage());
        }
    }
}
