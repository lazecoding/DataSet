package com.lazecoding.dataset.core.http;

import java.util.List;

/**
 * HTTP 请求
 *
 * @author lazecoding
 */
public class HttpRequest {

    /**
     * 请求路径
     */
    private String url;

    /**
     * 方法 HttpMethodEnum.X.value {@link HttpMethodEnum}
     */
    private String method;

    /**
     * 请求头
     */
    private List<Header> headers;

    /**
     * GET 请求的请求参数
     */
    private List<Param> params;

    /**
     * 请求体
     */
    private Body body;

    /**
     * 模拟数据条数
     */
    private int mockNum;

    /**
     * 备注
     */
    private String comment;

    /**
     * 请求头
     */
    public static class Header {

        public Header() {
        }

        public Header(String name, String value) {
            this.name = name;
            this.value = value;
        }

        /**
         * 字段名
         */
        private String name;

        /**
         * 字段类型
         */
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 请求参数
     */
    public static class Param {

        public Param() {
        }

        public Param(String name, String mockType, String mockParams) {
            this.name = name;
            this.mockType = mockType;
            this.mockParams = mockParams;
        }

        /**
         * 字段名
         */
        private String name;

        /**
         * 模拟类型  MockTypeEnum.X.value {@link com.lazecoding.dataset.core.enums.MockTypeEnum}
         */
        private String mockType;

        /**
         * 模拟参数
         * <p>
         * mockType:none  mockParams:默认值
         * mockType:increase  mockParams:起始值
         * mockType:fixed  mockParams:定值
         * mockType:random  mockParams:MockParamsRandomTypeEnum.X.value {@link com.lazecoding.dataset.core.enums.MockParamsRandomTypeEnum}
         * mockType:rule  mockParams:正则
         * mockType:dict  mockParams:字典Id
         */
        private String mockParams;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMockType() {
            return mockType;
        }

        public void setMockType(String mockType) {
            this.mockType = mockType;
        }

        public String getMockParams() {
            return mockParams;
        }

        public void setMockParams(String mockParams) {
            this.mockParams = mockParams;
        }
    }

    /**
     * Body
     */
    public static class Body {

        /**
         * 类型： HttpBodyTypeEnum.X.value {@link HttpBodyTypeEnum}
         */
        private String type;

        /**
         * form-data 类型的数据
         */
        private List<Param> formData;

        /**
         * x-www-form-urlencoded 类型的数据
         */
        private List<Param> xWwwFormUrlencoded;

        /**
         * raw 类型的数据
         */
        private String raw;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Param> getFormData() {
            return formData;
        }

        public void setFormData(List<Param> formData) {
            this.formData = formData;
        }

        public List<Param> getxWwwFormUrlencoded() {
            return xWwwFormUrlencoded;
        }

        public void setxWwwFormUrlencoded(List<Param> xWwwFormUrlencoded) {
            this.xWwwFormUrlencoded = xWwwFormUrlencoded;
        }

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public int getMockNum() {
        return mockNum;
    }

    public void setMockNum(int mockNum) {
        this.mockNum = mockNum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
