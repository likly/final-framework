package org.finalframework.cache.invocation;

import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.annotation.Cacheable;
import org.finalframework.cache.operation.CacheValueOperation;
import org.finalframework.json.Json;
import org.finalframework.spring.aop.OperationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @see Cacheable
 * @since 1.0
 */
@SuppressWarnings("all")
public class CacheValueInvocation extends AbsCacheInvocationSupport implements CacheInvocation<CacheValueOperation> {

    @Override
    public Void before(Cache cache, OperationContext<CacheValueOperation> context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, null);
        final CacheValueOperation operation = context.operation();

        final Object key = generateKey(operation.key(), operation.delimiter(), context.metadata(), evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache action generate null key, action=" + context.operation());
        }
        final Object field = generateField(operation.field(), operation.delimiter(), context.metadata(), evaluationContext);
        final Type type = operation.parameterType();
        logger.info("==> cache get: key={},field={}", key, field);
        Object cacheValue = cache.get(key, field, type, null);
        logger.info("<== value: {}", Json.toJson(cacheValue));
        context.args()[operation.index()] = cacheValue;
        return null;
    }

}
