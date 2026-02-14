package com.qoobot.openscm.integration.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.integration.entity.ElectronicInvoice;
import com.qoobot.openscm.integration.service.ElectronicInvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 电子发票控制器
 */
@Tag(name = "电子发票管理", description = "电子发票相关接口")
@RestController
@RequestMapping("/api/integration/invoice")
@RequiredArgsConstructor
@Validated
public class ElectronicInvoiceController {

    private final ElectronicInvoiceService electronicInvoiceService;

    @Operation(summary = "分页查询电子发票")
    @GetMapping("/page")
    public Result<Page<ElectronicInvoice>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String invoiceCode,
            @RequestParam(required = false) String invoiceNo,
            @RequestParam(required = false) String invoiceType,
            @RequestParam(required = false) String invoiceStatus) {
        Page<ElectronicInvoice> page = new Page<>(current, size);
        return Result.success(electronicInvoiceService.selectPage(page, invoiceCode, invoiceNo, invoiceType, invoiceStatus));
    }

    @Operation(summary = "根据ID查询电子发票")
    @GetMapping("/{id}")
    public Result<ElectronicInvoice> getById(@PathVariable Long id) {
        return Result.success(electronicInvoiceService.getById(id));
    }

    @Operation(summary = "创建电子发票")
    @PostMapping
    public Result<Boolean> create(@RequestBody ElectronicInvoice invoice) {
        return Result.success(electronicInvoiceService.save(invoice));
    }

    @Operation(summary = "更新电子发票")
    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @RequestBody ElectronicInvoice invoice) {
        invoice.setId(id);
        return Result.success(electronicInvoiceService.updateById(invoice));
    }

    @Operation(summary = "删除电子发票")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(electronicInvoiceService.removeById(id));
    }

    @Operation(summary = "开具电子发票")
    @PostMapping("/{id}/issue")
    public Result<Boolean> issueInvoice(@PathVariable Long id) {
        return Result.success(electronicInvoiceService.issueInvoice(id));
    }

    @Operation(summary = "查询电子发票状态")
    @GetMapping("/{id}/status")
    public Result<String> queryInvoiceStatus(@PathVariable Long id) {
        return Result.success(electronicInvoiceService.queryInvoiceStatus(id));
    }

    @Operation(summary = "重新开具电子发票")
    @PostMapping("/{id}/reissue")
    public Result<Boolean> reissueInvoice(@PathVariable Long id) {
        return Result.success(electronicInvoiceService.reissueInvoice(id));
    }

    @Operation(summary = "作废电子发票")
    @PostMapping("/{id}/cancel")
    public Result<Boolean> cancelInvoice(@PathVariable Long id) {
        return Result.success(electronicInvoiceService.cancelInvoice(id));
    }
}
