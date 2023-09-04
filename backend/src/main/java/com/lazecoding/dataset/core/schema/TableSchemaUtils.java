package com.lazecoding.dataset.core.schema;

/**
 * 表结构工具类
 *
 * @author laze
 */
public class TableSchemaUtils {

    /**
     * 封装字段名
     *
     * @param name
     * @return
     */
    public static String wrapFieldName(String name) {
        return String.format("`%s`", name);
    }

    /**
     * 解析字段名
     *
     * @param fieldName
     * @return
     */
    public static String parseFieldName(String fieldName) {
        if (fieldName.startsWith("`") && fieldName.endsWith("`")) {
            return fieldName.substring(1, fieldName.length() - 1);
        }
        return fieldName;
    }

    /**
     * 包装表名
     *
     * @param name
     * @return
     */
    public static String wrapTableName(String name) {
        return String.format("`%s`", name);
    }

    /**
     * 解析表名
     *
     * @param tableName
     * @return
     */
    public static String parseTableName(String tableName) {
        if (tableName.startsWith("`") && tableName.endsWith("`")) {
            return tableName.substring(1, tableName.length() - 1);
        }
        return tableName;
    }

}
