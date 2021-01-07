---
formatter: off
layout: post
title: start-with-mybatis 
subtitle: start-with-mybatis 
description: start-with-mybatis 
tags: [] 
menus: 
    - quick-start
    - start-with-mybatis 
date: 2021-01-06 20:29:53 +800 
version: 1.0
formatter: on
---

# start-with-mybatis

## How

### Import Dependency

* maven

```xml

<dependencies>
    <dependency>
        <groupId>org.ifinal.finalframework.boot</groupId>
        <artifactId>final-boot-starter-mybatis</artifactId>
        <version>{{ site.final.version }}</version>
    </dependency>
    <dependency>
        <groupId>org.ifinal.finalframework.auto</groupId>
        <artifactId>final-auto-mybatis</artifactId>
        <version>{{ site.final.version }}</version>
    </dependency>
</dependencies>
```

### Declare package-info.java

Declare a `package-info.java` file in your project source directory like `src/main/java`.
