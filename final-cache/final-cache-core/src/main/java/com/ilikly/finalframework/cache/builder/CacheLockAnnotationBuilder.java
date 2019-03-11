package com.ilikly.finalframework.cache.builder;


import com.ilikly.finalframework.cache.CacheAnnotationBuilder;
import com.ilikly.finalframework.cache.annotation.CacheLock;
import com.ilikly.finalframework.cache.operation.CacheLockOperation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 09:26:21
 * @since 1.0
 */
public class CacheLockAnnotationBuilder extends AbsCacheAnnotationBuilder implements CacheAnnotationBuilder<CacheLock, CacheLockOperation> {

    @Override
    public CacheLockOperation build(Class<?> type, CacheLock ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public CacheLockOperation build(Method method, CacheLock ann) {
        return build((AnnotatedElement) method, ann);
    }

    private CacheLockOperation build(AnnotatedElement ae, CacheLock ann) {
        return CacheLockOperation.builder()
                .name(ae.toString())
                .key(parse(ann.key(), ann.delimiter()))
                .value(ann.value())
                .delimiter(ann.delimiter())
                .condition(ann.condition())
                .ttl(ann.ttl())
                .timeunit(ann.timeunit())
                .invocation(ann.invocation())
                .build();

    }
}
