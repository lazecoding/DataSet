package com.lazecoding.dataset.common.util;

import java.util.regex.Pattern;

/**
 * ValidateUtil
 *
 * @author lazecoding
 */
public class ValidateUtil {

    private ValidateUtil() {
    }

    /**
     * 是否是字母
     */
    private static Pattern Pattern_ENChar = Pattern.compile("[a-z|A-Z]+");

    /**
     * 仅包含 字母、数字、下划线
     */
    private static Pattern Pattern_EnglishNumberLine = Pattern.compile("[a-zA-Z0-9_]+");

    /**
     * 是否是字母
     */
    public static boolean isENChar(String value) {
        return Pattern_ENChar.matcher(value).matches();
    }

    /**
     * 仅包含 字母、数字、下划线
     */
    public static boolean isEnglishNumberLine(String value) {
        return Pattern_EnglishNumberLine.matcher(value).matches();
    }

}
