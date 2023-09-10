package com.lazecoding.dataset.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lazecoding.dataset.core.producer.DataProducer;
import com.lazecoding.dataset.core.producer.SqlProducer;
import com.lazecoding.dataset.core.schema.TableSchema;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * TableSchemaMain
 *
 * @author lazecoding
 */
public class TableSchemaMain {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .setPrettyPrinting()// 调教格式
                .disableHtmlEscaping() //默认是GSON把HTML 转义的
                .create();

        String tableSchemaExample = "";

        try {
            File file = ResourceUtils.getFile("classpath:private/example/TableSchemaExample.json");
            tableSchemaExample =  FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(tableSchemaExample);

        TableSchema tableSchema = gson.fromJson(tableSchemaExample, TableSchema.class);
        System.out.println(tableSchema);

        // TODO 2. 对 tableSchema 二次加工（用户行为）

        // 3. 通过 tableSchema 生成模拟数据
        tableSchema.setMockNum(3);
        List<Map<String, Object>> dataList = DataProducer.generateData(tableSchema);
        // 4. 使用模拟数据生成 SQL
        String insertSql = SqlProducer.buildInsertSql(tableSchema, dataList);
        System.out.println(insertSql);
    }

}
