package com.lazecoding.dataset.core.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 模拟参数随机类型枚举
 *
 * @author lazecoding
 **/
public enum MockParamsRandomTypeEnum {

    STRING("string", "字符串"),

    NAME("name", "人名"),

    CITY("city", "城市"),

    URL("url", "网址"),

    EMAIL("email", "邮箱"),

    IP("ip", "IP"),

    INTEGER("integer", "整数"),

    DECIMAL("decimal", "小数"),

    UNIVERSITY("university", "大学"),

    DATE("date", "日期"),

    TIMESTAMP("timestamp", "时间戳"),

    PHONE("phone", "手机号");

    private final String value;

    private final String desc;

    MockParamsRandomTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(MockParamsRandomTypeEnum::getValue).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static MockParamsRandomTypeEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (MockParamsRandomTypeEnum mockTypeEnum : MockParamsRandomTypeEnum.values()) {
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
