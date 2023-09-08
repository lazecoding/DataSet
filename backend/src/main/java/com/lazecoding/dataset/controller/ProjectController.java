package com.lazecoding.dataset.controller;

import com.lazecoding.dataset.common.exceptions.NilParamException;
import com.lazecoding.dataset.common.mvc.ResultBean;
import com.lazecoding.dataset.common.util.LocalDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 常量
 *
 * @author lazecoding
 */
@Controller
@RequestMapping("project")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    /**
     * 获取项目列表
     */
    @RequestMapping("list")
    @ResponseBody
    public ResultBean list() {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            List<String> projects = LocalDataUtil.findProjects();
            resultBean.setValue(projects);
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("获取项目列表失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 创建项目
     */
    @RequestMapping("create")
    @ResponseBody
    public ResultBean create(String projectId) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = LocalDataUtil.createProject(projectId);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("创建项目失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 删除项目
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultBean delete(String projectId) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = LocalDataUtil.deleteProject(projectId);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("删除项目失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }




}
