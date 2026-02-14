package com.qoobot.openscm.system.controller;

import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.system.entity.SysDictData;
import com.qoobot.openscm.system.entity.SysDictType;
import com.qoobot.openscm.system.service.SysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据字典控制器
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Tag(name = "数据字典", description = "数据字典管理接口")
@RestController
@RequestMapping("/api/system/dict")
@RequiredArgsConstructor
public class DictController {

    private final SysDictService dictService;

    @Operation(summary = "获取所有字典类型")
    @GetMapping("/type/list")
    public Result<List<SysDictType>> getDictTypes() {
        return Result.success(dictService.list());
    }

    @Operation(summary = "根据类型获取字典数据")
    @GetMapping("/data/{dictType}")
    public Result<List<SysDictData>> getDictDataByType(@PathVariable String dictType) {
        return Result.success(dictService.getDictDataByType(dictType));
    }

    @Operation(summary = "获取所有字典数据")
    @GetMapping("/data/all")
    public Result<Map<String, List<SysDictData>>> getAllDictData() {
        return Result.success(dictService.getAllDictData());
    }

    @Operation(summary = "根据类型和值获取标签")
    @GetMapping("/label")
    public Result<String> getDictLabel(
            @RequestParam String dictType,
            @RequestParam String dictValue) {
        return Result.success(dictService.getDictLabel(dictType, dictValue));
    }

    @Operation(summary = "创建字典类型")
    @PostMapping("/type")
    public Result<Void> createDictType(@RequestBody SysDictType dictType) {
        dictService.save(dictType);
        return Result.success();
    }

    @Operation(summary = "创建字典数据")
    @PostMapping("/data")
    public Result<Void> createDictData(@RequestBody SysDictData dictData) {
        // TODO: 保存字典数据
        return Result.success();
    }
}
