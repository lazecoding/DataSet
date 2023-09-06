package com.lazecoding.dataset.common.mvc;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResultBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 附加信息
     */
    private String message = "";

    /**
     * 状态码
     */
    private String code = "200";

    /**
     * 是否成功
     */
    private boolean isSuccess = true;

    /**
     * 数据集合
     */
    private Map<String, Object> data = new HashMap(4);

    /**
     * 数据
     */
    private Object value;

    /**
     * 获取实例
     */
    public static ResultBean getInstance() {
        return new ResultBean();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCode(int code) {
        this.code = String.valueOf(code);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public ResultBean addData(String key, Object value) {
        if (StringUtils.hasLength(key)) {
            this.data.put(key, value);
        }
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "ResultBean{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", isSuccess=" + isSuccess +
                ", data=" + data +
                ", value=" + value +
                '}';
    }
}
