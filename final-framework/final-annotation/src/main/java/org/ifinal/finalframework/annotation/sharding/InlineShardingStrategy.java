package org.ifinal.finalframework.annotation.sharding;

import org.ifinal.finalframework.auto.service.annotation.AutoService;

import java.lang.annotation.*;

/**
 * @author likly
 * @version 1.0.0
 * @see org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration
 * @see org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration
 * @see org.apache.shardingsphere.sharding.algorithm.sharding.inline.InlineShardingAlgorithm
 * @since 1.0.0
 */
@AutoService(ShardingStrategy.class)
@Repeatable(InlineShardingStrategy.ShardingStrategies.class)
@ShardingStrategy(strategy = ShardingStrategy.Strategy.STANDARD, type = ShardingStrategy.Algorithm.INLINE)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InlineShardingStrategy {

    ShardingStrategy.Scope scope();

    @Property(Property.INLINE_SHARING_COLUMNS)
    String[] columns();

    @Property(Property.INLINE_ALGORITHM_EXPRESSION)
    String expression();

    @Property(Property.INLINE_ALLOW_RANGE_QUERY)
    boolean allowRangeQuery() default false;

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ShardingStrategies {
        InlineShardingStrategy[] value();
    }

}
