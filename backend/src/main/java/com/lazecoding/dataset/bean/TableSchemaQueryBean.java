package com.lazecoding.dataset.bean;

import com.lazecoding.dataset.core.schema.TableSchema;

import java.io.Serializable;

/**
 * TableSchemaQueryBean
 *
 * @author lazecoding
 */
public class TableSchemaQueryBean implements Serializable {

    private static final long serialVersionUID = -1;

    /**
     * 项目
     */
    private String projectId;

    /**
     * Id
     */
    private String tableSchemaId;

    /**
     * tableSchema 对象
     */
    private TableSchema tableSchema;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTableSchemaId() {
        return tableSchemaId;
    }

    public void setTableSchemaId(String tableSchemaId) {
        this.tableSchemaId = tableSchemaId;
    }

    public TableSchema getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(TableSchema tableSchema) {
        this.tableSchema = tableSchema;
    }
}
