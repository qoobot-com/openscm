package com.qoobot.openscm.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据权限Mapper接口
 */
@Mapper
public interface DataPermissionMapper extends BaseMapper<com.qoobot.openscm.auth.entity.DataPermission> {

    /**
     * 根据权限编码查询
     */
    com.qoobot.openscm.auth.entity.DataPermission selectByPermissionCode(
        @Param("permissionCode") String permissionCode);
}
