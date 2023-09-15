package com.lazecoding.dataset.controller;

import com.lazecoding.dataset.common.exceptions.NilParamException;
import com.lazecoding.dataset.common.exceptions.NilValueException;
import com.lazecoding.dataset.common.exceptions.UnCreatedFileException;
import com.lazecoding.dataset.common.mvc.ResultBean;
import com.lazecoding.dataset.common.util.ValidateUtil;
import com.lazecoding.dataset.model.DictItem;
import com.lazecoding.dataset.service.DictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 词典
 *
 * @author lazecoding
 */
@Controller
@RequestMapping("datasource/dict")
public class DictController {

    private static final Logger logger = LoggerFactory.getLogger(DictController.class);

    @Autowired
    private DictService dictService;

    /**
     * 获取词典列表
     */
    @RequestMapping("list-dict")
    @ResponseBody
    public ResultBean listDict() {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            List<String> dicts = dictService.listDict();
            resultBean.setValue(dicts);
            isSuccess = true;
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("获取词典列表失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("获取词典列表失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 创建词典
     */
    @RequestMapping("create-dict")
    @ResponseBody
    public ResultBean createDict(String dictId) {
        if (!StringUtils.hasText(dictId)) {
            throw new NilParamException("dictId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(dictId)) {
            throw new NilParamException("dictId format error.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = dictService.createDict(dictId);
        }catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("创建词典失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("创建词典失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 删除词典
     */
    @RequestMapping("delete-dict")
    @ResponseBody
    public ResultBean deleteDict(String dictId) {
        if (!StringUtils.hasText(dictId)) {
            throw new NilParamException("dictId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(dictId)) {
            throw new NilParamException("dictId format error.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = dictService.deleteDict(dictId);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("删除词典失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 清空词典
     */
    @RequestMapping("drop-dict")
    @ResponseBody
    public ResultBean dropDict() {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = dictService.dropDict();
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("清空词典失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


    /**
     * 获取词条列表
     */
    @RequestMapping("list-item")
    @ResponseBody
    public ResultBean listItem(String dictId) {
        ResultBean resultBean = ResultBean.getInstance();
        if (!StringUtils.hasText(dictId)) {
            throw new NilParamException("dictId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(dictId)) {
            throw new NilParamException("dictId format error.");
        }
        String message = "";
        boolean isSuccess = false;
        try {
            List<DictItem> dictItems = dictService.listItem(dictId);
            resultBean.setValue(dictItems);
            isSuccess = true;
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("获取词条列表失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("获取词条列表失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


    /**
     * 新增词条
     */
    @RequestMapping("add-item")
    @ResponseBody
    public ResultBean addItem(String dictId, String value, String desc) {
        ResultBean resultBean = ResultBean.getInstance();
        if (!StringUtils.hasText(dictId)) {
            throw new NilParamException("dictId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(dictId)) {
            throw new NilParamException("dictId format error.");
        }
        if (!StringUtils.hasText(value)) {
            throw new NilParamException("value is nil.");
        }
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = dictService.addItem(dictId, value, desc);
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("新增词条列表", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("新增词条列表", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 修改词条
     */
    @RequestMapping("modify-item")
    @ResponseBody
    public ResultBean modifyItem(String dictId, String id, String value, String desc) {
        ResultBean resultBean = ResultBean.getInstance();
        if (!StringUtils.hasText(dictId)) {
            throw new NilParamException("dictId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(dictId)) {
            throw new NilParamException("dictId format error.");
        }
        if (!StringUtils.hasText(id)) {
            throw new NilParamException("id is nil.");
        }
        if (!StringUtils.hasText(value)) {
            throw new NilParamException("value is nil.");
        }
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = dictService.modifyItem(dictId, id, value, desc);
        } catch (NilValueException | UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("修改词条失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("修改词条失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 删除词条
     */
    @RequestMapping("delete-item")
    @ResponseBody
    public ResultBean deleteItem(String dictId, String id) {
        ResultBean resultBean = ResultBean.getInstance();
        if (!StringUtils.hasText(dictId)) {
            throw new NilParamException("dictId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(dictId)) {
            throw new NilParamException("dictId format error.");
        }
        if (!StringUtils.hasText(id)) {
            throw new NilParamException("id is nil.");
        }
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = dictService.deleteItem(dictId, id);
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("删除词条失败", e);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("删除词条失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 清空词条
     */
    @RequestMapping("drop-item")
    @ResponseBody
    public ResultBean dropItem(String dictId) {
        ResultBean resultBean = ResultBean.getInstance();
        if (!StringUtils.hasText(dictId)) {
            throw new NilParamException("dictId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(dictId)) {
            throw new NilParamException("dictId format error.");
        }
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = dictService.dropItem(dictId);
        } catch (Exception e) {
            isSuccess = false;
            message = "系统异常";
            logger.error("清空词条失败", e);
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


}
