package com.qoobot.openscm.integration.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.integration.entity.ElectronicInvoice;

/**
 * 电子发票服务接口
 */
public interface ElectronicInvoiceService extends IService<ElectronicInvoice> {

    /**
     * 分页查询电子发票
     */
    Page<ElectronicInvoice> selectPage(Page<ElectronicInvoice> page, String invoiceCode, String invoiceNo,
                                         String invoiceType, String invoiceStatus);

    /**
     * 开具电子发票
     */
    Boolean issueInvoice(Long id);

    /**
     * 查询电子发票状态
     */
    String queryInvoiceStatus(Long id);

    /**
     * 重新开票
     */
    Boolean reissueInvoice(Long id);

    /**
     * 作废电子发票
     */
    Boolean cancelInvoice(Long id);
}
