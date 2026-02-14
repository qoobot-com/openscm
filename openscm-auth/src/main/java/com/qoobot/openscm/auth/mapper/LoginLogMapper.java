package com.qoobot.openscm.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qoobot.openscm.auth.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 登录日志Mapper接口
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 分页查询登录日志
     */
    IPage<LoginLog> selectLogPage(Page<LoginLog> page,
                                    @Param("username") String username,
                                    @Param("loginType") Integer loginType,
                                    @Param("status") Integer status,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("endTime") LocalDateTime endTime);

    /**
     * 查询用户最近登录日志
     */
    LoginLog selectLatestByUserId(@Param("userId") Long userId);

    /**
     * 统计登录失败次数
     */
    Integer countFailedAttempts(@Param("username") String username,
                               @Param("startTime") LocalDateTime startTime);
}
