package com.qoobot.openscm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qoobot.openscm.system.entity.SysDictData;
import com.qoobot.openscm.system.entity.SysDictType;

import java.util.List;
import java.util.Map;

/**
 * 数据字典服务接口
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
public interface SysDictService extends IService<SysDictType> {

    /**
     * 根据字典类型获取字典数据
     *
     * @param dictType  字典类型
     * @return          字典数据列表
     */
    List<SysDictData> getDictDataByType(String dictType);

    /**
     * 获取所有字典类型及其数据
     *
     * @return          字典类型与数据映射
     */
    Map<String, List<SysDictData>> getAllDictData();

    /**
     * 根据字典类型和值获取标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return          字典标签
     */
    String getDictLabel(String dictType, String dictValue);
}
