package com.lazecoding.dataset.service;

import com.lazecoding.dataset.common.exceptions.NilParamException;
import com.lazecoding.dataset.common.util.JsonUtil;
import com.lazecoding.dataset.common.util.LocalDataUtil;
import com.lazecoding.dataset.core.http.HttpRequest;
import com.lazecoding.dataset.core.producer.DataProducer;
import com.lazecoding.dataset.core.producer.HttpProducer;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

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

    /**
     * 预览结果
     */
    public HttpProducer.CallbackInfo preview(HttpRequest httpRequest) {
        if (ObjectUtils.isEmpty(httpRequest) || !StringUtils.hasText(httpRequest.getUrl())
                || !StringUtils.hasText(httpRequest.getMethod())) {
            throw new NilParamException("there is one or more params is nil");
        }
        // 预览一条数据
        httpRequest.setMockNum(1);
        HttpProducer.CallbackInfo callbackInfo = new HttpProducer.CallbackInfo(true);
        this.doExecute(httpRequest, callbackInfo);
        return callbackInfo;
    }

    /**
     * 生成
     */
    public boolean generator(String projectId, String httpRequestId) {
        HttpRequest httpRequest = this.find(projectId, httpRequestId);
        if (ObjectUtils.isEmpty(httpRequest) || !StringUtils.hasText(httpRequest.getUrl())
                || !StringUtils.hasText(httpRequest.getMethod()) || httpRequest.getMockNum() <= 0) {
            throw new NilParamException("there is one or more params is nil");
        }
        return this.doExecute(httpRequest, null);
    }

    /**
     * 执行
     */
    public boolean doExecute(HttpRequest httpRequest, HttpProducer.CallbackInfo callbackInfo) {
        if (ObjectUtils.isEmpty(httpRequest)) {
            throw new NilParamException("httpRequest is nil");
        }
        // 1. 生产数据
        List<Map<String, Object>> dataList = DataProducer.generateData(httpRequest);
        // 2. 接口调用
        HttpProducer.doExecute(httpRequest, dataList, callbackInfo);
        return true;
    }

}
