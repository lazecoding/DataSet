package com.lazecoding.dataset.core.generator;


import com.lazecoding.dataset.core.http.HttpRequest;
import com.lazecoding.dataset.core.schema.TableSchema;

import java.util.List;

/**
 * 数据生成器
 *
 * @author lazecoding
 */
public interface DataGenerator {

    /**
     * 生成
     *
     * @param field 字段信息
     * @param rowNum 行数
     * @return 生成的数据列表
     */
    List<String> doGenerate(TableSchema.Field field, int rowNum);



    /**
     * 生成
     *
     * @param param 字段信息
     * @param rowNum 行数
     * @return 生成的数据列表
     */
    List<String> doGenerate(HttpRequest.Param param, int rowNum);


}
