package com.lazecoding.dataset.bean;

import com.lazecoding.dataset.core.http.HttpRequest;

import java.io.Serializable;

/**
 * HttpRequestQueryBean
 *
 * @author lazecoding
 */
public class HttpRequestQueryBean implements Serializable {

    private static final long serialVersionUID = -1;

    /**
     * 项目
     */
    private String projectId;

    /**
     * Id
     */
    String httpRequestId;

    /**
     * httpRequest 对象
     */
    HttpRequest httpRequest;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getHttpRequestId() {
        return httpRequestId;
    }

    public void setHttpRequestId(String httpRequestId) {
        this.httpRequestId = httpRequestId;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }
}
