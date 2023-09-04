package com.lazecoding.dataset.core.generator;

import com.lazecoding.dataset.core.enums.MockTypeEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 数据生成器工厂
 * 工厂 + 单例模式，降低开销
 *
 * @author lazecoding
 */
public class DataGeneratorFactory {

    /**
     * 模拟类型 => 生成器映射
     */
    private static final Map<MockTypeEnum, DataGenerator> MOCK_TYPE_DATA_GENERATOR_MAP = new HashMap<>() {{
        put(MockTypeEnum.NONE, new DefaultDataGenerator());
        put(MockTypeEnum.FIXED, new FixedDataGenerator());
        put(MockTypeEnum.RANDOM, new RandomDataGenerator());
        put(MockTypeEnum.RULE, new RuleDataGenerator());
        // put(MockTypeEnum.DICT, new DictDataGenerator());
        put(MockTypeEnum.INCREASE, new IncreaseDataGenerator());
    }};

    private DataGeneratorFactory() {
    }

    /**
     * 获取实例
     *
     * @param mockTypeEnum
     * @return
     */
    public static DataGenerator getGenerator(MockTypeEnum mockTypeEnum) {
        mockTypeEnum = Optional.ofNullable(mockTypeEnum).orElse(MockTypeEnum.NONE);
        return MOCK_TYPE_DATA_GENERATOR_MAP.get(mockTypeEnum);
    }
}
