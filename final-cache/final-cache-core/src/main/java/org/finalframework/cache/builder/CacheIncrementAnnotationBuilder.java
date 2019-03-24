package org.finalframework.cache.builder;


import org.finalframework.cache.CacheAnnotationBuilder;
import org.finalframework.cache.annotation.CacheIncrement;
import org.finalframework.cache.operation.CacheIncrementOperation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-22 22:54:34
 * @since 1.0
 */
public class CacheIncrementAnnotationBuilder extends AbsCacheAnnotationBuilder implements CacheAnnotationBuilder<CacheIncrement, CacheIncrementOperation> {

    @Override
    public CacheIncrementOperation build(Class<?> type, CacheIncrement ann) {
        return build((AnnotatedElement) type, ann);
    }

    @Override
    public CacheIncrementOperation build(Method method, CacheIncrement ann) {
        return build((AnnotatedElement) method, ann);
    }

    private CacheIncrementOperation build(AnnotatedElement ae, CacheIncrement ann) {
        final String delimiter = getDelimiter(ann.delimiter());
        return CacheIncrementOperation.builder()
                .name(ae.toString())
                .key(parse(ann.key(), delimiter))
                .field(parse(ann.field(), delimiter))
                .delimiter(delimiter)
                .condition(ann.condition())
                .point(ann.point())
                .value(ann.value())
                .type(ann.type())
                .ttl(ann.ttl())
                .expire(ann.expire())
                .timeunit(ann.timeunit())
                .invocation(ann.invocation())
                .build();

    }
}