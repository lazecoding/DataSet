package com.lazecoding.dataset.controller;

import com.lazecoding.dataset.common.exceptions.NilParamException;
import com.lazecoding.dataset.common.exceptions.NilValueException;
import com.lazecoding.dataset.common.exceptions.UnCreatedFileException;
import com.lazecoding.dataset.common.mvc.ResultBean;
import com.lazecoding.dataset.common.util.LocalDataUtil;
import com.lazecoding.dataset.model.Rule;
import com.lazecoding.dataset.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 规则
 *
 * @author lazecoding
 */
@Controller
@RequestMapping("datasource/rule")
public class RuleController {

    private static final Logger logger = LoggerFactory.getLogger(RuleController.class);


    @Autowired
    private RuleService ruleService;

    /**
     * 获取规则列表
     */
    @RequestMapping("list")
    @ResponseBody
    public ResultBean list() {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            List<Rule> rules = ruleService.list();
            resultBean.setValue(rules);
            isSuccess = true;
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("获取规则列表失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("获取规则列表失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 新增规则列表
     */
    @RequestMapping("add")
    @ResponseBody
    public ResultBean add(String regex, String desc) {
        ResultBean resultBean = ResultBean.getInstance();
        if (!StringUtils.hasText(regex)) {
            throw new NilParamException("regex is nil.");
        }

        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = ruleService.add(regex, desc);
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("新增规则失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("新增规则失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 修改规则
     */
    @RequestMapping("modify")
    @ResponseBody
    public ResultBean modify(String id, String regex, String desc) {
        ResultBean resultBean = ResultBean.getInstance();
        if (!StringUtils.hasText(id)) {
            throw new NilParamException("id is nil.");
        }
        if (!StringUtils.hasText(regex)) {
            throw new NilParamException("regex is nil.");
        }

        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = ruleService.modify(id, regex, desc);
        } catch (UnCreatedFileException | NilValueException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("修改规则失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("修改规则失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


    /**
     * 删除规则
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultBean delete(String id) {
        ResultBean resultBean = ResultBean.getInstance();
        if (!StringUtils.hasText(id)) {
            throw new NilParamException("id is nil.");
        }

        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = ruleService.delete(id);
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("删除规则失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("删除规则失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 清空规则
     */
    @RequestMapping("drop")
    @ResponseBody
    public ResultBean drop() {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = ruleService.drop();
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("清空规则列表", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


}
