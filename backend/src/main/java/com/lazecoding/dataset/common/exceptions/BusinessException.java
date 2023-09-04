package com.lazecoding.dataset.common.exceptions;

import com.lazecoding.dataset.common.constans.ResponseCode;

/**
 * 自定义异常类
 *
 * @author lazecoding
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    public BusinessException(ResponseCode responseCode, String message) {
        super(message);
        this.code = responseCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
