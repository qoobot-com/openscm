package com.qoobot.openscm.purchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qoobot.openscm.purchase.entity.PurchaseOrder;
import com.qoobot.openscm.purchase.mapper.PurchaseOrderMapper;
import com.qoobot.openscm.purchase.service.PurchaseStatisticsService;
import com.qoobot.openscm.purchase.vo.PurchaseStatisticsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 采购统计服务实现
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Service
@RequiredArgsConstructor
public class PurchaseStatisticsServiceImpl implements PurchaseStatisticsService {

    private final PurchaseOrderMapper orderMapper;

    @Override
    public PurchaseStatisticsVO getStatistics(LocalDate startDate, LocalDate endDate) {
        PurchaseStatisticsVO vo = new PurchaseStatisticsVO();
        vo.setPeriod(startDate + " ~ " + endDate);

        // 查询该时间段内的所有订单
        LambdaQueryWrapper<PurchaseOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(PurchaseOrder::getCreateTime, startDate.atStartOfDay())
                .le(PurchaseOrder::getCreateTime, endDate.atTime(23, 59, 59));
        List<PurchaseOrder> orders = orderMapper.selectList(wrapper);

        // 计算统计数据
        int orderCount = orders.size();
        BigDecimal totalAmount = orders.stream()
                .map(PurchaseOrder::getOrderAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal paidAmount = orders.stream()
                .map(PurchaseOrder::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal unpaidAmount = totalAmount.subtract(paidAmount);
        long supplierCount = orders.stream()
                .map(PurchaseOrder::getSupplierId)
                .distinct()
                .count();

        BigDecimal avgOrderAmount = orderCount > 0
                ? totalAmount.divide(BigDecimal.valueOf(orderCount), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        vo.setOrderCount(orderCount);
        vo.setTotalAmount(totalAmount);
        vo.setPaidAmount(paidAmount);
        vo.setUnpaidAmount(unpaidAmount);
        vo.setSupplierCount((int) supplierCount);
        vo.setAvgOrderAmount(avgOrderAmount);

        // 按供应商统计
        Map<String, BigDecimal> supplierStatistics = orders.stream()
                .collect(Collectors.groupingBy(
                        PurchaseOrder::getSupplierName,
                        Collectors.reducing(BigDecimal.ZERO,
                                order -> order.getOrderAmount() != null ? order.getOrderAmount() : BigDecimal.ZERO,
                                BigDecimal::add)
                ));
        vo.setSupplierStatistics(supplierStatistics);

        // 按月度趋势
        vo.setMonthlyTrend(getMonthlyTrend(orders, startDate, endDate));

        return vo;
    }

    @Override
    public Map<String, Object> getMonthlyStatistics(Integer year) {
        Map<String, Object> result = new HashMap<>();

        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();

            LambdaQueryWrapper<PurchaseOrder> wrapper = new LambdaQueryWrapper<>();
            wrapper.ge(PurchaseOrder::getCreateTime, startDate.atStartOfDay())
                    .le(PurchaseOrder::getCreateTime, endDate.atTime(23, 59, 59));
            List<PurchaseOrder> orders = orderMapper.selectList(wrapper);

            int orderCount = orders.size();
            BigDecimal totalAmount = orders.stream()
                    .map(PurchaseOrder::getOrderAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, Object> monthData = new HashMap<>();
            monthData.put("orderCount", orderCount);
            monthData.put("totalAmount", totalAmount);

            result.put(year + "-" + String.format("%02d", month), monthData);
        }

        return result;
    }

    @Override
    public Map<String, Object> getSupplierStatistics(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> result = new LinkedHashMap<>();

        LambdaQueryWrapper<PurchaseOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(PurchaseOrder::getCreateTime, startDate.atStartOfDay())
                .le(PurchaseOrder::getCreateTime, endDate.atTime(23, 59, 59));
        List<PurchaseOrder> orders = orderMapper.selectList(wrapper);

        // 按供应商分组统计
        Map<String, List<PurchaseOrder>> supplierGroups = orders.stream()
                .collect(Collectors.groupingBy(PurchaseOrder::getSupplierName));

        for (Map.Entry<String, List<PurchaseOrder>> entry : supplierGroups.entrySet()) {
            String supplierName = entry.getKey();
            List<PurchaseOrder> supplierOrders = entry.getValue();

            int orderCount = supplierOrders.size();
            BigDecimal totalAmount = supplierOrders.stream()
                    .map(PurchaseOrder::getOrderAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal paidAmount = supplierOrders.stream()
                    .map(PurchaseOrder::getPaidAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, Object> supplierData = new HashMap<>();
            supplierData.put("orderCount", orderCount);
            supplierData.put("totalAmount", totalAmount);
            supplierData.put("paidAmount", paidAmount);
            supplierData.put("unpaidAmount", totalAmount.subtract(paidAmount));

            result.put(supplierName, supplierData);
        }

        return result;
    }

    @Override
    public Map<String, Object> getMaterialStatistics(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> result = new LinkedHashMap<>();

        // TODO: 需要从订单明细表中统计物料数据
        // 这里暂时返回空数据
        return result;
    }

    @Override
    public Map<String, Object> getCostAnalysis(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> result = new HashMap<>();

        LambdaQueryWrapper<PurchaseOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(PurchaseOrder::getCreateTime, startDate.atStartOfDay())
                .le(PurchaseOrder::getCreateTime, endDate.atTime(23, 59, 59));
        List<PurchaseOrder> orders = orderMapper.selectList(wrapper);

        // 成本分析数据
        BigDecimal totalAmount = orders.stream()
                .map(PurchaseOrder::getOrderAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal paidAmount = orders.stream()
                .map(PurchaseOrder::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal unpaidAmount = totalAmount.subtract(paidAmount);

        result.put("totalAmount", totalAmount);
        result.put("paidAmount", paidAmount);
        result.put("unpaidAmount", unpaidAmount);
        result.put("paymentRate", paidAmount.divide(
                totalAmount.compareTo(BigDecimal.ZERO) > 0 ? totalAmount : BigDecimal.ONE,
                4, RoundingMode.HALF_UP
        ));

        // 按付款方式分析
        Map<Integer, List<PurchaseOrder>> paymentGroups = orders.stream()
                .collect(Collectors.groupingBy(PurchaseOrder::getPaymentMethod));

        Map<String, Object> paymentAnalysis = new HashMap<>();
        for (Map.Entry<Integer, List<PurchaseOrder>> entry : paymentGroups.entrySet()) {
            Integer paymentMethod = entry.getKey();
            List<PurchaseOrder> paymentOrders = entry.getValue();

            BigDecimal paymentTotalAmount = paymentOrders.stream()
                    .map(PurchaseOrder::getOrderAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            String paymentMethodName = getPaymentMethodName(paymentMethod);
            paymentAnalysis.put(paymentMethodName, Map.of(
                    "count", paymentOrders.size(),
                    "amount", paymentTotalAmount
            ));
        }

        result.put("paymentAnalysis", paymentAnalysis);

        return result;
    }

    private Map<String, BigDecimal> getMonthlyTrend(List<PurchaseOrder> orders, LocalDate startDate, LocalDate endDate) {
        Map<String, BigDecimal> monthlyTrend = new LinkedHashMap<>();

        // 计算涉及的月份
        YearMonth start = YearMonth.from(startDate);
        YearMonth end = YearMonth.from(endDate);

        YearMonth current = start;
        while (!current.isAfter(end)) {
            String monthKey = current.toString();

            // 筛选当月订单
            LocalDate monthStart = current.atDay(1);
            LocalDate monthEnd = current.atEndOfMonth();

            BigDecimal monthAmount = orders.stream()
                    .filter(order -> {
                        LocalDate orderDate = order.getCreateTime().toLocalDate();
                        return !orderDate.isBefore(monthStart) && !orderDate.isAfter(monthEnd);
                    })
                    .map(order -> order.getOrderAmount() != null ? order.getOrderAmount() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            monthlyTrend.put(monthKey, monthAmount);

            current = current.plusMonths(1);
        }

        return monthlyTrend;
    }

    private String getPaymentMethodName(Integer method) {
        return switch (method) {
            case 1 -> "货到付款";
            case 2 -> "预付款";
            case 3 -> "月结";
            case 4 -> "分期付款";
            default -> "未知";
        };
    }
}
