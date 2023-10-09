package com.lazecoding.dataset.core.producer;

import com.lazecoding.dataset.common.util.HttpUtil;
import com.lazecoding.dataset.core.http.HttpBodyTypeEnum;
import com.lazecoding.dataset.core.http.HttpMethodEnum;
import com.lazecoding.dataset.core.http.HttpRequest;
import okhttp3.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * HTTP
 *
 * @author lazy
 */
public class HttpProducer {

    private HttpProducer() {
    }

    public static void doExecute(HttpRequest httpRequest, List<Map<String, Object>> dataList) {
        if (ObjectUtils.isEmpty(httpRequest) || !StringUtils.hasText(httpRequest.getUrl())
                || !StringUtils.hasText(httpRequest.getMethod()) || httpRequest.getMockNum() <= 0) {
            return;
        }
        // 注意：存在 dataList 为空，但是需要请求的情况
        HttpMethodEnum httpMethodEnum = HttpMethodEnum.getEnumByValue(httpRequest.getMethod());
        if (ObjectUtils.isEmpty(httpMethodEnum)) {
            return;
        }
        if (httpMethodEnum.equals(HttpMethodEnum.GET)) {
            handleGet(httpRequest, dataList);
        } else if (httpMethodEnum.equals(HttpMethodEnum.POST)) {
            HttpRequest.Body body = httpRequest.getBody();
            if (!ObjectUtils.isEmpty(body)) {
                HttpBodyTypeEnum httpBodyTypeEnum = HttpBodyTypeEnum.getEnumByValue(body.getType());
                if (!ObjectUtils.isEmpty(httpBodyTypeEnum)) {
                    if (httpBodyTypeEnum.equals(HttpBodyTypeEnum.NONE)) {
                        handlePostNone(httpRequest);
                    }
                    if (httpBodyTypeEnum.equals(HttpBodyTypeEnum.RAW)) {
                        handlePostRaw(httpRequest);
                    }
                    if (httpBodyTypeEnum.equals(HttpBodyTypeEnum.FORM_DATA)) {
                        handlePostFormData(httpRequest, dataList);
                    }
                    if (httpBodyTypeEnum.equals(HttpBodyTypeEnum.X_WWW_FORM_URLENCODED)) {
                        handlePostFormUrlencoded(httpRequest, dataList);
                    }
                }
            }
        }
    }

