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

    TINYINT("tinyint", "Integer", "number", MockTypeEnum.RANDOM),
    SMALLINT("smallint", "Integer", "number", MockTypeEnum.RANDOM),
    MEDIUMINT("mediumint", "Integer", "number", MockTypeEnum.RANDOM),
    INT("int", "Integer", "number", MockTypeEnum.RANDOM),
    BIGINT("bigint", "Long", "number", MockTypeEnum.RANDOM),
    FLOAT("float", "Double", "number", MockTypeEnum.RANDOM),
    DOUBLE("double", "Double", "number", MockTypeEnum.RANDOM),
    DECIMAL("decimal", "BigDecimal", "number", MockTypeEnum.RANDOM),
    DATE("date", "Date", "Date", MockTypeEnum.RANDOM),
    TIME("time", "Time", "Date", MockTypeEnum.RANDOM),
    YEAR("year", "Integer", "number", MockTypeEnum.RANDOM),
    DATETIME("datetime", "Date", "Date", MockTypeEnum.RANDOM),
    TIMESTAMP("timestamp", "Long", "number", MockTypeEnum.RANDOM),
    CHAR("char", "String", "string", MockTypeEnum.RANDOM),
    VARCHAR("varchar", "String", "string", MockTypeEnum.RANDOM),
    TINYTEXT("tinytext", "String", "string", MockTypeEnum.RANDOM),
    TEXT("text", "String", "string", MockTypeEnum.RANDOM),
    MEDIUMTEXT("mediumtext", "String", "string", MockTypeEnum.RANDOM),
    LONGTEXT("longtext", "String", "string", MockTypeEnum.RANDOM),
    TINYBLOB("tinyblob", "byte[]", "string", MockTypeEnum.RANDOM),
    BLOB("blob", "byte[]", "string", MockTypeEnum.RANDOM),
    MEDIUMBLOB("mediumblob", "byte[]", "string", MockTypeEnum.RANDOM),
    LONGBLOB("longblob", "byte[]", "string", MockTypeEnum.RANDOM),
    BINARY("binary", "byte[]", "string", MockTypeEnum.RANDOM),
    VARBINARY("varbinary", "byte[]", "string", MockTypeEnum.RANDOM);

    /**
     * 数据库类型
     */
    private final String value;

    /**
     * Java 类型
     */
    private final String javaType;

    /**
     * TS 类型
     */
    private final String typescriptType;


    private final MockTypeEnum defaultMockType;

    FieldTypeEnum(String value, String javaType, String typescriptType, MockTypeEnum defaultMockType) {
        this.value = value;
        this.javaType = javaType;
        this.typescriptType = typescriptType;
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

    public String getTypescriptType() {
        return typescriptType;
    }


    public MockTypeEnum getDefaultMockType() {
        return defaultMockType;
    }
}
