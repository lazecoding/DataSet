package com.lazecoding.dataset.core.producer;

import com.lazecoding.dataset.core.http.HttpBodyTypeEnum;
import com.lazecoding.dataset.core.http.HttpMethodEnum;
import com.lazecoding.dataset.core.http.HttpRequest;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * HTTP
 *
 * @author lazy
 */
public class HttpProducer {

    private HttpProducer() {
    }

    public static void doExecute(HttpRequest httpRequest, List<Map<String, Object>> dataList) {
        if (ObjectUtils.isEmpty(httpRequest)) {
            return;
        }

        // 存在 dataList 为空，但是需要请求的情况
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

    private static void handleGet(HttpRequest httpRequest, List<Map<String, Object>> dataList){

    }

    private static void handlePostNone(HttpRequest httpRequest){

    }

    private static void handlePostRaw(HttpRequest httpRequest){

    }

    private static void handlePostFormData(HttpRequest httpRequest, List<Map<String, Object>> dataList){

    }

    private static void handlePostFormUrlencoded(HttpRequest httpRequest, List<Map<String, Object>> dataList){

    }
}
