package com.lazecoding.dataset.common.exceptions;

/**
 * 空值异常
 *
 * @author lazecoding
 */
public class NilValueException extends RuntimeException {
    public NilValueException(String msg) {
        super(msg);
    }
}
