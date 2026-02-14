package com.qoobot.openscm.common.service.impl;

import com.qoobot.openscm.common.service.FileService;
import com.qoobot.openscm.common.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 文件服务实现(本地存储)
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload.path:./uploads}")
    private String uploadPath;

    @Value("${file.upload.prefix:/files}")
    private String urlPrefix;

    /**
     * 允许的文件类型
     */
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            "jpg", "jpeg", "png", "gif", "bmp",
            "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx",
            "txt", "zip", "rar"
    );

    /**
     * 最大文件大小(10MB)
     */
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    @Override
    public String upload(MultipartFile file) {
        return upload(file, "");
    }

    @Override
    public String upload(MultipartFile file, String dir) {
        try {
            // 校验文件
            validateFile(file);

            // 生成文件路径
            String relativePath = generateFilePath(file, dir);
            Path absolutePath = Paths.get(uploadPath, relativePath);

            // 确保目录存在
            Files.createDirectories(absolutePath.getParent());

            // 保存文件
            file.transferTo(absolutePath.toFile());

            log.info("文件上传成功: {}", relativePath);

            // 返回访问路径
            return urlPrefix + "/" + relativePath.replace(File.separator, "/");

        } catch (IOException e) {
            log.error("文件上传失败: {}", e.getMessage());
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String filePath) {
        try {
            // 移除URL前缀
            String relativePath = filePath.replace(urlPrefix, "").replaceFirst("^/", "");

            Path absolutePath = Paths.get(uploadPath, relativePath);
            boolean deleted = Files.deleteIfExists(absolutePath);

            if (deleted) {
                log.info("文件删除成功: {}", relativePath);
            }

            return deleted;

        } catch (IOException e) {
            log.error("文件删除失败: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public byte[] download(String filePath) {
        try {
            // 移除URL前缀
            String relativePath = filePath.replace(urlPrefix, "").replaceFirst("^/", "");

            Path absolutePath = Paths.get(uploadPath, relativePath);

            if (!Files.exists(absolutePath)) {
                throw new RuntimeException("文件不存在");
            }

            log.info("文件下载: {}", relativePath);

            return Files.readAllBytes(absolutePath);

        } catch (IOException e) {
            log.error("文件下载失败: {}", e.getMessage());
            throw new RuntimeException("文件下载失败: " + e.getMessage());
        }
    }

    @Override
    public boolean exists(String filePath) {
        try {
            // 移除URL前缀
            String relativePath = filePath.replace(urlPrefix, "").replaceFirst("^/", "");

            Path absolutePath = Paths.get(uploadPath, relativePath);
            return Files.exists(absolutePath);

        } catch (Exception e) {
            log.error("检查文件存在失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 校验文件
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("文件大小超过限制(最大10MB)");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new RuntimeException("文件名不能为空");
        }

        String extension = getFileExtension(originalFilename);
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new RuntimeException("不支持的文件类型: " + extension);
        }
    }

    /**
     * 生成文件路径
     */
    private String generateFilePath(MultipartFile file, String dir) {
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);

        // 生成唯一文件名
        String filename = UUID.randomUUID().toString() + "." + extension;

        // 生成日期目录
        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        // 获取用户ID目录(如果用户已登录)
        String userDir = "guest";
        try {
            userDir = String.valueOf(SecurityUtils.getCurrentUserId());
        } catch (Exception e) {
            // 未登录用户使用guest
        }

        // 组装路径: dir/日期/用户ID/文件名
        if (dir != null && !dir.isEmpty()) {
            return dir + File.separator + dateDir + File.separator + userDir + File.separator + filename;
        } else {
            return dateDir + File.separator + userDir + File.separator + filename;
        }
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0 && lastDot < filename.length() - 1) {
            return filename.substring(lastDot + 1);
        }
        return "";
    }
}
