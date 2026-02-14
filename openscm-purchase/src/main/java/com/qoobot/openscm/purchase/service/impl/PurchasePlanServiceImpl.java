package com.qoobot.openscm.purchase.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.common.exception.BusinessException;
import com.qoobot.openscm.common.result.PageResult;
import com.qoobot.openscm.common.util.SecurityUtils;
import com.qoobot.openscm.purchase.dto.PurchasePlanDTO;
import com.qoobot.openscm.purchase.dto.PurchasePlanItemDTO;
import com.qoobot.openscm.purchase.entity.PurchasePlan;
import com.qoobot.openscm.purchase.mapper.PurchasePlanMapper;
import com.qoobot.openscm.purchase.service.PurchasePlanService;
import com.qoobot.openscm.purchase.vo.PurchasePlanVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购计划服务实现
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Service
@RequiredArgsConstructor
public class PurchasePlanServiceImpl extends ServiceImpl<PurchasePlanMapper, PurchasePlan> implements PurchasePlanService {

    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    @Override
    public PageResult<PurchasePlanVO> queryPage(Integer current, Integer size, Map<String, Object> params) {
        Page<PurchasePlan> page = new Page<>(current, size);
        LambdaQueryWrapper<PurchasePlan> wrapper = new LambdaQueryWrapper<>();

        if (params != null) {
            String planName = (String) params.get("planName");
            if (StrUtil.isNotBlank(planName)) {
                wrapper.like(PurchasePlan::getPlanName, planName);
            }

            Integer status = (Integer) params.get("status");
            if (status != null) {
                wrapper.eq(PurchasePlan::getStatus, status);
            }

            Integer planYear = (Integer) params.get("planYear");
            if (planYear != null) {
                wrapper.eq(PurchasePlan::getPlanYear, planYear);
            }

            Integer planMonth = (Integer) params.get("planMonth");
            if (planMonth != null) {
                wrapper.eq(PurchasePlan::getPlanMonth, planMonth);
            }
        }

        wrapper.orderByDesc(PurchasePlan::getCreateTime);
        IPage<PurchasePlan> iPage = page(page, wrapper);

        return PageResult.of(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(),
                iPage.getRecords().stream()
                        .map(this::convertToVO)
                        .toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPlan(PurchasePlanDTO dto) {
        PurchasePlan plan = new PurchasePlan();
        BeanUtil.copyProperties(dto, plan);

        // 生成计划编号
        String planNo = "PP" + LocalDate.now().format(MONTH_FORMATTER) + IdUtil.getSnowflakeNextIdStr().substring(10);
        plan.setPlanNo(planNo);

        // 计算计划总金额
        BigDecimal totalAmount = calculateTotalAmount(dto.getItems());
        plan.setTotalAmount(totalAmount);
        plan.setActualAmount(BigDecimal.ZERO);

        // 设置状态为草稿
        plan.setStatus(0);

        // 设置申请人
        plan.setApplicantId(SecurityUtils.getUserId());
        plan.setApplicantName(SecurityUtils.getUsername());

        save(plan);
        return plan.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePlan(Long id, PurchasePlanDTO dto) {
        PurchasePlan plan = getById(id);
        if (plan == null) {
            throw new BusinessException("采购计划不存在");
        }

        // 只有草稿或已驳回状态可以修改
        if (plan.getStatus() != 0 && plan.getStatus() != 5) {
            throw new BusinessException("当前状态不允许修改");
        }

        BeanUtil.copyProperties(dto, plan);
        plan.setTotalAmount(calculateTotalAmount(dto.getItems()));

        return updateById(plan);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deletePlan(Long id) {
        PurchasePlan plan = getById(id);
        if (plan == null) {
            throw new BusinessException("采购计划不存在");
        }

        // 只有草稿状态可以删除
        if (plan.getStatus() != 0) {
            throw new BusinessException("当前状态不允许删除");
        }

        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean submitForApproval(Long id) {
        PurchasePlan plan = getById(id);
        if (plan == null) {
            throw new BusinessException("采购计划不存在");
        }

        // 只有草稿状态可以提交审批
        if (plan.getStatus() != 0) {
            throw new BusinessException("当前状态不允许提交审批");
        }

        plan.setStatus(1); // 待审批
        return updateById(plan);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean approvePlan(Long id, Boolean approved, String remark) {
        PurchasePlan plan = getById(id);
        if (plan == null) {
            throw new BusinessException("采购计划不存在");
        }

        // 只有待审批状态可以审批
        if (plan.getStatus() != 1) {
            throw new BusinessException("当前状态不允许审批");
        }

        if (approved) {
            plan.setStatus(2); // 已审批
            plan.setStatusName("已审批");
        } else {
            plan.setStatus(5); // 已驳回
            plan.setStatusName("已驳回");
        }

        plan.setApproverId(SecurityUtils.getUserId());
        plan.setApproverName(SecurityUtils.getUsername());
        plan.setApprovalDate(LocalDate.now());
        plan.setApprovalRemark(remark);

        return updateById(plan);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean executePlan(Long id) {
        PurchasePlan plan = getById(id);
        if (plan == null) {
            throw new BusinessException("采购计划不存在");
        }

        // 只有已审批状态可以执行
        if (plan.getStatus() != 2) {
            throw new BusinessException("当前状态不允许执行");
        }

        plan.setStatus(3); // 执行中
        return updateById(plan);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateActualAmount(Long id, BigDecimal amount) {
        PurchasePlan plan = getById(id);
        if (plan == null) {
            return;
        }

        BigDecimal currentAmount = plan.getActualAmount() != null ? plan.getActualAmount() : BigDecimal.ZERO;
        plan.setActualAmount(currentAmount.add(amount));

        // 如果实际金额达到计划金额，更新为已完成
        if (plan.getTotalAmount() != null && plan.getActualAmount().compareTo(plan.getTotalAmount()) >= 0) {
            plan.setStatus(4); // 已完成
        }

        updateById(plan);
    }

    private PurchasePlanVO convertToVO(PurchasePlan plan) {
        PurchasePlanVO vo = BeanUtil.copyProperties(plan, PurchasePlanVO.class);
        vo.setStatusName(getStatusName(plan.getStatus()));
        return vo;
    }

    private String getStatusName(Integer status) {
        return switch (status) {
            case 0 -> "草稿";
            case 1 -> "待审批";
            case 2 -> "已审批";
            case 3 -> "执行中";
            case 4 -> "已完成";
            case 5 -> "已驳回";
            default -> "未知";
        };
    }

    private BigDecimal calculateTotalAmount(List<PurchasePlanItemDTO> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return items.stream()
                .map(item -> {
                    BigDecimal quantity = item.getQuantity() != null ? item.getQuantity() : BigDecimal.ZERO;
                    BigDecimal price = item.getEstimatedPrice() != null ? item.getEstimatedPrice() : BigDecimal.ZERO;
                    return quantity.multiply(price);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
