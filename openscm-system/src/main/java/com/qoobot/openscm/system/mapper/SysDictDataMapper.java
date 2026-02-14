package com.qoobot.openscm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qoobot.openscm.system.entity.SysDictData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 数据字典数据Mapper
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType  字典类型
     * @return          字典数据列表
     */
    @Select("SELECT * FROM sys_dict_data WHERE dict_type = #{dictType} AND status = 1 ORDER BY dict_sort")
    List<SysDictData> selectByDictType(@Param("dictType") String dictType);
}
