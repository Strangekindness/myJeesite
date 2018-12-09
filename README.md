# 微服务项目

微服务管理平台

## 关键技术点
- spring mvc
- mybatis
- shiro + jwt
- cors
- wechat

## 初始化项目
- `git clone https://gitee.com/plusdo/micro.git`
- 进入`bin`目录，执行`eclise.bat`
- 进入eclise配置，右键项目->Maven-> update Maven
- 配置`jeesite.properties`


## rest

### 认证（jwt）

调用地址/rest/login(post)，参数`{"username":"admin","password":"000000"}`，返回`token`

- LoginApiController 登录接口

### 调用api

请求API需要先登录，获得Token

- 调用具体的api，在Header中将登录返回的token写入Authorization字段中，所有的api均返回ResponseResult对象，该对象中有code和message。参考事例`RestDemoController`


#### 分页
和原来的分页方式一直，返回
参考`http://localhost:8181/micro/wechat/rest/crm/sales/order/list`，  `SalesApiController -> salesOrderList`
```
Page<SalesOrder> orderList = salesOrderService.findPage(new RestfulPage<SalesOrder>(request, response), new SalesOrder()); 
		return ResponseResult.success(orderList);
```
- 默认返回第一页
- 其他页面传入   `pageNo=页面&pageSize=页面大小&orderBy=排序字段`

``` 返回结果
{
    "code": 0,
    "message": "成功",
    "data": {
        "pageNo": 1,    //当前页码
        "pageSize": 30, //页面大小
        "count": 40,    //总记录数
        "first": 1,     //首页索引
        "last": 2,      //尾页索引
        "prev": 1,      //上一页索引
        "next": 2,      // 下一页索引
        "firstPage": true, //是否是第一页
        "lastPage": false, //是否是最后一页
        "list": [
			// 数据列表            
        ],
        "firstResult": 0,
        "maxResults": 30,
        "html": "",
        "totalPage": 2  // 总页数
    }
}
```

## 系统设计

### 数据库设计规范
- 业务表使用`crm`开头
- 所有表名和字段名均为小写
- 所有字段用`_`下划线连接
- 所有金额字段使用decimal(16,4)
- 所有表均使用相同的系统字段
- 所有表关联字段均以`_id`结尾
- 所有字典字段均以`_code`结尾
```
       `sort`            INT comment '排序',
       `status`          INT comment '启用状态 0:不可用 1:可用',
       `create_by`       VARCHAR(64) comment '创建者',
       `create_date`     DATETIME comment '创建时间',
       `update_by`       VARCHAR(64) comment '更新者',
       `update_date`     DATETIME comment '更新时间',
       `remarks`         VARCHAR(255) comment '备注信息',
       `del_flag`        VARCHAR(1) comment '删除标识'
```
- 所有的操作使用逻辑删除，即`del_flag`为1。

### 接口规范
- 接口使用`/rest`开头


### 编码规范
- 所有的接口写到`api`包下。

## 数据库脚本
数据库脚本在[upgrade](upgrade/README.md)目录中。