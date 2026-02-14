package com.qoobot.openscm.common.result;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应结果
 *
 * @param <T> 数据类型
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页响应结果")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    @Schema(description = "当前页码", example = "1")
    private long pageNum;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数", example = "10")
    private long pageSize;

    /**
     * 总记录数
     */
    @Schema(description = "总记录数", example = "100")
    private long total;

    /**
     * 总页数
     */
    @Schema(description = "总页数", example = "10")
    private long pages;

    /**
     * 数据列表
     */
    @Schema(description = "数据列表")
    private List<T> records;

    /**
     * 创建分页结果
     */
    public static <T> PageResult<T> of(long pageNum, long pageSize, long total, List<T> records) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setTotal(total);
        pageResult.setPages((total + pageSize - 1) / pageSize);
        pageResult.setRecords(records);
        return pageResult;
    }

    /**
     * 从MyBatis-Plus的Page对象创建分页结果
     */
    public static <T> PageResult<T> of(Page<T> page) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNum(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());
        pageResult.setRecords(page.getRecords());
        return pageResult;
    }

    /**
     * 创建空分页结果
     */
    public static <T> PageResult<T> empty(long pageNum, long pageSize) {
        return of(pageNum, pageSize, 0, List.of());
    }
}
