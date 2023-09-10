# 接口

## 常量

- 枚举：http://localhost:8859/constants/enums


## 项目

- 项目列表：http://localhost:8859/project/list

- 新增项目：http://localhost:8859/project/create?projectId=cms

- 删除项目：http://localhost:8859/project/delete?projectId=cms

## Mock

### TableSchema

- 获取 TableSchema 列表：

- 获取 TableSchema 属性：

- 根据 SQL 创建 TableSchema：

- 手工创建 TableSchema：

- 修改 TableSchema：

- 删除 TableSchema：

- 清空 TableSchema：

### HttpRequest

- 获取 HttpRequest 列表：

- 获取 TableSchema 属性：

- 创建 HttpRequest：

- 修改 HttpRequest：

- 删除 HttpRequest：

- 清空 HttpRequest：

## DataSource

### Rule

- 获取规则集合：http://localhost:8859/datasource/rule/list?projectId=okr

- 创建规则：http://localhost:8859/datasource/rule/add?projectId=okr&regex=123

- 修改规则：http://localhost:8859/datasource/rule/modify?projectId=okr&id=123&regex=000

- 删除规则：http://localhost:8859/datasource/rule/delete?projectId=okr&id=123

- 清空规则：http://localhost:8859/datasource/rule/drop?projectId=okr

### Dict

- 获取词典列表：http://localhost:8859/datasource/dict/list-dict?projectId=okr

- 创建词典：http://localhost:8859/datasource/dict/create-dict?projectId=okr&dictId=diss

- 删除词典：http://localhost:8859/datasource/dict/delete-dict?projectId=okr&dictId=diss

- 清空词典：http://localhost:8859/datasource/dict/drop-dict?projectId=okr

- 获取词条列表：http://localhost:8859/datasource/dict/list-item?projectId=okr&dictId=diss

- 新增词条：http://localhost:8859/datasource/dict/add-item?projectId=okr&dictId=diss&value=VVV

- 修改词条：http://localhost:8859/datasource/dict/modify-item?projectId=okr&dictId=diss&value=TTT&id=9

- 删除词条：http://localhost:8859/datasource/dict/delete-item?projectId=okr&dictId=diss&id=9

- 清空词条：http://localhost:8859/datasource/dict/drop-item?projectId=okr&dictId=diss