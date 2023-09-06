package com.lazecoding.dataset.core.generator;

import com.lazecoding.dataset.core.enums.MockParamsRandomTypeEnum;
import com.lazecoding.dataset.core.http.HttpRequest;
import com.lazecoding.dataset.core.schema.TableSchema;
import com.lazecoding.dataset.core.util.FakerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 随机值数据生成器
 *
 * @author lazecoding
 */
public class RandomDataGenerator implements DataGenerator {

    @Override
    public List<String> doGenerate(TableSchema.Field field, int rowNum) {
        String mockParams = field.getMockParams();
        int fieldLength = field.getFieldLength();
        List<String> list = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            MockParamsRandomTypeEnum randomTypeEnum = Optional.ofNullable(
                            MockParamsRandomTypeEnum.getEnumByValue(mockParams))
                    .orElse(MockParamsRandomTypeEnum.STRING);
            String randomString = FakerUtils.getRandomValue(randomTypeEnum);
            if (fieldLength > 0 && randomString.length() > fieldLength) {
                randomString = randomString.substring(0, fieldLength);
            }
            list.add(randomString);
        }
        return list;
    }

    @Override
    public List<String> doGenerate(HttpRequest.Param param, int rowNum) {
        String mockParams = param.getMockParams();
        List<String> list = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            MockParamsRandomTypeEnum randomTypeEnum = Optional.ofNullable(
                            MockParamsRandomTypeEnum.getEnumByValue(mockParams))
                    .orElse(MockParamsRandomTypeEnum.STRING);
            String randomString = FakerUtils.getRandomValue(randomTypeEnum);
            list.add(randomString);
        }
        return list;
    }
}
