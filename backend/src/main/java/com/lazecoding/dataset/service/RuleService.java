package com.lazecoding.dataset.service;


import com.lazecoding.dataset.common.exceptions.NilValueException;
import com.lazecoding.dataset.common.util.JsonUtil;
import com.lazecoding.dataset.common.util.LocalDataUtil;
import com.lazecoding.dataset.model.Rule;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 规则
 */
@Service
public class RuleService {

    /**
     * 获取规则
     */
    public List<Rule> list(String projectId) {
        List<Rule> rules = new ArrayList<>();
        Map<String, Rule> ruleMap = LocalDataUtil.findRules(projectId);
        if (!CollectionUtils.isEmpty(ruleMap)) {
            rules = ruleMap.values().stream().collect(Collectors.toList());
        }
        return rules;
    }

    /**
     * 增加规则
     */
    public boolean add(String projectId, String regex, String desc) {
        // 获取 rule
        Map<String, Rule> ruleMap = LocalDataUtil.findRules(projectId);
        Rule rule = new Rule(regex, desc);
        ruleMap.put(rule.getId(), rule);
        // 格式化
        String ruleJson = JsonUtil.GSON.toJson(ruleMap);
        // 新增
        if (StringUtils.hasText(ruleJson)) {
           return LocalDataUtil.writeRule(projectId, ruleJson);
        }
        return false;
    }

    /**
     * 修改规则
     */
    public boolean modify(String projectId,String id, String regex, String desc) {
        // 获取 rule
        Map<String, Rule> ruleMap = LocalDataUtil.findRules(projectId);

        Rule rule = null;
        if (CollectionUtils.isEmpty(ruleMap) || ObjectUtils.isEmpty(rule = ruleMap.get(id))) {
            throw new NilValueException("未匹配到目标值");
        }
        rule.setRegex(regex);
        rule.setDesc(desc);
        // 格式化
        String ruleJson = JsonUtil.GSON.toJson(ruleMap);
        // 新增
        if (StringUtils.hasText(ruleJson)) {
            return LocalDataUtil.writeRule(projectId, ruleJson);
        }
        return false;
    }

    /**
     * 删除规则
     */
    public boolean delete(String projectId, String id) {
        // 获取 rule
        Map<String, Rule> ruleMap = LocalDataUtil.findRules(projectId);
        if (!CollectionUtils.isEmpty(ruleMap)) {
            ruleMap.remove(id);
            String ruleJson = JsonUtil.GSON.toJson(ruleMap);
            return LocalDataUtil.writeRule(projectId, ruleJson);
        }
        // 格式化
        return true;
    }

    /**
     * 清空规则
     */
    public boolean drop(String projectId) {
        return LocalDataUtil.dropRule(projectId);
    }

}
