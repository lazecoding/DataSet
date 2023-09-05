package com.lazecoding.dataset.core.schema;

import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLPrimaryKey;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlCreateTableParser;
import com.alibaba.excel.EasyExcel;
import com.lazecoding.dataset.common.constans.ResponseCode;
import com.lazecoding.dataset.common.exceptions.BusinessException;
import com.lazecoding.dataset.core.enums.FieldTypeEnum;
import com.lazecoding.dataset.core.enums.MockTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 表概要解析器
 *
 * @author lazecoding
 */
@Component
public class TableSchemaParser {

    /**
     * 日期格式
     */
    private static final String[] DATE_PATTERNS = {"yyyy-MM-dd", "yyyy年MM月dd日", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};

    /**
     * 根据建表 SQL 构建
     *
     * @param sql 建表 SQL
     * @return 生成的 TableSchema
     */
    public static TableSchema parserFromSql(String sql) {
        if (StringUtils.isBlank(sql)) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        try {
            // 解析 SQL
            MySqlCreateTableParser parser = new MySqlCreateTableParser(sql);
            SQLCreateTableStatement sqlCreateTableStatement = parser.parseCreateTable();
            TableSchema tableSchema = new TableSchema();
            tableSchema.setDbName(sqlCreateTableStatement.getSchema());
            tableSchema.setTableName(TableSchemaUtils.parseTableName(sqlCreateTableStatement.getTableName()));
            String tableComment = null;
            if (sqlCreateTableStatement.getComment() != null) {
                tableComment = sqlCreateTableStatement.getComment().toString();
                if (tableComment.length() > 2) {
                    tableComment = tableComment.substring(1, tableComment.length() - 1);
                }
            }
            tableSchema.setTableComment(tableComment);
            List<TableSchema.Field> fieldList = new ArrayList<>();
            // 解析列
            for (SQLTableElement sqlTableElement : sqlCreateTableStatement.getTableElementList()) {
                // 主键约束
                if (sqlTableElement instanceof SQLPrimaryKey) {
                    SQLPrimaryKey sqlPrimaryKey = (SQLPrimaryKey) sqlTableElement;
                    String primaryFieldName = TableSchemaUtils.parseFieldName(sqlPrimaryKey.getColumns().get(0).toString());
                    fieldList.forEach(field -> {
                        if (field.getFieldName().equals(primaryFieldName)) {
                            field.setPrimaryKey(true);
                        }
                    });
                } else if (sqlTableElement instanceof SQLColumnDefinition) {
                    // 列
                    SQLColumnDefinition columnDefinition = (SQLColumnDefinition) sqlTableElement;
                    TableSchema.Field field = new TableSchema.Field();
                    field.setFieldName(TableSchemaUtils.parseFieldName(columnDefinition.getNameAsString()));
                    SQLDataType sqlDataType = columnDefinition.getDataType();
                    String fieldTypeFull = sqlDataType.toString();
                    field.setFieldTypeAll(fieldTypeFull);
                    String fieldType = sqlDataType.getName();
                    field.setFieldType(fieldType);
                    // 解析文本类型并设置长度
                    if (fieldType.equals(FieldTypeEnum.CHAR.getValue())
                            || fieldType.equals(FieldTypeEnum.VARCHAR.getValue())) {
                        int fieldLength = Integer.parseInt(fieldTypeFull.replace(fieldType, "").replace("(", "").replace(")", ""));
                        field.setFieldLength(fieldLength);
                    }
                    String defaultValue = null;
                    if (columnDefinition.getDefaultExpr() != null) {
                        defaultValue = columnDefinition.getDefaultExpr().toString();
                    }
                    field.setDefaultValue(defaultValue);
                    field.setNotNull(columnDefinition.containsNotNullConstaint());
                    String comment = null;
                    if (columnDefinition.getComment() != null) {
                        comment = columnDefinition.getComment().toString();
                        if (comment.length() > 2) {
                            comment = comment.substring(1, comment.length() - 1);
                        }
                    }
                    field.setComment(comment);
                    field.setPrimaryKey(columnDefinition.isPrimaryKey());
                    field.setAutoIncrement(columnDefinition.isAutoIncrement());
                    String onUpdate = null;
                    if (columnDefinition.getOnUpdate() != null) {
                        onUpdate = columnDefinition.getOnUpdate().toString();
                    }
                    field.setOnUpdate(onUpdate);

                    // 默认生成规则
                    if (columnDefinition.containsNotNullConstaint() && !columnDefinition.isAutoIncrement()
                            && StringUtils.isBlank(defaultValue)) {
                        // 必填 & 不是自增 & 没默认值
                        FieldTypeEnum fieldTypeEnum = Optional.ofNullable(FieldTypeEnum.getEnumByValue(fieldType)).orElse(FieldTypeEnum.TEXT);
                        // 获取
                        field.setMockType(fieldTypeEnum.getDefaultMockType().getValue());
                    } else {
                        field.setMockType(MockTypeEnum.NONE.getValue());
                    }

                    fieldList.add(field);
                }
            }
            tableSchema.setFieldList(fieldList);
            return tableSchema;
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "请确认 SQL 语句正确");
        }
    }

    /**
     * 推断字段类型
     *
     * @param value
     * @return
     */
    public static String deduceFieldTypeByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return FieldTypeEnum.TEXT.getValue();
        }
        // 布尔
        if ("false".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value)) {
            return FieldTypeEnum.TINYINT.getValue();
        }
        // 整数
        if (StringUtils.isNumeric(value)) {
            long number = Long.parseLong(value);
            if (number > Integer.MAX_VALUE) {
                return FieldTypeEnum.BIGINT.getValue();
            }
            return FieldTypeEnum.INT.getValue();
        }
        // 小数
        if (isDouble(value)) {
            return FieldTypeEnum.DOUBLE.getValue();
        }
        // 日期
        if (isDate(value)) {
            return FieldTypeEnum.DATETIME.getValue();
        }
        return FieldTypeEnum.TEXT.getValue();
    }

    /**
     * 判断字符串是不是 double 型
     *
     * @param str
     * @return
     */
    private static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 判断是否为日期
     *
     * @param str
     * @return
     */
    private static boolean isDate(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        try {
            DateUtils.parseDate(str, DATE_PATTERNS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取字段默认类型
     *
     * @param fieldName
     * @return
     */
    private static TableSchema.Field getDefaultField(String fieldName) {
        final TableSchema.Field field = new TableSchema.Field();
        field.setFieldName(fieldName);
        field.setFieldTypeAll("text");
        field.setFieldType("text");
        field.setDefaultValue("");
        field.setNotNull(false);
        field.setComment(fieldName);
        field.setPrimaryKey(false);
        field.setAutoIncrement(false);
        field.setMockType("");
        field.setMockParams("");
        field.setOnUpdate("");
        return field;
    }

}

