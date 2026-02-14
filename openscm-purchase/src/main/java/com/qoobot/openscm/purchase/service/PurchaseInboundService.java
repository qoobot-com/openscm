package com.qoobot.openscm.purchase.service;

import com.qoobot.openscm.common.result.PageResult;
import com.qoobot.openscm.purchase.dto.PurchaseInboundDTO;
import com.qoobot.openscm.purchase.entity.PurchaseInbound;

import java.util.Map;

/**
 * 采购入库服务接口
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
public interface PurchaseInboundService extends com.baomidou.mybatisplus.extension.service.IService<PurchaseInbound> {

    /**
     * 分页查询入库单
     *
     * @param current 当前页
     * @param size 每页大小
     * @param params 查询参数
     * @return 分页结果
     */
    PageResult<PurchaseInbound> queryPage(Integer current, Integer size, Map<String, Object> params);

    /**
     * 创建入库单
     *
     * @param dto 入库单DTO
     * @return 入库单ID
     */
    Long createInbound(PurchaseInboundDTO dto);

    /**
     * 质检
     *
     * @param id 入库单ID
     * @param result 质检结果: 1-合格 2-不合格
     * @param remark 质检备注
     * @return 是否成功
     */
    Boolean qualityCheck(Long id, Integer result, String remark);

    /**
     * 入库
     *
     * @param id 入库单ID
     * @return 是否成功
     */
    Boolean inbound(Long id);

    /**
     * 完成入库
     *
     * @param id 入库单ID
     * @return 是否成功
     */
    Boolean completeInbound(Long id);
}
