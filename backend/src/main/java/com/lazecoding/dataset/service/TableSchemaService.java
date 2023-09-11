package com.lazecoding.dataset.service;

import com.lazecoding.dataset.common.exceptions.NilParamException;
import com.lazecoding.dataset.common.util.JsonUtil;
import com.lazecoding.dataset.common.util.LocalDataUtil;
import com.lazecoding.dataset.core.producer.DataProducer;
import com.lazecoding.dataset.core.producer.SqlProducer;
import com.lazecoding.dataset.core.schema.TableSchema;
import com.lazecoding.dataset.core.schema.TableSchemaParser;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * TableSchema
 *
 * @author lazecoding
 */
@Service
public class TableSchemaService {

    /**
     * 根据 SQL 创建 TableSchema
     */
    public TableSchema sqlParser(String projectId, String sql) {
        // 先检查项目是否存在
        LocalDataUtil.checkProjectExist(projectId);
        // 解析
        TableSchema tableSchema = TableSchemaParser.parserFromSql(sql);
        if (!ObjectUtils.isEmpty(tableSchema)) {
            this.add(projectId,tableSchema);
        }
        return tableSchema;
    }

    /**
     * 清空
     */
    public List<String> list(String projectId) {
        return LocalDataUtil.findTableSchemas(projectId);
    }

    /**
     * 删除
     */
    public TableSchema find(String projectId, String tableSchemaId) {
        return LocalDataUtil.findTableSchemaFile(projectId, tableSchemaId);
    }


    /**
     * 新增
     */
    public String add(String projectId, TableSchema tableSchema) {
        String tableSchemaJson = JsonUtil.GSON.toJson(tableSchema);
        String tableSchemaId = tableSchema.getTableName() + "_" + System.currentTimeMillis();
        if (LocalDataUtil.writeTableSchema(projectId, tableSchemaId, tableSchemaJson)) {
            return tableSchemaId;
        }
        return "";
    }

    /**
     * 修改
     */
    public boolean modify(String projectId, String tableSchemaId, TableSchema tableSchema) {
        String tableSchemaJson = JsonUtil.GSON.toJson(tableSchema);
        return LocalDataUtil.writeTableSchema(projectId, tableSchemaId, tableSchemaJson);
    }


    /**
     * 删除
     */
    public boolean delete(String projectId, String tableSchemaId) {
        return LocalDataUtil.deleteTableSchema(projectId, tableSchemaId);
    }

    /**
     * 清空
     */
    public boolean drop(String projectId) {
        return LocalDataUtil.dropTableSchema(projectId);
    }


    /**
     * 预览结果
     */
    public String preview(TableSchema tableSchema) {
        if (ObjectUtils.isEmpty(tableSchema)) {
            throw new NilParamException("tableSchema is nil");
        }
        // 1. 预览一条数据
        tableSchema.setMockNum(1);
        // 2. 生产数据
        List<Map<String, Object>> dataList = DataProducer.generateData(tableSchema);
        // 3. 使用模拟数据生成 SQL
        return SqlProducer.buildInsertSql(tableSchema, dataList);
    }

}
