package com.qoobot.openscm.system.controller;

import com.qoobot.openscm.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统管理控制器
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Tag(name = "系统管理", description = "系统管理相关接口")
@RestController
@RequestMapping("/system")
public class SystemController {

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result<Map<String, Object>> health() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", "UP");
        data.put("timestamp", System.currentTimeMillis());
        return Result.success("系统运行正常", data);
    }

    @Operation(summary = "系统信息")
    @GetMapping("/info")
    public Result<Map<String, Object>> info() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "OpenSCM");
        data.put("version", "1.0.0");
        data.put("description", "Open Supply Chain Management");
        return Result.success(data);
    }
}
