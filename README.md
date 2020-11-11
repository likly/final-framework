# final-frameworks

[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)


## 概述

`final-frameworks`致力于提供简单、灵活且功能强大的`java`开发框架。



## 核心内容



### 增强的JSON

#### 日期

你定义包含日期类型的的Bean：

```java
@Data
public class DateBean {
    private Date date;
    private LocalDateTime localDateTime;
}
```

你得到序列化后的JSON：

```json
{
    "date":1605059845585,
    "dateFormat":"2020-11-11 09:57:25",
    "localDateTime":1605059845603,
    "localDateTimeFormat":"2020-11-11 09:57:25"
}
```

#### 枚举

你定义包含**枚举类型的Bean：

```java
@Data
static class EnumBean{
    private YN yn = YN.YES;
}
```

你得到序列化后的JSON：

```json
{
    "yn":1,
    "ynName":"YES",
    "ynDesc":"有效"
}
```

### 统一的结果集

你在`@RestController`中定义的方法：

```java
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello(String word) {
        return "hello " + word + "!";
    }
}
```

你在访问`/hello?word=final`时，得到的结果：

```json
{
    "status":0,
    "description":"success",
    "code":"0",
    "message":"success",
    "data":"hello final!",
    "trace":"7aba435f-69d2-4c44-a944-315107623a92",
    "timestamp":1605063263491,
    "duration":0.063,
    "address":"127.0.0.1:80",
    "locale":"en",
    "timeZone":"Asia/Shanghai",
    "success":true
}
```

### 简化的操作日志

通过在方法声明上添加`@MonitorAction`注解，即可快速加入操作日志。

```java
@RestController
public class HelloController {
    @RequestMapping("/hello")
    @MonitorAction("${'访问Hello ' + #word}")
    public String hello(String word) {
        return "hello " + word + "!";
    }
}
```

访问`/hello?word=finalframework`时，可以看到有如下输出：

```verilog
2020-11-11 11:14:09.237  INFO 65380 --- [           main] o.f.monitor.action.ActionLoggerListener  : ==> action handler: {"name":"访问Hello finalframework","type":0,"action":0,"level":3,"levelName":"INFO","levelDesc":"INFO","attributes":{},"trace":"885dee93-8e90-4e61-afa9-b2a3b3bcbf39","timestamp":1605064449179}
```

默认情况下，操作日志仅以日志格式输出到文件中，开发人员可根据需求实现`ActionListener`接口将日志实现持久化等自定义操作。

```java
@FunctionalInterface
public interface ActionListener {

    void onAction(@NonNull Action<?> action);

}
```

> `@MonitorAction`中的大部分属性都支持`SpEL`表达式。
>
> **Note**：老版本中以`{}`表示表达式，新版本中使用`${}`表示表达式。



## 集成技术

|      技术      |           说明           |                             官网                             |
| :------------: | :----------------------: | :----------------------------------------------------------: |
|     Lombok     |     简化对象封闭工具     | [https://github.com/rzwitserloot/lombok](https://github.com/rzwitserloot/lombok) |
|  Spring Boot   |   Spring容器及MVC框架    | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
|    Mybatis     |         ORM框架          | [https://mybatis.org/mybatis-3/zh/index.html](https://mybatis.org/mybatis-3/zh/index.html) |
|   PageHelper   |     Mybatis分页插件      | [https://github.com/pagehelper/Mybatis-PageHelper](https://github.com/pagehelper/Mybatis-PageHelper) |
|     Redis      |        分布式缓存        |            [https://redis.io/](https://redis.io/)            |
|     Dubbo      |      分布式RPC调用       | [http://dubbo.apache.org/zh-cn/](http://dubbo.apache.org/zh-cn/) |
| ShardingSphere |       分库分表组件       | [https://shardingsphere.apache.org/document/current/cn/overview/](https://shardingsphere.apache.org/document/current/cn/overview/) |
|   Zookeeper    | 分布式注册中心、配置中心 |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |
|                |                          |                                                              |

## 核心工程

### final-framework

`final-framework`用于封装常用的技术框架。

|                         artifactId                         |                        描述                        |
| :--------------------------------------------------------: | :------------------------------------------------: |
|                        `final-core`                        |                                                    |
|                     `final-annotation`                     |     定义基本的`Annotation`，为框架解析提供基础     |
|                      `final-context`                       |                                                    |
|    [`final-json`](final-framework/final-json/README.md)    |         定义统一的Json序列化与反序列化接口         |
|                       `final-redis`                        |                                                    |
|                       `final-cache`                        |               基于`AOP`的的缓存切面                |
| [`final-mybatis`](final-framework/final-mybatis/README.md) |      定义通用的`Mapper`，提供强大的`CRUD`功能      |
|     [`final-web`](final-framework/final-web/README.md)     |  封装`springMvc`，提供增强的拦截器、异常处理机制   |
|                        `final-aop`                         |                                                    |
|                        `final-data`                        |                                                    |
| [`final-monitor`](final-framework/final-monitor/README.md) | 提供丰富的切面监控，如**操作日志**，**链路追踪**等 |
|                                                            |                                                    |
|                                                            |                                                    |
|                                                            |                                                    |
|                                                            |                                                    |
|                                                            |                                                    |
|                                                            |                                                    |
|                                                            |                                                    |
|                                                            |                                                    |



### final-auto

`final-auto`基于`APT（注解处理器）`技术，自动生成常用的配置文件或模板代码，提高开发效率。

|       artifactId        |                             描述                             |
| :---------------------: | :----------------------------------------------------------: |
| `final-auto-annotation` |                                                              |
|  `final-auto-service`   | 自动生成`META-INF/services`文件，支持JDK原生`SPI`及`Dubbo`扩展`SPI` |
|   `final-auto-spring`   | 自动生成`META-INF/spring.factories`文件，支持对`SpringFactory`进行扩展 |
|   `final-auto-coding`   |        使用`Velocity`模板引擎，提供模板代码生成的基础        |
|    `final-auto-data`    | 1. 解析所有实现了`IEntity`接口的类，生成`META-INF/service/**.IEntity`文件，供其他组件使用 |
|   `final-auto-query`    | 1. 解析`META-INF/service/**.IEntity`文件，自动生成`QEntity`源文件 |
|  `final-auto-mybatis`   | 1. 解析`META-INF/service/**.IEntity`文件，自动生成`EntityMapper`源文件 |
|                         |                                                              |




### final-boot

`final-boot`基于`final-framework`，提供快速开始的`starters`。

|          artifactId          |      描述       |
| :--------------------------: | :-------------: |
|     `final-boot-starter`     |                 |
|  `final-boot-starter-json`   |  json快速集成   |
|  `final-boot-starter-data`   |                 |
|  `final-boot-starter-cache`  |  cache快速集成  |
| `final-boot-starter-mybatis` | mybatis快速集成 |
| `final-boot-starter-monitor` | monitor快速集成 |
|   `final-boot-starter-web`   |   web快速集成   |
|                              |                 |
|                              |                 |
|                              |                 |



## 开发规范

* [日志规范](docs/code-rules/logger.md)

## 致谢

* 感谢<a href=""><img src="https://www.jetbrains.com/apple-touch-icon.png" width="64" height="64">jetbrains</a>提供的免费授权。
