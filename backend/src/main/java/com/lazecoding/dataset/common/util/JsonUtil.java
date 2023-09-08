package com.lazecoding.dataset.common.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Gson 常量实例
 */
public class JsonUtil {

    public static final Gson GSON = new GsonBuilder()
            .setLenient()// json宽松
            .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
            .serializeNulls() //智能null
            .setPrettyPrinting()// 调教格式
            .disableHtmlEscaping() //默认是GSON把HTML 转义的
            .create();

}
