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
        // 初始化结果数据
        List<Map<String, Object>> resultList = new ArrayList<>(mockNum);
        for (int i = 0; i < mockNum; i++) {
            resultList.add(new HashMap<>(fieldList.size()));
        }
        List<List<Map<String, Object>>> lists = ListUtil.partition(resultList, 500);
        if (CollectionUtils.isEmpty(lists)) {
            return "";
        }
        int partitionIndex = 0;
        int partitionSize = lists.size();
        StringBuilder resultStringBuilder = new StringBuilder();
        for (List<Map<String, Object>> itemList : lists) {
            partitionIndex++;
            int generateNum = itemList.size();
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
                    for (int i = 0; i < generateNum; i++) {
                        itemList.get(i).put(fieldName, mockDataList.get(i));
                    }
                }
            }
            // 生成 SQL
            String buildSql = SqlProducer.buildInsertSql0(tableSchema, itemList);
            resultStringBuilder.append(buildSql);
            if (partitionIndex != partitionSize) {
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
        int rowNum = httpRequest.getMockNum();
        if (rowNum == 0) {
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
        List<Map<String, Object>> resultList = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            resultList.add(new HashMap<>());
        }
        for (HttpRequest.Param param : params) {
            MockTypeEnum mockTypeEnum = Optional.ofNullable(MockTypeEnum.getEnumByValue(param.getMockType())).orElse(MockTypeEnum.NONE);
            DataGenerator dataGenerator = DataGeneratorFactory.getGenerator(mockTypeEnum);
            if (ObjectUtils.isEmpty(dataGenerator)) {
                continue;
            }
            List<String> mockDataList = dataGenerator.doGenerate(param, rowNum);
            String fieldName = param.getName();
            // 填充结果列表
            if (!CollectionUtils.isEmpty(mockDataList)) {
                for (int i = 0; i < rowNum; i++) {
                    resultList.get(i).put(fieldName, mockDataList.get(i));
                }
            }
        }
        return resultList;
    }

}
