package com.lazecoding.dataset.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lazecoding.dataset.common.mvc.ResultBean;
import com.lazecoding.dataset.core.producer.DataProducer;
import com.lazecoding.dataset.core.producer.SqlProducer;
import com.lazecoding.dataset.core.schema.TableSchema;
import com.lazecoding.dataset.core.schema.TableSchemaParser;
import com.mifmif.common.regex.Generex;
import java.util.List;
import java.util.Map;

public class CoreMain {

    public static void main(String[] args) {
        String sql = "create table attach\n" +
                "(\n" +
                "    iid       int    auto_increment not null,\n" +
                "    name     varchar(255) not null,\n" +
                "    filename varchar(255), " +
                "    trashed  smallint     default 0 not null,\n" +
                "    fileinfo varchar(255),\n" +
                "    typeid   int   default 0 not null,\n" +
                "    userid   varchar(10) default '00000' not null,\n" +
                "    filesize varchar(10) not null,\n" +
                "    filetype char(5) not null,\n" +
                "    ctime    char(20)  default '0000-00-00 00:00:00' not null,\n" +
                "    webId    int    default 1  not null,\n" +
                "    uuid  varchar(60),\n" +
                "    primary key (iid)\n" +
                ")engine=innodb default charset=utf8;";
        ResultBean resultBean = ResultBean.getInstance();
        // 1. 获得最初的 tableSchema
        TableSchema tableSchema = TableSchemaParser.parserFromSql(sql);
        String res = SqlProducer.buildCreateTableSql(tableSchema);
        resultBean.setValue(res);
        // System.out.println(resultBean);

        // TODO 2. 对 tableSchema 二次加工（用户行为）

        // 3. 通过 tableSchema 生成模拟数据
        tableSchema.setMockNum(3);
        List<Map<String, Object>> dataList = DataProducer.generateData(tableSchema);
        // 4. 使用模拟数据生成 SQL
        String insertSql = SqlProducer.buildInsertSql(tableSchema, dataList);
        System.out.println(insertSql);

        Gson gson = new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .setPrettyPrinting()// 调教格式
                .disableHtmlEscaping() //默认是GSON把HTML 转义的
                .create();
        String json = gson.toJson(tableSchema);
        System.out.println(json);

        // 手机号
        String mockParams = "(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}";
        // mockParams = "[0-3]([a-c]|[e-g]{1,2})";
        Generex generex = new Generex(mockParams);
        String randomStr = generex.random();
        System.out.println("正则随机数据：" + randomStr);

    }

}
