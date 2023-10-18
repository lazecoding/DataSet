package com.lazecoding.dataset.controller;

import com.lazecoding.dataset.bean.HttpRequestQueryBean;
import com.lazecoding.dataset.common.exceptions.NilParamException;
import com.lazecoding.dataset.common.exceptions.UnCreatedFileException;
import com.lazecoding.dataset.common.mvc.ResultBean;
import com.lazecoding.dataset.common.util.ValidateUtil;
import com.lazecoding.dataset.core.http.HttpRequest;
import com.lazecoding.dataset.service.HttpRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * HttpRequest
 *
 * @author lazecoding
 */
@Controller
@RequestMapping("mick/http")
public class HttpRequestController {

    @Autowired
    private HttpRequestService httpRequestService;

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestController.class);
    
    /**
     * 获取 HttpRequest 列表
     */
    @RequestMapping("list")
    @ResponseBody
    public ResultBean list(String projectId) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(projectId)) {
            throw new NilParamException("projectId format error.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            List<String> httpRequests = httpRequestService.list(projectId);
            resultBean.setValue(httpRequests);
            isSuccess = true;
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("获取 httpRequest 列表失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("系统异常", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 获取 HttpRequest
     */
    @RequestMapping("find")
    @ResponseBody
    public ResultBean find(String projectId, String httpRequestId) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(projectId)) {
            throw new NilParamException("projectId format error.");
        }
        if (!StringUtils.hasText(httpRequestId)) {
            throw new NilParamException("httpRequestId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(httpRequestId)) {
            throw new NilParamException("httpRequestId format error.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            HttpRequest httpRequest = httpRequestService.find(projectId, httpRequestId);
            resultBean.setValue(httpRequest);
            isSuccess = true;
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("删除 httpRequest 失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("系统异常", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 写入 HttpRequest
     */
    @RequestMapping("write")
    @ResponseBody
    public ResultBean write(@RequestBody HttpRequestQueryBean httpRequestQueryBean) {
        if (ObjectUtils.isEmpty(httpRequestQueryBean)) {
            throw new NilParamException("query json is nil.");
        }
        String projectId = httpRequestQueryBean.getProjectId();
        String httpRequestId = httpRequestQueryBean.getHttpRequestId();
        HttpRequest httpRequest = httpRequestQueryBean.getHttpRequest();
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(projectId)) {
            throw new NilParamException("projectId format error.");
        }
        if (!StringUtils.hasText(httpRequestId)) {
            throw new NilParamException("httpRequestId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(httpRequestId)) {
            throw new NilParamException("httpRequestId format error.");
        }
        if (ObjectUtils.isEmpty(httpRequest)) {
            throw new NilParamException("httpRequest is nil.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            isSuccess = httpRequestService.write(projectId, httpRequestId, httpRequest);
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("新增 HttpRequest 失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("系统异常", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


    /**
     * 写入 HttpRequest
     */
    @RequestMapping("write0")
    @ResponseBody
    public ResultBean write0(String projectId, String httpRequestId, @RequestBody HttpRequest httpRequest) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(projectId)) {
            throw new NilParamException("projectId format error.");
        }
        if (!StringUtils.hasText(httpRequestId)) {
            throw new NilParamException("httpRequestId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(httpRequestId)) {
            throw new NilParamException("httpRequestId format error.");
        }
        if (ObjectUtils.isEmpty(httpRequest)) {
            throw new NilParamException("httpRequest is nil.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            isSuccess = httpRequestService.write(projectId, httpRequestId, httpRequest);
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("新增 HttpRequest 失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("系统异常", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 删除 HttpRequest
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultBean delete(String projectId, String httpRequestId) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(projectId)) {
            throw new NilParamException("projectId format error.");
        }
        if (!StringUtils.hasText(httpRequestId)) {
            throw new NilParamException("httpRequestId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(httpRequestId)) {
            throw new NilParamException("httpRequestId format error.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            isSuccess = httpRequestService.delete(projectId, httpRequestId);
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("删除 HttpRequest 失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("系统异常", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 清空 HttpRequest
     */
    @RequestMapping("drop")
    @ResponseBody
    public ResultBean drop(String projectId) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(projectId)) {
            throw new NilParamException("projectId format error.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            isSuccess = httpRequestService.drop(projectId);
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("清空 HttpRequest 失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("系统异常", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


    /**
     * 预览结果
     *
     * @return
     */
    @RequestMapping("preview")
    @ResponseBody
    public ResultBean preview(@RequestBody HttpRequest httpRequest) {
        if (ObjectUtils.isEmpty(httpRequest)) {
            throw new NilParamException("httpRequest is nil.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            String result = httpRequestService.preview(httpRequest);
            resultBean.setValue(result);
            isSuccess = true;
        } catch (NilParamException | UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("预览结果失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("系统异常", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


    /**
     * 执行 HttpRequest 生成数据
     */
    @RequestMapping("generator")
    @ResponseBody
    public ResultBean generator(String projectId, String httpRequestId) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(projectId)) {
            throw new NilParamException("projectId format error.");
        }
        if (!StringUtils.hasText(httpRequestId)) {
            throw new NilParamException("tableSchemaId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(httpRequestId)) {
            throw new NilParamException("tableSchemaId format error.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            isSuccess = httpRequestService.generator(projectId, httpRequestId);
        } catch (NilParamException | UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("执行 HttpRequest 生成数据失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("系统异常", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }







}
