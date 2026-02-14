package com.qoobot.openscm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qoobot.openscm.system.entity.SysDictData;
import com.qoobot.openscm.system.entity.SysDictType;
import com.qoobot.openscm.system.mapper.SysDictDataMapper;
import com.qoobot.openscm.system.mapper.SysDictTypeMapper;
import com.qoobot.openscm.system.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据字典服务实现
 *
 * @author OpenSCM Team
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictService {

    private final SysDictDataMapper dictDataMapper;

    @Override
    @Cacheable(value = "dictData", key = "#dictType")
    public List<SysDictData> getDictDataByType(String dictType) {
        return dictDataMapper.selectByDictType(dictType);
    }

    @Override
    public Map<String, List<SysDictData>> getAllDictData() {
        List<SysDictType> dictTypes = list();
        
        Map<String, List<SysDictData>> result = new HashMap<>();
        
        for (SysDictType dictType : dictTypes) {
            List<SysDictData> dictDataList = dictDataMapper.selectByDictType(dictType.getDictType());
            result.put(dictType.getDictType(), dictDataList);
        }
        
        return result;
    }

    @Override
    public String getDictLabel(String dictType, String dictValue) {
        List<SysDictData> dictDataList = getDictDataByType(dictType);
        
        return dictDataList.stream()
                .filter(d -> dictValue.equals(d.getDictValue()))
                .map(SysDictData::getDictLabel)
                .findFirst()
                .orElse(dictValue);
    }
}
