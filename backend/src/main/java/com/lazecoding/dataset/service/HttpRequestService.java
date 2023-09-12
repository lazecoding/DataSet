package com.lazecoding.dataset.service;

import com.lazecoding.dataset.common.util.JsonUtil;
import com.lazecoding.dataset.common.util.LocalDataUtil;
import com.lazecoding.dataset.core.http.HttpRequest;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * 词典
 *
 * @author lazecoding
 */
@Service
public class HttpRequestService {

    /**
     * 清空
     */
    public List<String> list(String projectId) {
        return LocalDataUtil.findHttpRequests(projectId);
    }

    /**
     * 删除
     */
    public HttpRequest find(String projectId, String httpRequestId) {
        return LocalDataUtil.findHttpRequestFile(projectId, httpRequestId);
    }

    /**
     * 写入
     */
    public boolean write(String projectId, String httpRequestId, HttpRequest httpRequest) {
        String httpRequestJson = JsonUtil.GSON.toJson(httpRequest);
        return LocalDataUtil.writeHttpRequest(projectId, httpRequestId, httpRequestJson);
    }

    /**
     * 删除
     */
    public boolean delete(String projectId, String httpRequestId) {
        return LocalDataUtil.deleteHttpRequest(projectId, httpRequestId);
    }

    /**
     * 清空
     */
    public boolean drop(String projectId) {
        return LocalDataUtil.dropHttpRequest(projectId);
    }


}
