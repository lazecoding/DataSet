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
import org.springframework.util.StringUtils;

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
    public TableSchema sqlParser(String projectId, String tableSchemaId, String sql) {
        // 先检查项目是否存在
        LocalDataUtil.checkProjectExist(projectId);
        // 解析
        TableSchema tableSchema = TableSchemaParser.parserFromSql(sql);
        if (!ObjectUtils.isEmpty(tableSchema)) {
            if (!StringUtils.hasText(tableSchemaId)) {
                tableSchemaId = tableSchema.getTableName() + "_" + System.currentTimeMillis();
            }
            this.write(projectId, tableSchemaId, tableSchema);
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
     * 写入
     */
    public boolean write(String projectId, String tableSchemaId, TableSchema tableSchema) {
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

    /**
     * 生成
     */
    public boolean generator(String projectId, String tableSchemaId) {
        TableSchema tableSchema = this.find(projectId, tableSchemaId);
        if (ObjectUtils.isEmpty(tableSchema)) {
            return false;
        }
        // 生成模拟数据
        List<Map<String, Object>> dataList = DataProducer.generateData(tableSchema);
        if (CollectionUtils.isEmpty(dataList)) {
            return false;
        }
        // 生成 SQL
        String insertSql = SqlProducer.buildInsertSql(tableSchema, dataList);
        if (!StringUtils.hasText(insertSql)) {
            return false;
        }
        // 写入文件
        return LocalDataUtil.writeTableSchemaResult(projectId, tableSchemaId, insertSql);
    }


}
