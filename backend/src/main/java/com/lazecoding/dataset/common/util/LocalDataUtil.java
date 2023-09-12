package com.lazecoding.dataset.common.util;

import com.google.gson.reflect.TypeToken;
import com.lazecoding.dataset.common.exceptions.UnCreatedFileException;
import com.lazecoding.dataset.core.http.HttpRequest;
import com.lazecoding.dataset.core.schema.TableSchema;
import com.lazecoding.dataset.model.DictItem;
import com.lazecoding.dataset.model.Rule;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地数据存储
 */
public class LocalDataUtil {

    private static final Logger logger = LoggerFactory.getLogger(LocalDataUtil.class);

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
     * 文件项目路径
     */
    private static final String LOCAL_DATA_PROJECT_PATH = "./../data/" + PROJECT_ID_TAG + "/";

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
     * 正则文件固定文件名
     */
    private static final String RULE_DATA_FILE_NAME = "rule.json";

    /**
     * 结果路径
     */
    private static final String RESULT_PATH = "./../result/";

    /**
     * 结果路径
     */
    private static final String RESULT_PROJECT_PATH = "./../result/" + PROJECT_ID_TAG + "/";


    /**
     * TableSchema Result
     */
    private static final String TABLE_SCHEMA_RESULT = RESULT_PATH + PROJECT_ID_TAG + "/table_schema/";

    /**
     * 项目数据目录
     */
    private static String getProjectDataPath(String projectId) {
        return LOCAL_DATA_PROJECT_PATH.replace(PROJECT_ID_TAG, projectId);
    }

    /**
     * 项目词典数据
     */
    private static String getProjectDictPath(String projectId) {
        return DICT_DATA.replace(PROJECT_ID_TAG, projectId);
    }

    /**
     * 项目词典数据
     */
    private static String getProjectDictPath(String projectId, String dictId) {
        return (DICT_DATA.replace(PROJECT_ID_TAG, projectId)) + dictId + ".json";
    }


    /**
     * 项目规则数据
     */
    private static String getProjectRulePath(String projectId) {
        return RULE_DATA.replace(PROJECT_ID_TAG, projectId);
    }


