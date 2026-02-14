package com.qoobot.openscm.purchase.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.common.annotation.OperationLog;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.common.result.PageResult;
import com.qoobot.openscm.common.util.SecurityUtils;
import com.qoobot.openscm.purchase.entity.SupplierInquiry;
import com.qoobot.openscm.purchase.entity.SupplierQuote;
import com.qoobot.openscm.purchase.mapper.SupplierInquiryMapper;
import com.qoobot.openscm.purchase.mapper.SupplierQuoteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 供应商询价控制器
 *
 * @author OpenSCM Team
 * @since 2026-02-15
 */
@Tag(name = "供应商询价管理", description = "供应商询价相关接口")
@RestController
@RequestMapping("/api/purchase/inquiry")
@RequiredArgsConstructor
public class SupplierInquiryController {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final SupplierInquiryMapper inquiryMapper;
    private final SupplierQuoteMapper quoteMapper;

    @Operation(summary = "分页查询询价单")
    @GetMapping("/page")
    public Result<PageResult<SupplierInquiry>> queryPage(
            @Parameter(description = "当前页") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "查询参数") @RequestParam(required = false) Map<String, Object> params) {
        Page<SupplierInquiry> page = new Page<>(current, size);
        LambdaQueryWrapper<SupplierInquiry> wrapper = new LambdaQueryWrapper<>();

        if (params != null) {
            String subject = (String) params.get("subject");
            if (subject != null && !subject.isEmpty()) {
                wrapper.like(SupplierInquiry::getSubject, subject);
            }

            Integer status = (Integer) params.get("status");
            if (status != null) {
                wrapper.eq(SupplierInquiry::getStatus, status);
            }
        }

        wrapper.orderByDesc(SupplierInquiry::getCreateTime);
        IPage<SupplierInquiry> iPage = inquiryMapper.selectPage(page, wrapper);

        return Result.success(PageResult.of(iPage.getCurrent(), iPage.getSize(),
                iPage.getTotal(), iPage.getRecords()));
    }

    @Operation(summary = "根据ID查询询价单")
    @GetMapping("/{id}")
    public Result<SupplierInquiry> getById(@Parameter(description = "询价单ID") @PathVariable Long id) {
        return Result.success(inquiryMapper.selectById(id));
    }

    @Operation(summary = "创建询价单")
    @PostMapping
    @OperationLog("创建供应商询价单")
    public Result<Long> create(@RequestBody Map<String, Object> params) {
        SupplierInquiry inquiry = new SupplierInquiry();

        // 生成询价单编号
        String inquiryNo = "SQ" + LocalDate.now().format(DATE_FORMATTER) + System.currentTimeMillis() % 10000;
        inquiry.setInquiryNo(inquiryNo);

        // 设置基本信息
        inquiry.setSubject((String) params.get("subject"));
        inquiry.setStartDate(parseDate((String) params.get("startDate")));
        inquiry.setEndDate(parseDate((String) params.get("endDate")));
        inquiry.setInquirerId(SecurityUtils.getUserId());
        inquiry.setInquirerName(SecurityUtils.getUsername());
        inquiry.setContactPhone((String) params.get("contactPhone"));
        inquiry.setEmail((String) params.get("email"));
        inquiry.setDescription((String) params.get("description"));
        inquiry.setRemark((String) params.get("remark"));
        inquiry.setStatus(0); // 待询价

        inquiryMapper.insert(inquiry);
        return Result.success(inquiry.getId());
    }

    @Operation(summary = "更新询价单")
    @PutMapping("/{id}")
    @OperationLog("更新供应商询价单")
    public Result<Void> update(
            @Parameter(description = "询价单ID") @PathVariable Long id,
            @RequestBody Map<String, Object> params) {
        SupplierInquiry inquiry = inquiryMapper.selectById(id);
        if (inquiry == null) {
            return Result.fail("询价单不存在");
        }

        // 只有待询价状态可以修改
        if (inquiry.getStatus() != 0) {
            return Result.fail("当前状态不允许修改");
        }

        inquiry.setSubject((String) params.get("subject"));
        inquiry.setStartDate(parseDate((String) params.get("startDate")));
        inquiry.setEndDate(parseDate((String) params.get("endDate")));
        inquiry.setContactPhone((String) params.get("contactPhone"));
        inquiry.setEmail((String) params.get("email"));
        inquiry.setDescription((String) params.get("description"));
        inquiry.setRemark((String) params.get("remark"));

        inquiryMapper.updateById(inquiry);
        return Result.success();
    }

    @Operation(summary = "删除询价单")
    @DeleteMapping("/{id}")
    @OperationLog("删除供应商询价单")
    public Result<Void> delete(@Parameter(description = "询价单ID") @PathVariable Long id) {
        SupplierInquiry inquiry = inquiryMapper.selectById(id);
        if (inquiry == null) {
            return Result.fail("询价单不存在");
        }

        // 只有待询价状态可以删除
        if (inquiry.getStatus() != 0) {
            return Result.fail("当前状态不允许删除");
        }

        inquiryMapper.deleteById(id);
        return Result.success();
    }

    @Operation(summary = "开始询价")
    @PutMapping("/{id}/start")
    @OperationLog("开始供应商询价")
    public Result<Void> startInquiry(@Parameter(description = "询价单ID") @PathVariable Long id) {
        SupplierInquiry inquiry = inquiryMapper.selectById(id);
        if (inquiry == null) {
            return Result.fail("询价单不存在");
        }

        if (inquiry.getStatus() != 0) {
            return Result.fail("当前状态不允许开始询价");
        }

        inquiry.setStatus(1); // 询价中
        inquiryMapper.updateById(inquiry);

        // TODO: 发送询价通知给相关供应商

        return Result.success();
    }

    @Operation(summary = "完成询价")
    @PutMapping("/{id}/complete")
    @OperationLog("完成供应商询价")
    public Result<Void> completeInquiry(@Parameter(description = "询价单ID") @PathVariable Long id) {
        SupplierInquiry inquiry = inquiryMapper.selectById(id);
        if (inquiry == null) {
            return Result.fail("询价单不存在");
        }

        if (inquiry.getStatus() != 1) {
            return Result.fail("当前状态不允许完成");
        }

        inquiry.setStatus(2); // 已完成
        inquiryMapper.updateById(inquiry);

        return Result.success();
    }

    @Operation(summary = "查询询价单的报价列表")
    @GetMapping("/{id}/quotes")
    public Result<List<SupplierQuote>> getQuotes(@Parameter(description = "询价单ID") @PathVariable Long id) {
        LambdaQueryWrapper<SupplierQuote> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SupplierQuote::getInquiryId, id);
        wrapper.orderByDesc(SupplierQuote::getQuoteDate);

        List<SupplierQuote> quotes = quoteMapper.selectList(wrapper);
        return Result.success(quotes);
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr);
    }
}
