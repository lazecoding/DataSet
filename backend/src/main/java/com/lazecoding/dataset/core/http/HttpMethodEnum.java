package com.lazecoding.dataset.core.http;

import org.apache.commons.lang3.StringUtils;

/**
 * 支持的请求类型
 *
 * @author lazecoding
 */
public enum HttpMethodEnum {

    GET("get", "GET"),

    POST("post", "POST");

    private String value;

    private String desc;

    HttpMethodEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     */
    public static HttpMethodEnum getEnumByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (HttpMethodEnum httpMethodEnum : HttpMethodEnum.values()) {
            if (httpMethodEnum.value.equals(value)) {
                return httpMethodEnum;
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
