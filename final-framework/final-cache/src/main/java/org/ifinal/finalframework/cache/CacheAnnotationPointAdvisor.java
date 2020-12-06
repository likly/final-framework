package org.ifinal.finalframework.cache;

import org.ifinal.finalframework.aop.AnnotationAttributesAnnotationBuilder;
import org.ifinal.finalframework.aop.multi.MultiAnnotationPointAdvisor;
import org.ifinal.finalframework.cache.annotation.*;
import org.ifinal.finalframework.cache.handler.*;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CacheAnnotationPointAdvisor extends MultiAnnotationPointAdvisor<AnnotationAttributes, Cache> {

    @Resource
    private RedisCache redisCache;

    public CacheAnnotationPointAdvisor() {

        this.addAnnotation(CacheLock.class, new AnnotationAttributesAnnotationBuilder<>(), new CacheLockInterceptorHandler());
        this.addAnnotation(Cacheable.class, new AnnotationAttributesAnnotationBuilder<>(), new CacheableInterceptorHandler());
        this.addAnnotation(CachePut.class, new AnnotationAttributesAnnotationBuilder<>(), new CachePutInterceptorHandler());
        this.addAnnotation(CacheDel.class, new AnnotationAttributesAnnotationBuilder<>(), new CacheDelInterceptorHandler());
        this.addAnnotation(CacheIncrement.class, new AnnotationAttributesAnnotationBuilder<>(), new CacheIncrementInterceptorHandler());
        this.addAnnotation(CacheValue.class, new AnnotationAttributesAnnotationBuilder<>(), new CacheValueInterceptorHandler());

    }

    @Override
    @NonNull
    protected Cache getExecutor(AnnotationAttributes annotation) {
        return redisCache;
    }
}
