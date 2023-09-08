package com.lazecoding.dataset.common.exceptions;

/**
 * 未创建的词条
 *
 * @author lazecoding
 */
public class UnCreatedFileException extends RuntimeException {
    public UnCreatedFileException(String msg) {
        super(msg);
    }
}
