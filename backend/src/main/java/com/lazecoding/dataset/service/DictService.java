package com.lazecoding.dataset.service;

import com.lazecoding.dataset.common.exceptions.NilValueException;
import com.lazecoding.dataset.common.util.JsonUtil;
import com.lazecoding.dataset.common.util.LocalDataUtil;
import com.lazecoding.dataset.model.DictItem;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 词典
 */
@Service
public class DictService {

    /**
     * 获取词典列表
     */
    public List<String> listDict(String projectId) {
        return LocalDataUtil.findDicts(projectId);
    }

    /**
     * 创建词典
     */
    public boolean createDict(String projectId, String dictId) {
        return LocalDataUtil.createDict(projectId, dictId);
    }

    /**
     * 删除词典
     */
    public boolean deleteDict(String projectId, String dictId) {
        return LocalDataUtil.deleteDict(projectId, dictId);
    }

    /**
     * 清空词典
     */
    public boolean dropDict(String projectId) {
        return LocalDataUtil.dropDict(projectId);
    }

    /**
     * 获取词条列表
     */
    public List<DictItem> listItem(String projectId, String dictId) {
        List<DictItem> items = new ArrayList<>();
        Map<String, DictItem> dictItemMap = LocalDataUtil.findDictItems(projectId, dictId);
        if (!CollectionUtils.isEmpty(dictItemMap)) {
            items = dictItemMap.values().stream().collect(Collectors.toList());
        }
        return items;
    }


    /**
     * 增加词条
     */
    public boolean addItem(String projectId, String dictId, String value, String desc) {
        // 获取词条列表
        Map<String, DictItem> dictItemMap = LocalDataUtil.findDictItems(projectId, dictId);
        DictItem dictItem = new DictItem(value, desc);
        dictItemMap.put(dictItem.getId(), dictItem);
        // 格式化
        String dictItemJson = JsonUtil.GSON.toJson(dictItemMap);
        // 新增
        if (StringUtils.hasText(dictItemJson)) {
            return LocalDataUtil.writeDict(projectId, dictId, dictItemJson);
        }
        return false;
    }

    /**
     * 修改词条
     */
    public boolean modifyItem(String projectId, String dictId, String id, String value, String desc) {
        // 获取词条列表
        Map<String, DictItem> dictItemMap = LocalDataUtil.findDictItems(projectId, dictId);
        DictItem dictItem = null;
        if (CollectionUtils.isEmpty(dictItemMap) || ObjectUtils.isEmpty(dictItem = dictItemMap.get(id))) {
            throw new NilValueException("未匹配到目标值");
        }
        dictItem.setValue(value);
        dictItem.setDesc(desc);
        // 格式化
        String dictItemJson = JsonUtil.GSON.toJson(dictItemMap);
        // 新增
        if (StringUtils.hasText(dictItemJson)) {
            return LocalDataUtil.writeDict(projectId, dictId, dictItemJson);
        }
        return false;
    }

    /**
     * 删除词条
     */
    public boolean deleteItem(String projectId, String dictId, String id) {
        // 获取词条列表
        Map<String, DictItem> dictItemMap = LocalDataUtil.findDictItems(projectId, dictId);
        if (!CollectionUtils.isEmpty(dictItemMap)) {
            dictItemMap.remove(id);
            String dictItemJson = JsonUtil.GSON.toJson(dictItemMap);
            return LocalDataUtil.writeDict(projectId, dictId, dictItemJson);
        }
        // 格式化
        return true;
    }

    /**
     * 清空词条
     */
    public boolean dropItem(String projectId, String dictId) {
        return LocalDataUtil.dropDictItem(projectId, dictId);
    }

}
