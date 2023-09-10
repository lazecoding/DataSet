package com.lazecoding.dataset.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lazecoding.dataset.core.enums.MockParamsRandomTypeEnum;
import com.lazecoding.dataset.core.enums.MockTypeEnum;
import com.lazecoding.dataset.core.http.HttpBodyTypeEnum;
import com.lazecoding.dataset.core.http.HttpMethodEnum;
import com.lazecoding.dataset.core.http.HttpRequest;
import com.lazecoding.dataset.core.producer.DataProducer;
import com.lazecoding.dataset.core.producer.HttpProducer;
import com.lazecoding.dataset.core.schema.TableSchema;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpRequestMain {

    public static void main(String[] args) {

        Gson gson = new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .setPrettyPrinting()// 调教格式
                .disableHtmlEscaping() //默认是GSON把HTML 转义的
                .create();

        /*HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl("http://www.baidu.com");
        httpRequest.setMethod(HttpMethodEnum.GET.getValue());
        httpRequest.setHeaders(Collections.singletonList(new HttpRequest.Header("Content-Type", "application/json")));
        List<HttpRequest.Param> params = new ArrayList<>();
        params.add(new HttpRequest.Param("userName", MockTypeEnum.RANDOM.getValue(), MockParamsRandomTypeEnum.NAME.getValue()));
        params.add(new HttpRequest.Param("cite", MockTypeEnum.RANDOM.getValue(), MockParamsRandomTypeEnum.CITY.getValue()));
        params.add(new HttpRequest.Param("mail", MockTypeEnum.RANDOM.getValue(), MockParamsRandomTypeEnum.EMAIL.getValue()));
        params.add(new HttpRequest.Param("ip", MockTypeEnum.RANDOM.getValue(), MockParamsRandomTypeEnum.IP.getValue()));
        httpRequest.setParams(params);
        System.out.println(gson.toJson(httpRequest));*/


        String httpRequestExample = "";

        try {
            File file = ResourceUtils.getFile("classpath:private/example/HttpRequestExample.json");
            httpRequestExample =  FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // System.out.println(httpRequestExample);

        HttpRequest httpRequest = gson.fromJson(httpRequestExample, HttpRequest.class);
        System.out.println(httpRequest);



        // 3. 通过 tableSchema 生成模拟数据
        httpRequest.setMockNum(3);
        List<Map<String, Object>> dataList = DataProducer.generateData(httpRequest);
        System.out.println(dataList);
        // 4. 处理数据
        HttpProducer.doExecute(httpRequest, dataList);


    }
}
