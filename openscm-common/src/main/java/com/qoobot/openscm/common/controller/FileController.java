package com.qoobot.openscm.common.controller;

import com.qoobot.openscm.common.result.Result;
import com.qoobot.openscm.common.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件管理控制器
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Tag(name = "文件管理")
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file,
                                @RequestParam(value = "dir", required = false) String dir) {
        String filePath = fileService.upload(file, dir);
        return Result.success(filePath);
    }

    @Operation(summary = "删除文件")
    @DeleteMapping("/delete")
    public Result<Void> delete(@RequestParam("filePath") String filePath) {
        boolean deleted = fileService.delete(filePath);
        return deleted ? Result.success() : Result.error("删除失败");
    }

    @Operation(summary = "下载文件")
    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam("filePath") String filePath) {
        byte[] fileData = fileService.download(filePath);

        // 获取文件名
        String filename = filePath.substring(filePath.lastIndexOf("/") + 1);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileData);
    }

    @Operation(summary = "检查文件是否存在")
    @GetMapping("/exists")
    public Result<Boolean> exists(@RequestParam("filePath") String filePath) {
        boolean exists = fileService.exists(filePath);
        return Result.success(exists);
    }
}