    /**
     * 项目结果目录
     */
    private static String getProjectResultPath(String projectId) {
        return RESULT_PROJECT_PATH.replace(PROJECT_ID_TAG, projectId);
    }

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
        File file = new File(getProjectDataPath(projectId));
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }

    /**
     * 删除项目
     */
    public static boolean deleteProject(String projectId) {
        return LocalFileUtil.forceDelete(getProjectDataPath(projectId));
    }

    /**
     * 检查项目是否存在
     * @param projectId
     */
    public static void checkProjectExist(String projectId) {
        File dir = new File(getProjectDataPath(projectId));
        if (!dir.exists()) {
            throw new UnCreatedFileException("项目不存在");
        }
    }

    /**
     * 获取环境中规则
     */
    public static Map<String, Rule> findRules(String projectId) {
        // 先判断项目有没有创建
        checkProjectExist(projectId);

        Map<String, Rule> ruleMap = new HashMap<>();
        if (!LocalFileUtil.createIfNil(getProjectRulePath(projectId))) {
            return ruleMap;
        }
        String ruleStr = "";
        File file = new File(getProjectRulePath(projectId) + RULE_DATA_FILE_NAME);
        if (file.exists()) {
            try {
                ruleStr = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            } catch (Exception e) {
                logger.error("读取文件异常", e);
            }
        } else {
            try {
                file.createNewFile();
            } catch (Exception e) {
                logger.error("创建文件异常", e);
            }
        }
        if (StringUtils.hasText(ruleStr)) {
            ruleMap = JsonUtil.GSON.fromJson(ruleStr, new TypeToken<Map<String, Rule>>() {
            }.getType());
        }
        return ruleMap;
    }


    /**
     * 写入规则
     */
    public static boolean writeRule(String projectId, String ruleJson) {
        checkProjectExist(projectId);
        return LocalFileUtil.writeStringToFile(getProjectRulePath(projectId) + RULE_DATA_FILE_NAME, ruleJson);
    }

    /**
     * 清空规则
     */
    public static boolean dropRule(String projectId) {
        return LocalFileUtil.delete(getProjectRulePath(projectId) + RULE_DATA_FILE_NAME);
    }

    /**
     * 获取环境中词典
     */
    public static List<String> findDicts(String projectId) {
        checkProjectExist(projectId);
        List<String> dicts = new ArrayList<>();
        if (!LocalFileUtil.createIfNil(getProjectDictPath(projectId))) {
            return dicts;
        }
        List<File> dictFiles = LocalFileUtil.list(getProjectDictPath(projectId));
        if (!CollectionUtils.isEmpty(dictFiles)) {
            for (File file : dictFiles) {
                dicts.add(file.getName().replace(".json",""));
            }
        }
        return dicts;
    }

    /**
     * 创建词典
     */
    public static boolean createDict(String projectId, String dictId) {
        checkProjectExist(projectId);
        if (!LocalFileUtil.createIfNil(getProjectDictPath(projectId))) {
            return false;
        }
        if (!StringUtils.hasText(projectId)) {
            return false;
        }
        File file = new File(getProjectDictPath(projectId, dictId));
        if (file.exists()) {
            return true;
        }
        boolean isSuccess = false;
        try {
            isSuccess = file.createNewFile();
        } catch (Exception e) {
            isSuccess = false;
            logger.error("创建文件异常", e);
        }
        return isSuccess;
    }

    /**
     * 删除词典
     */
    public static boolean deleteDict(String projectId, String dictId) {
        return LocalFileUtil.delete(getProjectDictPath(projectId, dictId));
    }


    /**
     * 清空词典
     */
    public static boolean dropDict(String projectId) {
        return LocalFileUtil.forceDelete(getProjectDictPath(projectId));
    }

    /**
     * 获取环境中词条
     */
    public static Map<String, DictItem> findDictItems(String projectId, String dictId) {
        checkProjectExist(projectId);
        Map<String, DictItem> dictItemMap = new HashMap<>();
        if (!LocalFileUtil.createIfNil(getProjectDictPath(projectId))) {
            return dictItemMap;
        }
        String dictItemJson = "";
        File file = new File(getProjectDictPath(projectId, dictId));
        if (!file.exists()) {
            logger.debug("词典:[{}] 不存在", dictId);
            throw new UnCreatedFileException("词典不存在");
        }
        try {
            dictItemJson = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("读取文件异常", e);
        }
        if (StringUtils.hasText(dictItemJson)) {
            dictItemMap = JsonUtil.GSON.fromJson(dictItemJson, new TypeToken<Map<String, DictItem>>() {
            }.getType());
        }
        return dictItemMap;
    }

    /**
     * 写入词典
     */
    public static boolean writeDict(String projectId, String dictId, String dictItemJson) {
        checkProjectExist(projectId);
        return LocalFileUtil.writeStringToFile(getProjectDictPath(projectId, dictId), dictItemJson);
    }

    /**
     * 清空词条
     */
    public static boolean dropDictItem(String projectId, String dictId) {
        return LocalFileUtil.delete(getProjectDictPath(projectId, dictId));
    }



    /**
     * 项目 TableSchema 路径
     */
    private static String getProjectTableSchemaFile(String projectId) {
        // id.json
        return TABLE_SCHEMA_DATA.replace(PROJECT_ID_TAG, projectId);
    }

    /**
     * 项目 TableSchema 路径
     */
    private static String getProjectTableSchemaFile(String projectId, String tableSchemaId) {
        // id.json
        return (TABLE_SCHEMA_DATA.replace(PROJECT_ID_TAG, projectId)) + tableSchemaId + ".json";
    }


    /**
     * 获取项目中的 TableSchema
     */
    public static List<String> findTableSchemas(String projectId) {
        checkProjectExist(projectId);
        List<String> tableSchemas = new ArrayList<>();
        if (!LocalFileUtil.createIfNil(getProjectTableSchemaFile(projectId))) {
            return tableSchemas;
        }
        List<File> findTableSchemaFiles = LocalFileUtil.list(getProjectTableSchemaFile(projectId));
        if (!CollectionUtils.isEmpty(findTableSchemaFiles)) {
            for (File file : findTableSchemaFiles) {
                tableSchemas.add(file.getName().replace(".json",""));
            }
        }
        return tableSchemas;
    }

    
    /**
     * 删除 TableSchema
     */
    public static boolean deleteTableSchema(String projectId, String tableSchemaId) {
        return LocalFileUtil.delete(getProjectTableSchemaFile(projectId, tableSchemaId));
    }


    /**
     * 清空 TableSchema
     */
    public static boolean dropTableSchema(String projectId) {
        return LocalFileUtil.forceDelete(getProjectTableSchemaFile(projectId));
    }

    /**
     * 写入 TableSchema
     */
    public static boolean writeTableSchema(String projectId, String tableSchemaId, String tableSchemaJson) {
        checkProjectExist(projectId);
        return LocalFileUtil.writeStringToFile(getProjectTableSchemaFile(projectId, tableSchemaId), tableSchemaJson);
    }


    /**
     * 获取 TableSchema
     */
    public static TableSchema findTableSchemaFile(String projectId, String tableSchemaId) {
        // 先判断项目有没有创建
        checkProjectExist(projectId);
        String tableSchemaJson = "";
        File file = new File(getProjectTableSchemaFile(projectId, tableSchemaId));
        if (!file.exists()) {
            return null;
        }
        try {
            tableSchemaJson = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("读取文件异常", e);
        }
        TableSchema tableSchema = null;
        if (StringUtils.hasText(tableSchemaJson)) {
            tableSchema = JsonUtil.GSON.fromJson(tableSchemaJson, new TypeToken<TableSchema>() {
            }.getType());
        }
        return tableSchema;
    }


    /**
     * 项目 HttpRequest 路径
     */
    private static String getProjectHttpRequestFile(String projectId) {
        // id.json
        return HTTP_REQUEST_DATA.replace(PROJECT_ID_TAG, projectId);
    }

    /**
     * 项目 HttpRequest 路径
     */
    private static String getProjectHttpRequestFile(String projectId, String httpRequestId) {
        // id.json
        return (HTTP_REQUEST_DATA.replace(PROJECT_ID_TAG, projectId)) + httpRequestId + ".json";
    }

    /**
     * 获取项目中的 HttpRequest
     */
    public static List<String> findHttpRequests(String projectId) {
        checkProjectExist(projectId);
        List<String> httpRequests = new ArrayList<>();
        if (!LocalFileUtil.createIfNil(getProjectHttpRequestFile(projectId))) {
            return httpRequests;
        }
        List<File> findHttpRequestsFiles = LocalFileUtil.list(getProjectHttpRequestFile(projectId));
        if (!CollectionUtils.isEmpty(findHttpRequestsFiles)) {
            for (File file : findHttpRequestsFiles) {
                httpRequests.add(file.getName().replace(".json",""));
            }
        }
        return httpRequests;
    }


    /**
     * 删除 HttpRequest
     */
    public static boolean deleteHttpRequest(String projectId, String httpRequestId) {
        return LocalFileUtil.delete(getProjectHttpRequestFile(projectId, httpRequestId));
    }


    /**
     * 清空 HttpRequest
     */
    public static boolean dropHttpRequest(String projectId) {
        return LocalFileUtil.forceDelete(getProjectHttpRequestFile(projectId));
    }

    /**
     * 写入 HttpRequest
     */
    public static boolean writeHttpRequest(String projectId, String httpRequestId, String httpRequestJson) {
        checkProjectExist(projectId);
        return LocalFileUtil.writeStringToFile(getProjectHttpRequestFile(projectId, httpRequestId), httpRequestJson);
    }


    /**
     * 获取 TableSchema
     */
    public static HttpRequest findHttpRequestFile(String projectId, String httpRequestId) {
        // 先判断项目有没有创建
        checkProjectExist(projectId);
        String httpRequestJson = "";
        File file = new File(getProjectHttpRequestFile(projectId, httpRequestId));
        if (!file.exists()) {
            return null;
        }
        try {
            httpRequestJson = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("读取文件异常", e);
        }
        HttpRequest httpRequest = null;
        if (StringUtils.hasText(httpRequestJson)) {
            httpRequest = JsonUtil.GSON.fromJson(httpRequestJson, new TypeToken<HttpRequest>() {
            }.getType());
        }
        return httpRequest;
    }


}
