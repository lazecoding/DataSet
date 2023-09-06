package com.lazecoding.dataset.core.generator;

import com.lazecoding.dataset.core.http.HttpRequest;
import com.lazecoding.dataset.core.schema.TableSchema;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 递增值数据生成器
 *
 * @author lazecoding
 */
public class IncreaseDataGenerator implements DataGenerator {

    @Override
    public List<String> doGenerate(TableSchema.Field field, int rowNum) {
        String mockParams = field.getMockParams();
        List<String> list = new ArrayList<>(rowNum);
        if (StringUtils.isBlank(mockParams)) {
            mockParams = "1";
        }
        int initValue = Integer.parseInt(mockParams);
        for (int i = 0; i < rowNum; i++) {
            list.add(String.valueOf(initValue + i));
        }
        return list;
    }

    @Override
    public List<String> doGenerate(HttpRequest.Param param, int rowNum) {
        String mockParams = param.getMockParams();
        List<String> list = new ArrayList<>(rowNum);
        if (StringUtils.isBlank(mockParams)) {
            mockParams = "1";
        }
        int initValue = Integer.parseInt(mockParams);
        for (int i = 0; i < rowNum; i++) {
            list.add(String.valueOf(initValue + i));
        }
        return list;
    }
}
