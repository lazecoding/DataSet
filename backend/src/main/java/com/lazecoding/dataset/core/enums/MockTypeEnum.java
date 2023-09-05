package com.lazecoding.dataset.core.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 模拟类型枚举
 *
 * @author lazecoding
 */
public enum MockTypeEnum {

    NONE("none", "不模拟"),

    INCREASE("increase", "递增"),

    FIXED("fixed", "固定"),

    RANDOM("random", "随机"),

    RULE("rule", "规则"),

    DICT("dict", "词库");

    private final String value;

    private final String desc;

    MockTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(MockTypeEnum::getValue).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static MockTypeEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (MockTypeEnum mockTypeEnum : MockTypeEnum.values()) {
            if (mockTypeEnum.value.equals(value)) {
                return mockTypeEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
