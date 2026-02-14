package com.qoobot.openscm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.system.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色 Mapper
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectByUserId(@Param("userId") Long userId);

    void deleteRoleMenus(@Param("roleId") Long roleId);

    void insertRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);
}
