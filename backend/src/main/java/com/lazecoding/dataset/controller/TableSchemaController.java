package com.lazecoding.dataset.controller;

import com.lazecoding.dataset.common.exceptions.NilParamException;
import com.lazecoding.dataset.common.exceptions.UnCreatedFileException;
import com.lazecoding.dataset.common.mvc.ResultBean;
import com.lazecoding.dataset.common.util.ValidateUtil;
import com.lazecoding.dataset.core.schema.SchemaException;
import com.lazecoding.dataset.core.schema.TableSchema;
import com.lazecoding.dataset.service.TableSchemaService;
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
 * TableSchema
 *
 * @author lazecoding
 */
@Controller
@RequestMapping("mick/schema")
public class TableSchemaController {

    private static final Logger logger = LoggerFactory.getLogger(TableSchemaController.class);

    @Autowired
    private TableSchemaService tableSchemaService;

    /**
     * 根据 SQL 创建 TableSchema
     */
    @RequestMapping("sql-parser")
    @ResponseBody
    public ResultBean sqlParser(String projectId, String tableSchemaId, String sql) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(projectId)) {
            throw new NilParamException("projectId format error.");
        }
        if (!StringUtils.hasText(sql)) {
            throw new NilParamException("sql is nil.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            TableSchema tableSchema = tableSchemaService.sqlParser(projectId, tableSchemaId, sql);
            resultBean.setValue(tableSchema);
            isSuccess = true;
        } catch (NilParamException | SchemaException | UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("解析 SQL 异常", e);
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
     * 获取 TableSchema 列表
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
            List<String> tableSchemas = tableSchemaService.list(projectId);
            resultBean.setValue(tableSchemas);
            isSuccess = true;
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("获取 TableSchema 列表失败", e);
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
     * 获取 TableSchema
     */
    @RequestMapping("find")
    @ResponseBody
    public ResultBean find(String projectId, String tableSchemaId) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(projectId)) {
            throw new NilParamException("projectId format error.");
        }
        if (!StringUtils.hasText(tableSchemaId)) {
            throw new NilParamException("tableSchemaId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(tableSchemaId)) {
            throw new NilParamException("tableSchemaId format error.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            TableSchema tableSchema = tableSchemaService.find(projectId, tableSchemaId);
            resultBean.setValue(tableSchema);
            isSuccess = true;
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("删除 TableSchema 失败", e);
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
     * 写入 TableSchema
     */
    @RequestMapping("write")
    @ResponseBody
    public ResultBean write(String projectId, String tableSchemaId, @RequestBody TableSchema tableSchema) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(projectId)) {
            throw new NilParamException("projectId format error.");
        }
        if (!StringUtils.hasText(tableSchemaId)) {
            throw new NilParamException("tableSchemaId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(tableSchemaId)) {
            throw new NilParamException("tableSchemaId format error.");
        }
        if (ObjectUtils.isEmpty(tableSchema)) {
            throw new NilParamException("tableSchema is nil.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            isSuccess = tableSchemaService.write(projectId, tableSchemaId, tableSchema);
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("新增 TableSchema 失败", e);
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
     * 删除 TableSchema
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultBean delete(String projectId, String tableSchemaId) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(projectId)) {
            throw new NilParamException("projectId format error.");
        }
        if (!StringUtils.hasText(tableSchemaId)) {
            throw new NilParamException("tableSchemaId is nil.");
        }
        if (!ValidateUtil.isEnglishNumberLine(tableSchemaId)) {
            throw new NilParamException("tableSchemaId format error.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            isSuccess = tableSchemaService.delete(projectId, tableSchemaId);
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("删除 TableSchema 失败", e);
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
     * 清空 TableSchema
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
            isSuccess = tableSchemaService.drop(projectId);
        } catch (UnCreatedFileException e) {
            isSuccess = false;
            message = e.getMessage();
            logger.error("清空 TableSchema 失败", e);
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
    public ResultBean preview(@RequestBody TableSchema tableSchema) {
        if (ObjectUtils.isEmpty(tableSchema)) {
            throw new NilParamException("tableSchema is nil.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            String result = tableSchemaService.preview(tableSchema);
            resultBean.setValue(result);
            isSuccess = true;
        } catch (NilParamException | SchemaException | UnCreatedFileException e) {
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

}
