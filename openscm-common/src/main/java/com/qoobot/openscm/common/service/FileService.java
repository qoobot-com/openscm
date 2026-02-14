package com.qoobot.openscm.common.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务接口
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
public interface FileService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件访问路径
     */
    String upload(MultipartFile file);

    /**
     * 上传文件到指定目录
     *
     * @param file  文件
     * @param dir   目录
     * @return 文件访问路径
     */
    String upload(MultipartFile file, String dir);

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 是否成功
     */
    boolean delete(String filePath);

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @return 文件字节数组
     */
    byte[] download(String filePath);

    /**
     * 检查文件是否存在
     *
     * @param filePath 文件路径
     * @return 是否存在
     */
    boolean exists(String filePath);
}
