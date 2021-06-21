# 云办管理平台
## 1. 项目简介
### 1.1 项目简介

​		本项目使用了 SpringBoot 等技术构建了一个云办公管理平台。



### 1.2 项目使用技术&插件&软件

+ Java 1.8版本（涉及一些Stream处理）+ Idea开发工具
+ Maven Jar包管理
+ Spring Security + SpringBoot + Mybatis Plus 数据库访问和控制器、逆向工程
+ Redis 用做缓存（管理列表菜单缓存）
+ MySQL 8 数据存储
+ Lombok 插件 （Java 实体类的 Setter和 Getter等）
+ Hutool 工具包（非空判断，登陆验证码生成等）

## 2. yeb-generator 模块

本模块用于初始化数据库，以及构建项目基本结构。

		1. 首先执行`resources/sql/yeb.sql` 中 的建表语句并初始化数据。
  		2. 然后根据 resources/doc/代码生成.md 文档操作，使用Mybatis-Plus的逆向工程，使用数据库生成实体类、Service接口以及Controller、Mapper等部分。

> 若已经生成了这些类或Xml文件，则此模块可不用执行。在生成对应的文件后，将java文件和xml文件粘贴到 yeb-server模块的对应位置即可。

## 3. yeb-server 模块

本模块是核心功能模块，实现了以下功能：

+ 配置并实现了 `SpringSecurity`的权限管理，核心思想是使用了JWT生成token。
+ 使用 Redis 缓存了登陆用户后的菜单列表。
+ 使用Swagger2生成了接口文档。（访问链接为：http://localhost/doc.html）
+ 实现了全局异常处理类，内容有待完善。
+ 实现了Controller同一返回结构类型 `ResponseBean`。
+ 完整的登陆模块实现，验证码使用了`Hutool`中的四则运算验证。
+ ...未完待续



## 4. yeb-util 模块

本模块是全局工具类。目前实现了：

+ StringUtil，继承了Hutool中的StrUtil类，并增加自己的方法。
+ ...未完待续





## 5. 部分截图

### 5.1 Swagger2的测试界面

![](img\pic1.png)





