package com.lazecoding.dataset.core.producer;

import cn.hutool.core.collection.ListUtil;
import com.lazecoding.dataset.core.enums.MockTypeEnum;
import com.lazecoding.dataset.core.generator.DataGenerator;
import com.lazecoding.dataset.core.generator.DataGeneratorFactory;
import com.lazecoding.dataset.core.http.HttpBodyTypeEnum;
import com.lazecoding.dataset.core.http.HttpMethodEnum;
import com.lazecoding.dataset.core.http.HttpRequest;
import com.lazecoding.dataset.core.schema.TableSchema;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 数据生成器
 *
 * @author lazecoding
 */
public class DataProducer {

    /**
     * 批处理大小
     */
    private static final int BATCH_SIZE = 500;

    private DataProducer() {
    }

    /**
     * 生成数据
     *
     * @param tableSchema
     * @return
     */
    public static String generateData(TableSchema tableSchema) {
        if (ObjectUtils.isEmpty(tableSchema)) {
            return "";
        }
        int mockNum = tableSchema.getMockNum();
        if (mockNum == 0) {
            return "";
        }
        List<TableSchema.Field> fieldList = tableSchema.getFieldList();
        // 处理批数
        int batchTotal = (int) Math.ceil((double) mockNum / BATCH_SIZE);
        StringBuilder resultStringBuilder = new StringBuilder();
        for (int i = 0; i < batchTotal; i++) {
            // 算出该批待处理数量
            int generateNum = Math.min(BATCH_SIZE, mockNum - i * BATCH_SIZE);
            // 预处理 list
            List<Map<String, Object>> mockDateCollect = new ArrayList<>(mockNum);
            for (int j = 0; j < generateNum; j++) {
                mockDateCollect.add(new HashMap<>(fieldList.size()));
            }
            // 依次生成每一列
            for (TableSchema.Field field : fieldList) {
                MockTypeEnum mockTypeEnum = Optional.ofNullable(MockTypeEnum.getEnumByValue(field.getMockType())).orElse(MockTypeEnum.NONE);
                DataGenerator dataGenerator = DataGeneratorFactory.getGenerator(mockTypeEnum);
                if (ObjectUtils.isEmpty(dataGenerator)) {
                    continue;
                }
                List<String> mockDataList = dataGenerator.doGenerate(field, generateNum);
                String fieldName = field.getFieldName();
                // 填充结果列表
                if (!CollectionUtils.isEmpty(mockDataList)) {
                    for (int k = 0; k < generateNum; k++) {
                        mockDateCollect.get(k).put(fieldName, mockDataList.get(k));
                    }
                }
            }
            // 生成 SQL
            String buildSql = SqlProducer.buildInsertSql0(tableSchema, mockDateCollect);
            resultStringBuilder.append(buildSql);
            if (i != (batchTotal - 1)) {
                resultStringBuilder.append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    /**
     * 生成数据
     *
     * @param httpRequest
     * @return
     */
    public static List<Map<String, Object>> generateData(HttpRequest httpRequest) {
        if (ObjectUtils.isEmpty(httpRequest)) {
            return null;
        }
        int mockNum = httpRequest.getMockNum();
        if (mockNum == 0) {
            return null;
        }
        HttpMethodEnum httpMethodEnum = HttpMethodEnum.getEnumByValue(httpRequest.getMethod());
        if (ObjectUtils.isEmpty(httpMethodEnum)) {
            return null;
        }
        List<HttpRequest.Param> params = null;
        if (httpMethodEnum.equals(HttpMethodEnum.GET)) {
            params = httpRequest.getParams();
        } else if (httpMethodEnum.equals(HttpMethodEnum.POST)) {
            HttpRequest.Body body = httpRequest.getBody();
            if (ObjectUtils.isEmpty(body)) {
                return null;
            }
            HttpBodyTypeEnum httpBodyTypeEnum = HttpBodyTypeEnum.getEnumByValue(body.getType());
            if (ObjectUtils.isEmpty(httpBodyTypeEnum)) {
                return null;
            }
            if (httpBodyTypeEnum.equals(HttpBodyTypeEnum.NONE) || httpBodyTypeEnum.equals(HttpBodyTypeEnum.RAW)) {
                return null;
            }
            if (httpBodyTypeEnum.equals(HttpBodyTypeEnum.FORM_DATA)) {
                params = body.getFormData();
            } else if (httpBodyTypeEnum.equals(HttpBodyTypeEnum.X_WWW_FORM_URLENCODED)) {
                params = body.getxWwwFormUrlencoded();
            } else {
                return null;
            }
        } else {
            return null;
        }
        // 处理 params
        if (CollectionUtils.isEmpty(params)) {
            return null;
        }
        // 初始化结果数据
        List<Map<String, Object>> resultList = new ArrayList<>(mockNum);
        // 处理批数
        int batchTotal = (int) Math.ceil((double) mockNum / BATCH_SIZE);
        for (int i = 0; i < batchTotal; i++) {
            // 算出该批待处理数量
            int generateNum = Math.min(BATCH_SIZE, mockNum - i * BATCH_SIZE);
            // 预处理 list
            List<Map<String, Object>> mockDateCollect = new ArrayList<>(mockNum);
            for (int j = 0; j < generateNum; j++) {
                mockDateCollect.add(new HashMap<>(params.size()));
            }
            // 依次生成每一列
            for (HttpRequest.Param param : params) {
                MockTypeEnum mockTypeEnum = Optional.ofNullable(MockTypeEnum.getEnumByValue(param.getMockType())).orElse(MockTypeEnum.NONE);
                DataGenerator dataGenerator = DataGeneratorFactory.getGenerator(mockTypeEnum);
                if (ObjectUtils.isEmpty(dataGenerator)) {
                    continue;
                }
                List<String> mockDataList = dataGenerator.doGenerate(param, mockNum);
                String fieldName = param.getName();
                // 填充结果列表
                if (!CollectionUtils.isEmpty(mockDataList)) {
                    for (int k = 0; k < generateNum; k++) {
                        mockDateCollect.get(k).put(fieldName, mockDataList.get(k));
                    }
                }
            }
            // TODO 之后生成一批请求一批，目前先分段生成为分组请求准备
            resultList.addAll(mockDateCollect);
        }
        return resultList;
    }

}
