package com.lazecoding.dataset.core.schema;

import java.io.Serializable;
import java.util.List;

/**
 * 表概要
 *
 * @author lazecoding
 */
public class TableSchema implements Serializable {

    private static final long serialVersionUID = -1;

    /**
     * 库名
     */
    private String dbName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 模拟数据条数
     */
    private Integer mockNum;

    /**
     * 列信息列表
     */
    private List<Field> fieldList;

    /**
     * 列信息
     */
    public static class Field {
        /**
         * 字段名
         */
        private String fieldName;

        /**
         * 字段类型(完整)
         */
        private String fieldTypeAll;

        /**
         * 字段类型
         */
        private String fieldType;

        /**
         * 文本类型字段长度(-1 表示无约束)
         */
        private int fieldLength = -1;

        /**
         * 默认值
         */
        private String defaultValue;

        /**
         * 是否非空
         */
        private boolean notNull;

        /**
         * 注释（字段中文名）
         */
        private String comment;

        /**
         * 是否为主键
         */
        private boolean primaryKey;

        /**
         * 是否自增
         */
        private boolean autoIncrement;

        /**
         * 模拟类型  MockTypeEnum.X.value {@link com.lazecoding.dataset.core.enums.MockTypeEnum}
         */
        private String mockType;

        /**
         * 模拟参数
         *
         * mockType:none  mockParams:
         * mockType:increase  mockParams:起始值
         * mockType:fixed  mockParams:定值
         * mockType:random  mockParams:MockParamsRandomTypeEnum.X.value {@link com.lazecoding.dataset.core.enums.MockParamsRandomTypeEnum}
         * mockType:rule  mockParams:正则
         * mockType:dict  mockParams:字典Id
         */
        private String mockParams;

        /**
         * 附加条件
         */
        private String onUpdate;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldTypeAll() {
            return fieldTypeAll;
        }

        public void setFieldTypeAll(String fieldType) {
            this.fieldTypeAll = fieldType;
        }

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public boolean isNotNull() {
            return notNull;
        }

        public void setNotNull(boolean notNull) {
            this.notNull = notNull;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public boolean isPrimaryKey() {
            return primaryKey;
        }

        public void setPrimaryKey(boolean primaryKey) {
            this.primaryKey = primaryKey;
        }

        public boolean isAutoIncrement() {
            return autoIncrement;
        }

        public void setAutoIncrement(boolean autoIncrement) {
            this.autoIncrement = autoIncrement;
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

        public String getOnUpdate() {
            return onUpdate;
        }

        public void setOnUpdate(String onUpdate) {
            this.onUpdate = onUpdate;
        }

        public int getFieldLength() {
            return fieldLength;
        }

        public void setFieldLength(int fieldLength) {
            this.fieldLength = fieldLength;
        }
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public Integer getMockNum() {
        return mockNum;
    }

    public void setMockNum(Integer mockNum) {
        this.mockNum = mockNum;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }
}
