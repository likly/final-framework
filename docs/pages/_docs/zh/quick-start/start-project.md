---
formatter: off
layout: post
title: 创建工程
subtitle: start-project 
description: start-project 
tags: [] 
date: 2021-01-07 13:01:52 +800 
version: 1.0
formatter: on
---

# 创建工程

## 创建一个 Spring Boot 工程

### 使用 Idea Spring Initializr

1. 创建一个工程
   ![Idea Spring Initializer](../images/quick-start/idea-spring-initializr.png)
2. 配置新工程信息
   ![Idea Spring New Project](../images/quick-start/idea-spring-new-project.png)

### 使用 Spring Initializr

Use [`start.spring.io`](https://start.spring.io) to create a spring project.

![Spring Initializr](../images/quick-start/start.spring.io.png)

## 导入依赖

### Maven

添加 `final-boot` 依赖通过**替换 `parent`节点**或**导入`pom`**。

* 替换 Parent 节点

```xml

<parent>
    <groupId>org.ifinal.finalframework.boot</groupId>
    <artifactId>final-boot</artifactId>
    <version>{{ site.final.version }}</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

* 导入 Pom

```xml

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.ifinal.finalframework.boot</groupId>
            <artifactId>final-boot</artifactId>
            <version>{{ site.final.version }}</version>
            <scope>import</scope>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>
```