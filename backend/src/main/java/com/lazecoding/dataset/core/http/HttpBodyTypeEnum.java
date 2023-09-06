package com.lazecoding.dataset.core.http;

import org.apache.commons.lang3.StringUtils;

/**
 * HttpBodyTypeEnum
 *
 * @author lazy
 */

public enum HttpBodyTypeEnum {

    NONE("none", "none"),

    FORM_DATA("form-data", "form-data"),

    X_WWW_FORM_URLENCODED("x-www-form-urlencoded", "x-www-form-urlencoded"),

    RAW("raw", "raw");

    private String value;

    private String desc;

    HttpBodyTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    /**
     * 根据 value 获取枚举
     *
     * @param value
     */
    public static HttpBodyTypeEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (HttpBodyTypeEnum httpBodyTypeEnum : HttpBodyTypeEnum.values()) {
            if (httpBodyTypeEnum.value.equals(value)) {
                return httpBodyTypeEnum;
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
