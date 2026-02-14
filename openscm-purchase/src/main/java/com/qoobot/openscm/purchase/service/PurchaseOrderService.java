package com.qoobot.openscm.purchase.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qoobot.openscm.common.result.PageResult;
import com.qoobot.openscm.purchase.dto.PurchaseOrderDTO;
import com.qoobot.openscm.purchase.entity.PurchaseOrder;
import com.qoobot.openscm.purchase.vo.PurchaseOrderVO;

import java.util.Map;

/**
 * 采购订单服务接口
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
public interface PurchaseOrderService extends com.baomidou.mybatisplus.extension.service.IService<PurchaseOrder> {

    /**
     * 分页查询采购订单
     *
     * @param current 当前页
     * @param size 每页大小
     * @param params 查询参数
     * @return 分页结果
     */
    PageResult<PurchaseOrderVO> queryPage(Integer current, Integer size, Map<String, Object> params);

    /**
     * 创建采购订单
     *
     * @param dto 采购订单DTO
     * @return 订单ID
     */
    Long createOrder(PurchaseOrderDTO dto);

    /**
     * 更新采购订单
     *
     * @param id 订单ID
     * @param dto 采购订单DTO
     * @return 是否成功
     */
    Boolean updateOrder(Long id, PurchaseOrderDTO dto);

    /**
     * 删除采购订单
     *
     * @param id 订单ID
     * @return 是否成功
     */
    Boolean deleteOrder(Long id);

    /**
     * 确认订单
     *
     * @param id 订单ID
     * @return 是否成功
     */
    Boolean confirmOrder(Long id);

    /**
     * 取消订单
     *
     * @param id 订单ID
     * @param reason 取消原因
     * @return 是否成功
     */
    Boolean cancelOrder(Long id, String reason);

    /**
     * 发货
     *
     * @param id 订单ID
     * @return 是否成功
     */
    Boolean shipOrder(Long id);

    /**
     * 收货
     *
     * @param id 订单ID
     * @return 是否成功
     */
    Boolean receiveOrder(Long id);

    /**
     * 完成订单
     *
     * @param id 订单ID
     * @return 是否成功
     */
    Boolean completeOrder(Long id);

    /**
     * 更新已付款金额
     *
     * @param id 订单ID
     * @param amount 付款金额
     */
    void updatePaidAmount(Long id, java.math.BigDecimal amount);
}
