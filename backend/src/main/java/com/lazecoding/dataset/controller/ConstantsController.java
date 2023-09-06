package com.lazecoding.dataset.controller;

import com.lazecoding.dataset.common.constans.ResponseCode;
import com.lazecoding.dataset.common.mvc.ResultBean;
import com.lazecoding.dataset.core.enums.FieldTypeEnum;
import com.lazecoding.dataset.core.enums.MockParamsRandomTypeEnum;
import com.lazecoding.dataset.core.enums.MockTypeEnum;
import com.lazecoding.dataset.core.http.HttpBodyTypeEnum;
import com.lazecoding.dataset.core.http.HttpMethodEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 常量
 *
 * @author lazecoding
 */
@Controller
@RequestMapping("constants")
public class ConstantsController {

    /**
     * 枚举，用于前端一次性初始化全局变量
     */
    @RequestMapping("enums")
    @ResponseBody
    public ResultBean findEnums() {
        ResultBean resultBean = ResultBean.getInstance();

        Map<String,Object> fieldTypeEnum = new HashMap<>(2);
        fieldTypeEnum.put("keys", Arrays.stream(FieldTypeEnum.values()).map(FieldTypeEnum::getValue).collect(Collectors.toList()));
        fieldTypeEnum.put("values", Arrays.stream(FieldTypeEnum.values()).collect(Collectors.toMap(FieldTypeEnum::getValue, FieldTypeEnum::getDesc)));
        resultBean.addData("fieldTypeEnum", fieldTypeEnum);

        Map<String,Object> mockTypeEnum = new HashMap<>(2);
        mockTypeEnum.put("keys", Arrays.stream(MockTypeEnum.values()).map(MockTypeEnum::getValue).collect(Collectors.toList()));
        mockTypeEnum.put("values", Arrays.stream(MockTypeEnum.values()).collect(Collectors.toMap(MockTypeEnum::getValue, MockTypeEnum::getDesc)));
        resultBean.addData("mockTypeEnum", mockTypeEnum);

        Map<String,Object> mockParamsRandomTypeEnum = new HashMap<>(2);
        mockParamsRandomTypeEnum.put("keys", Arrays.stream(MockParamsRandomTypeEnum.values()).map(MockParamsRandomTypeEnum::getValue).collect(Collectors.toList()));
        mockParamsRandomTypeEnum.put("values", Arrays.stream(MockParamsRandomTypeEnum.values()).collect(Collectors.toMap(MockParamsRandomTypeEnum::getValue, MockParamsRandomTypeEnum::getDesc)));
        resultBean.addData("mockParamsRandomTypeEnum", mockParamsRandomTypeEnum);

        Map<String,Object> httpMethodEnum = new HashMap<>(2);
        httpMethodEnum.put("keys", Arrays.stream(HttpMethodEnum.values()).map(HttpMethodEnum::getValue).collect(Collectors.toList()));
        httpMethodEnum.put("values", Arrays.stream(HttpMethodEnum.values()).collect(Collectors.toMap(HttpMethodEnum::getValue, HttpMethodEnum::getDesc)));
        resultBean.addData("httpMethodEnum", httpMethodEnum);

        Map<String,Object> httpBodyTypeEnum = new HashMap<>(2);
        httpBodyTypeEnum.put("keys", Arrays.stream(HttpBodyTypeEnum.values()).map(HttpBodyTypeEnum::getValue).collect(Collectors.toList()));
        httpBodyTypeEnum.put("values", Arrays.stream(HttpBodyTypeEnum.values()).collect(Collectors.toMap(HttpBodyTypeEnum::getValue, HttpBodyTypeEnum::getDesc)));
        resultBean.addData("httpBodyTypeEnum", httpBodyTypeEnum);

        resultBean.setSuccess(true);
        resultBean.setCode(ResponseCode.SUCCESS.getCode());
        return resultBean;
    }

    /**
     * 词库，用于前端一次性初始化全局变量
     */
    @RequestMapping("dict")
    @ResponseBody
    public ResultBean findDict() {
        ResultBean resultBean = ResultBean.getInstance();

        // TODO 词库

        // 词库类型：  词库唯一标识要求只能包含字母、数字、下划线，作为词库文件的名称。

        resultBean.setSuccess(true);
        resultBean.setCode(ResponseCode.SUCCESS.getCode());
        return resultBean;
    }


}
