package com.qoobot.openscm.purchase.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.common.result.PageResult;
import com.qoobot.openscm.purchase.dto.PurchasePlanDTO;
import com.qoobot.openscm.purchase.entity.PurchasePlan;
import com.qoobot.openscm.purchase.vo.PurchasePlanVO;

import java.util.Map;

/**
 * 采购计划服务接口
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
public interface PurchasePlanService extends IService<PurchasePlan> {

    /**
     * 分页查询采购计划
     *
     * @param current 当前页
     * @param size 每页大小
     * @param params 查询参数
     * @return 分页结果
     */
    PageResult<PurchasePlanVO> queryPage(Integer current, Integer size, Map<String, Object> params);

    /**
     * 创建采购计划
     *
     * @param dto 采购计划DTO
     * @return 采购计划ID
     */
    Long createPlan(PurchasePlanDTO dto);

    /**
     * 更新采购计划
     *
     * @param id 计划ID
     * @param dto 采购计划DTO
     * @return 是否成功
     */
    Boolean updatePlan(Long id, PurchasePlanDTO dto);

    /**
     * 删除采购计划
     *
     * @param id 计划ID
     * @return 是否成功
     */
    Boolean deletePlan(Long id);

    /**
     * 提交审批
     *
     * @param id 计划ID
     * @return 是否成功
     */
    Boolean submitForApproval(Long id);

    /**
     * 审批采购计划
     *
     * @param id 计划ID
     * @param approved 是否同意
     * @param remark 审批意见
     * @return 是否成功
     */
    Boolean approvePlan(Long id, Boolean approved, String remark);

    /**
     * 执行采购计划
     *
     * @param id 计划ID
     * @return 是否成功
     */
    Boolean executePlan(Long id);

    /**
     * 更新实际执行金额
     *
     * @param id 计划ID
     * @param amount 实际执行金额
     */
    void updateActualAmount(Long id, java.math.BigDecimal amount);
}
