package com.lazecoding.dataset.common.util;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地数据存储
 */
public class LocalDataUtil {

    private LocalDataUtil() {
    }

    /**
     * project Id 占位符
     */
    private static final String PROJECT_ID_TAG = "{projectId}";

    /**
     * 文件路径
     */
    private static final String LOCAL_DATA_PATH = "./../data/";

    /**
     * HttpRequest
     */
    private static final String HTTP_REQUEST_DATA = LOCAL_DATA_PATH + PROJECT_ID_TAG + "/mock/http_request/";

    /**
     * TableSchema
     */
    private static final String TABLE_SCHEMA_DATA = LOCAL_DATA_PATH + PROJECT_ID_TAG + "/mock/table_schema/";

    /**
     * 字典
     */
    private static final String DICT_DATA = LOCAL_DATA_PATH + PROJECT_ID_TAG + "/source/dict/";

    /**
     * 正则
     */
    private static final String RULE_DATA = LOCAL_DATA_PATH + PROJECT_ID_TAG + "/source/rule/";

    /**
     * 结果路径
     */
    private static final String RESULT_PATH = "./../result/";

    /**
     * TableSchema Result
     */
    private static final String TABLE_SCHEMA_RESULT = RESULT_PATH + PROJECT_ID_TAG + "/table_schema/";

    /**
     * 获取环境中项目
     */
    public static List<String> findProjects() {
        List<String> projects = new ArrayList<>();
        if (!LocalFileUtil.createIfNil(LOCAL_DATA_PATH)) {
            return projects;
        }
        List<File> projectDirs = LocalFileUtil.listDirs(LOCAL_DATA_PATH);
        if (!CollectionUtils.isEmpty(projectDirs)) {
            for (File file : projectDirs) {
                projects.add(file.getName());
            }
        }
        return projects;
    }

    /**
     * 创建项目
     */
    public static boolean createProject(String projectId) {
        if (!LocalFileUtil.createIfNil(LOCAL_DATA_PATH)) {
            return false;
        }
        if (!StringUtils.hasText(projectId)) {
            return false;
        }
        File file = new File(LOCAL_DATA_PATH + projectId + "/");
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }

    /**
     * 创建项目
     */
    public static boolean deleteProject(String projectId) {
        return LocalFileUtil.delete(LOCAL_DATA_PATH + projectId + "/");
    }



}