    private static void handleGet(HttpRequest httpRequest, List<Map<String, Object>> dataList) {
        String url = httpRequest.getUrl();
        List<HttpRequest.Header> headers = httpRequest.getHeaders();
        List<HttpRequest.Param> params = httpRequest.getParams();
        int mockNum = httpRequest.getMockNum();
        int index = 0;
        while (index < mockNum) {
            // 构建过程
            Request.Builder requestBuilder = new Request.Builder().get();
            HttpUrl.Builder httpUrlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
            if (!CollectionUtils.isEmpty(headers)) {
                for (HttpRequest.Header header : headers) {
                    requestBuilder.addHeader(header.getName(), header.getValue());
                }
            }
            if (!CollectionUtils.isEmpty(params)) {
                // params 不为空，需要使用模拟数据
                if (!CollectionUtils.isEmpty(dataList)) {
                    if (index < dataList.size()) {
                        Map<String, Object> mockDataLine = dataList.get(index);
                        if (!CollectionUtils.isEmpty(mockDataLine)) {
                            for (HttpRequest.Param param : params) {
                                Object mockValue = mockDataLine.get(param.getName());
                                httpUrlBuilder.addQueryParameter(param.getName(), mockValue.toString());
                            }
                        }
                    } else {
                        // 模拟数据不满足生成数据条数
                    }
                }
            }

            // 构建结果
            Request request = requestBuilder
                    .url(httpUrlBuilder.build())
                    .build();
            try {
                Response response = HttpUtil.HTTP_CLIENT.newCall(request).execute();
                System.out.println(response);
                System.out.println(response.isSuccessful());
                System.out.println(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }
            index++;
        }
    }

    private static void handlePostNone(HttpRequest httpRequest) {
        String url = httpRequest.getUrl();
        List<HttpRequest.Header> headers = httpRequest.getHeaders();
        int mockNum = httpRequest.getMockNum();
        int index = 0;
        while (index < mockNum) {
            // 构建过程
            Request.Builder requestBuilder = new Request.Builder();
            HttpUrl.Builder httpUrlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
            if (!CollectionUtils.isEmpty(headers)) {
                for (HttpRequest.Header header : headers) {
                    requestBuilder.addHeader(header.getName(), header.getValue());
                }
            }
            FormBody body = new FormBody.Builder().build();
            // 构建结果
            Request request = requestBuilder
                    .url(httpUrlBuilder.build())
                    .post(body)
                    .build();
            try {
                Response response = HttpUtil.HTTP_CLIENT.newCall(request).execute();
                System.out.println(response);
                System.out.println(response.isSuccessful());
                System.out.println(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }
            index++;
        }
    }

    private static void handlePostRaw(HttpRequest httpRequest) {
        String url = httpRequest.getUrl();
        List<HttpRequest.Header> headers = httpRequest.getHeaders();
        int mockNum = httpRequest.getMockNum();
        int index = 0;
        MediaType rawMediaType = MediaType.Companion.parse("application/json; charset=utf-8");
        while (index < mockNum) {
            // 构建过程
            Request.Builder requestBuilder = new Request.Builder();
            HttpUrl.Builder httpUrlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
            if (!CollectionUtils.isEmpty(headers)) {
                for (HttpRequest.Header header : headers) {
                    requestBuilder.addHeader(header.getName(), header.getValue());
                }
            }
            RequestBody body = RequestBody.Companion.create(httpRequest.getBody().getRaw(), rawMediaType);
            // 构建结果
            Request request = requestBuilder
                    .url(httpUrlBuilder.build())
                    .post(body)
                    .build();
            try {
                Response response = HttpUtil.HTTP_CLIENT.newCall(request).execute();
                System.out.println(response);
                System.out.println(response.isSuccessful());
                System.out.println(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }
            index++;
        }
    }

    private static void handlePostFormData(HttpRequest httpRequest, List<Map<String, Object>> dataList) {
        String url = httpRequest.getUrl();
        List<HttpRequest.Header> headers = httpRequest.getHeaders();
        List<HttpRequest.Param> formData = httpRequest.getBody().getFormData();
        int mockNum = httpRequest.getMockNum();
        int index = 0;
        while (index < mockNum) {
            // 构建过程
            Request.Builder requestBuilder = new Request.Builder();
            HttpUrl.Builder httpUrlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
            if (!CollectionUtils.isEmpty(headers)) {
                for (HttpRequest.Header header : headers) {
                    requestBuilder.addHeader(header.getName(), header.getValue());
                }
            }
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            if (!CollectionUtils.isEmpty(formData)) {
                // params 不为空，需要使用模拟数据
                if (!CollectionUtils.isEmpty(dataList)) {
                    if (index < dataList.size()) {
                        Map<String, Object> mockDataLine = dataList.get(index);
                        if (!CollectionUtils.isEmpty(mockDataLine)) {
                            for (HttpRequest.Param param : formData) {
                                Object mockValue = mockDataLine.get(param.getName());
                                bodyBuilder.add(param.getName(), mockValue.toString());
                            }
                        }
                    } else {
                        // 模拟数据不满足生成数据条数
                    }
                }
            }

            // 构建结果
            Request request = requestBuilder
                    .url(httpUrlBuilder.build())
                    .post(bodyBuilder.build())
                    .build();
            try {
                Response response = HttpUtil.HTTP_CLIENT.newCall(request).execute();
                System.out.println(response);
                System.out.println(response.isSuccessful());
                System.out.println(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }
            index++;
        }
    }

    private static void handlePostFormUrlencoded(HttpRequest httpRequest, List<Map<String, Object>> dataList) {
        String url = httpRequest.getUrl();
        List<HttpRequest.Header> headers = httpRequest.getHeaders();
        List<HttpRequest.Param> xWwwFormUrlencoded = httpRequest.getBody().getxWwwFormUrlencoded();
        int mockNum = httpRequest.getMockNum();
        int index = 0;
        while (index < mockNum) {
            // 构建过程
            Request.Builder requestBuilder = new Request.Builder();
            HttpUrl.Builder httpUrlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
            if (!CollectionUtils.isEmpty(headers)) {
                for (HttpRequest.Header header : headers) {
                    requestBuilder.addHeader(header.getName(), header.getValue());
                }
            }
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            if (!CollectionUtils.isEmpty(xWwwFormUrlencoded)) {
                // params 不为空，需要使用模拟数据
                if (!CollectionUtils.isEmpty(dataList)) {
                    if (index < dataList.size()) {
                        Map<String, Object> mockDataLine = dataList.get(index);
                        if (!CollectionUtils.isEmpty(mockDataLine)) {
                            for (HttpRequest.Param param : xWwwFormUrlencoded) {
                                Object mockValue = mockDataLine.get(param.getName());
                                bodyBuilder.add(param.getName(), mockValue.toString());
                            }
                        }
                    } else {
                        // 模拟数据不满足生成数据条数
                    }
                }
            }

            // 构建结果
            Request request = requestBuilder
                    .url(httpUrlBuilder.build())
                    .post(bodyBuilder.build())
                    .build();
            try {
                Response response = HttpUtil.HTTP_CLIENT.newCall(request).execute();
                System.out.println(response);
                System.out.println(response.isSuccessful());
                System.out.println(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }
            index++;
        }
    }
}
