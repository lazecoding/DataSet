package com.lazecoding.dataset.service;

import com.lazecoding.dataset.common.exceptions.NilParamException;
import com.lazecoding.dataset.common.util.LocalDataUtil;
import com.lazecoding.dataset.core.producer.DataProducer;
import com.lazecoding.dataset.core.producer.SqlProducer;
import com.lazecoding.dataset.core.schema.TableSchema;
import com.lazecoding.dataset.core.schema.TableSchemaParser;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

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
        return tableSchema;
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
