package org.finalframework.cache.component;


import org.finalframework.cache.annotation.CacheValue;
import org.finalframework.cache.builder.CacheValueAnnotationBuilder;
import org.finalframework.cache.handler.CacheValueInvocationHandler;
import org.finalframework.cache.invocation.CacheValueInvocation;
import org.finalframework.cache.operation.CacheValueOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-23 00:18:41
 * @since 1.0
 */
public class CacheValueComponent extends AbsCacheComponent<CacheValue, CacheValueOperation,
        CacheValueAnnotationBuilder, CacheValueInvocation, CacheValueInvocationHandler> {

    public CacheValueComponent() {
        super(CacheValue.class, new CacheValueAnnotationBuilder(), new CacheValueInvocation(), new CacheValueInvocationHandler());
    }
}
