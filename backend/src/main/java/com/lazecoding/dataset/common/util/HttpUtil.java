package com.lazecoding.dataset.common.util;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * HttpUtil
 *
 * @author lazecoding
 */
public class HttpUtil {

    /**
     * okhttp3 client
     */
    public static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

}
