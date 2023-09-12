# DataSet

用于生产模拟数据。

## 数据库
        
解析SQL/手工录入 --> TableSchema(Field) --> TableSchema 可设置生成数据规则细节 --> 根据设定规则生成设定数量新增数据 SQL。


## 接口

- 解析请求 URL 生成类似 PostMan 页面，区别在于 postman 中填值的区域用来设置生成数据规则
- 具体生成阶段数据落地方式：执行请求、生成 CURL 脚本。

## 结构

```C
-- bin
-- config
-- data
    -- mock
        -- <projectId>
            -- http_request
            -- table_schema
    -- source
        -- dict
        -- rule
-- result
    -- <projectId>
        -- table_schema
-- logs
```