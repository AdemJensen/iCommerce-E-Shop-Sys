# iCommerce 在线电子商务系统

## 简介

iCommerce 是一款简单的在线商务系统，可以提供一些基础的的 B2C 购物服务，满足一些个人或者微型企业的电子商务建设需求。

iCommerce系统拥有以下功能特性：
- 可定制的主页和商品简介
- 可通过后台进行管理的商品和商品类别
- 完整的购物车系统、订单系统
- 退货系统、确认收货系统（退货系统完善中）
- 接入在线客户服务系统（例如Tidio）

策划中的功能：
- 顾客注册邮件确认
- 顾客管理系统
- 管理员管理系统
- 顾客充值系统 / 在线支付接口接入
- 订单详情页面
- 图床系统

声明：本系统最初是用于满足《Web技术》课程设计中所要求的“电商系统的制作”，系统架构设计和所使用的安全技术可能相对较为陈旧，由于使用本系统导致的任何直接或间接损失均与作者无关。

## 部署

您可以在 Releases 界面下载本系统的打包部署版。

本系统在开发时使用的是 Tomcat + MySQL 的组合，因此您需要先在您的计算机上安装 Tomcat 服务和 MySQL 服务。

安装完成后，将下载得到的压缩包解压，并放到 Tomcat 目录下的 `webapps` 文件夹中，启动 Tomcat 服务器。

接下来，您可以通过以下两种方式初始化您的系统：

**方法 1**

启动服务器后，使用浏览器访问`localhost:8080/<文件夹名称>/init`，跟随向导填好数据库的用户名、密码，并根据自己的需要来设置数据表名称。设置完成后单击右侧“保存”按钮执行初始化脚本，脚本完成的工作有：
- 生成`config.json`文件
- 完成数据库的创建以及数据表的创建
- 创建默认管理员账号（用户名为`root`，密码为`123456`）。
（此用户名和密码，包括顾客的用户名和密码都暂时无法修改，当前版本未制作这些界面及响应器。如果您要修改这些密码，可以先使用 16 位 MD5 生成器将密码加密（得到32位字符串），然后填写到数据库指定位置。）

**方法 2**

将项目中的`config.default.json`使用编辑工具打开，并在里面填写好对应的数据，然后修改名称为`config.json`并放在原文件夹中。

`config.default.json`的文件结构如下所示：

```JSON
{
  // 基本设置
  "SYSTEM_NAME": "iCommerce",   // 系统名，会显示在各个地方
  "USE_MODIFIED_INDEX": false,  // 是否使用自定义的页面
  "MODIFIED_INDEX_FILE": "",    // 自定义页面的文件名，文件放在custom文件夹下
  "CHAT_SYSTEM": "",            // 聊天系统代码，可以看Tidio系统
  // 数据库设置
  "DATABASE_ADDR": "127.0.0.1", // 数据库地址
  "DATABASE_PORT": 3306,        // 数据库端口
  "DATABASE_USERNAME": "root",  // 数据库用户名
  "DATABASE_PASSWORD": "PASSWD",// 数据库密码
  "DATABASE_SCHEMA": "iCommerce",// 数据库名称
  "DATABASE_USE_UNICODE": true, // 是否使用Unicode字符集连接
  "DATABASE_ENCODING": "UTF-8", // 连接字符集
  "DATABASE_TIMEZONE": "",      // 连接使用时区，建议留空
  "DATABASE_USE_SSL": false,    // 是否使用SSL连接
  "PUBLIC_KEY_RETRIEVAL": true, // 是否允许公钥检索，这里一般是true
  // 数据表名称
  "DATABASE_TABLE_ADMIN": "admin",  // 管理员用户数据表
  "DATABASE_TABLE_CART": "cart",    // 购物车数据表
  "DATABASE_TABLE_ITEM": "item",    // 物品数据表
  "DATABASE_TABLE_ITEM_TYPE": "item_type",  // 物品类型数据表
  "DATABASE_TABLE_ITEM_TYPE_BELONG": "item_type_belong",  // 物品类型关系数据表
  "DATABASE_TABLE_ORDER": "order",  // 订单数据表
  "DATABASE_TABLE_ORDER_BELONG": "order_belong",  // 订单关系数据表
  "DATABASE_TABLE_SHIPPING": "shipping",  // 运送信息数据表
  "DATABASE_TABLE_USER": "user"   // 用户数据表
}
```

接下来，您需要将 `init.sql` 文件内的各种操作进行修改，使得其与`config.json`中的数据相符，之后在您的终端里执行它，或者使用数据库管理工具导入它。

之后您可以访问`localhost:8080/<文件夹名称>/admin/sys`来确认刚才的操作是否生效。如果没有任何错误，那么恭喜您，您的设置成功完成了。

完成系统初始化后，您可以使用默认的管理员用户名和密码登录到系统。

> 注意，管理员登陆界面在页面底端有一个`管理员登录`的链接，在那里单击才能进入管理员登陆界面。右上角只能进入普通用户的登陆界面。

## 系统使用指南

### 图片相关

本项目中目录`/assets/img/picture-bed`目录是为图床系统准备的，但是目前图床系统暂时未到位，只能读取不能写。

您需要用`数字 + ".png"`的格式来进行命名，例如`5.png`，这样可以被系统识别。其他格式的图片也可以直接修改扩展名为`png`，不会造成影响。

### 编写首页和商品详情页面

一般来讲，我们要使用JSP技术来编写页面。

页面上的其他元素可以按照`HTML 5`的标准进行书写。

要使用图片，请先在文件开头加入内容
```java
<%@ page import="top.chorg.iCommerce.function.Resource" %>
```
然后在图片URL处填写
```java
<%= Resource.getPBPic(request, [PBID]) %>
```
其中`[PBID]`就是你放在图床目录下的文件数字。

### 产品封面和类型封面

每个类型和产品都有一个封面。同样，需要您先放在图床目录下，然后在对应的设置面板填写即可。

另外，它们还有默认的图片，如果您指定的图片无效的话，将会显示默认图片。

## 开发

本系统使用`JDK 13`进行开发，未使用后端框架，前端框架使用的是 `Bootstrap v4.4.1`。

使用了`Google Gson`库作为 Json 数据格式支持，使用`MySQL Connector 8.0.15`来进行数据库支持。

本项目使用`IntelliJ IDEA Ultimate`进行开发，如果您恰好使用这款IDE，您可以直接使用IDEA来打开这个项目。

## 其他

本项目`Master`分支是面向全体 GitHub 成员的，`Demo`分支中的代码会与`Master`同步，但是里面会添加一些面向展示的内容。
