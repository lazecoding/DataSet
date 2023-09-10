package com.lazecoding.dataset.controller;

import com.lazecoding.dataset.common.exceptions.NilParamException;
import com.lazecoding.dataset.common.exceptions.UnCreatedFileException;
import com.lazecoding.dataset.common.mvc.ResultBean;
import com.lazecoding.dataset.common.util.LocalDataUtil;
import com.lazecoding.dataset.core.schema.SchemaException;
import com.lazecoding.dataset.core.schema.TableSchema;
import com.lazecoding.dataset.core.schema.TableSchemaParser;
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

    // 获取 TableSchema 列表

    // 获取 TableSchema 属性

    /**
     * 根据 SQL 创建 TableSchema
     */
    @RequestMapping("sql-parser")
    @ResponseBody
    public ResultBean sqlParser(String projectId, String sql) {
        if (!StringUtils.hasText(projectId)) {
            throw new NilParamException("projectId is nil.");
        }
        if (!StringUtils.hasText(sql)) {
            throw new NilParamException("sql is nil.");
        }
        ResultBean resultBean = ResultBean.getInstance();
        boolean isSuccess = false;
        String message = "";
        try {
            TableSchema tableSchema = tableSchemaService.sqlParser(projectId, sql);
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

    // 手工创建 TableSchema

    // 修改 TableSchema

    // 删除 TableSchema

    // 清空 TableSchema

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
