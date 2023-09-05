package com.lazecoding.dataset.core.producer;

import com.lazecoding.dataset.core.enums.MockTypeEnum;
import com.lazecoding.dataset.core.generator.DataGenerator;
import com.lazecoding.dataset.core.generator.DataGeneratorFactory;
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
     * 生成数据
     *
     * @param tableSchema
     * @param rowNum
     * @return
     */
    public static List<Map<String, Object>> generateData(TableSchema tableSchema) {
        int rowNum = tableSchema.getMockNum();
        List<TableSchema.Field> fieldList = tableSchema.getFieldList();
        // 初始化结果数据
        List<Map<String, Object>> resultList = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            resultList.add(new HashMap<>());
        }
        // 依次生成每一列
        for (TableSchema.Field field : fieldList) {
            MockTypeEnum mockTypeEnum = Optional.ofNullable(MockTypeEnum.getEnumByValue(field.getMockType())).orElse(MockTypeEnum.NONE);
            DataGenerator dataGenerator = DataGeneratorFactory.getGenerator(mockTypeEnum);
            if (ObjectUtils.isEmpty(dataGenerator)) {
                continue;
            }
            List<String> mockDataList = dataGenerator.doGenerate(field, rowNum);
            String fieldName = field.getFieldName();
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
