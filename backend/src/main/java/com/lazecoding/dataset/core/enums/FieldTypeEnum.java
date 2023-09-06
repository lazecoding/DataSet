package com.lazecoding.dataset.core.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字段类型枚举
 *
 * @author lazecoding
 */
public enum FieldTypeEnum {

    TINYINT("tinyint", "Integer", "整数类型（-2^7 到  2^7 - 1）", MockTypeEnum.RANDOM),
    SMALLINT("smallint", "Integer", "整数类型（-2^15 到  2^15 - 1）", MockTypeEnum.RANDOM),
    MEDIUMINT("mediumint", "Integer", "整数类型（-2^24 到  2^24 - 1）", MockTypeEnum.RANDOM),
    INT("int", "Integer", "整数类型（-2^31 到 2^31 - 1）", MockTypeEnum.RANDOM),
    BIGINT("bigint", "Long", "整数类型（-2^63 到 2^63 - 1）", MockTypeEnum.RANDOM),
    FLOAT("float", "Double", "小数（单精度）", MockTypeEnum.RANDOM),
    DOUBLE("double", "Double", "浮点（双精度）", MockTypeEnum.RANDOM),
    DECIMAL("decimal", "BigDecimal", "定点型）", MockTypeEnum.RANDOM),
    DATE("date", "Date", "日期", MockTypeEnum.RANDOM),
    TIME("time", "Time", "时间", MockTypeEnum.RANDOM),
    YEAR("year", "Integer", "年份", MockTypeEnum.RANDOM),
    DATETIME("datetime", "Date", "时间", MockTypeEnum.RANDOM),
    TIMESTAMP("timestamp", "Long", "时间戳", MockTypeEnum.RANDOM),
    CHAR("char", "String", "定长字符", MockTypeEnum.RANDOM),
    VARCHAR("varchar", "String", "变长字符", MockTypeEnum.RANDOM),
    TINYTEXT("tinytext", "String", "文本（2^8-1）", MockTypeEnum.RANDOM),
    TEXT("text", "String", "文本（2^16-1）", MockTypeEnum.RANDOM),
    MEDIUMTEXT("mediumtext", "String", "文本（2^24-1）", MockTypeEnum.RANDOM),
    LONGTEXT("longtext", "String", "文本（(2^32-1）", MockTypeEnum.RANDOM),
    TINYBLOB("tinyblob", "byte[]", "二进制对象（2^8-1）", MockTypeEnum.RANDOM),
    BLOB("blob", "byte[]", "二进制对象（2^16-1）", MockTypeEnum.RANDOM),
    MEDIUMBLOB("mediumblob", "byte[]", "二进制对象（2^24-1）", MockTypeEnum.RANDOM),
    LONGBLOB("longblob", "byte[]", "二进制对象（2^32-1）", MockTypeEnum.RANDOM),
    BINARY("binary", "byte[]", "定长二进制字符串", MockTypeEnum.RANDOM),
    VARBINARY("varbinary", "byte[]", "可变二进制字符串", MockTypeEnum.RANDOM);

    /**
     * 数据库类型
     */
    private final String value;

    /**
     * Java 类型
     */
    private final String javaType;

    /**
     * 描述
     */
    private final String desc;

    private final MockTypeEnum defaultMockType;

    FieldTypeEnum(String value, String javaType, String desc, MockTypeEnum defaultMockType) {
        this.value = value;
        this.javaType = javaType;
        this.desc = desc;
        this.defaultMockType = defaultMockType;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(FieldTypeEnum::getValue).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static FieldTypeEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (FieldTypeEnum mockTypeEnum : FieldTypeEnum.values()) {
            if (mockTypeEnum.value.equals(value)) {
                return mockTypeEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getJavaType() {
        return javaType;
    }

    public String getDesc() {
        return desc;
    }

    public MockTypeEnum getDefaultMockType() {
        return defaultMockType;
    }
}
