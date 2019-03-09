package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.CacheOperation;
import com.ilikly.finalframework.core.Assert;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:35:59
 * @since 1.0
 */
public abstract class AbsCacheOperation<A extends Annotation> implements CacheOperation<A> {
    private final Collection<String> key;
    private final Collection<String> field;
    private final String result;
    private final String delimiter;
    private final String condition;
    private final String expire;
    private final Long ttl;
    private final TimeUnit timeUnit;
    private final Integer retry;
    private final Long sleep;

    protected AbsCacheOperation(Builder builder) {
        this.key = Assert.isEmpty(builder.key) ? null : builder.key;
        this.field = Assert.isEmpty(builder.field) ? null : builder.field;
        this.delimiter = Assert.isEmpty(builder.delimiter) ? ":" : builder.delimiter;
        this.result = Assert.isEmpty(builder.result) ? null : builder.result;
        this.condition = Assert.isEmpty(builder.condition) ? null : builder.condition;
        this.expire = Assert.isEmpty(builder.expire) ? null : builder.expire;
        this.ttl = builder.ttl;
        this.timeUnit = builder.timeUnit;
        this.retry = Assert.nonNull(builder.retry) && builder.retry > 1 ? builder.retry : null;
        this.sleep = Assert.nonNull(builder.sleep) && builder.sleep > 0L ? builder.sleep : null;
    }

    protected static Collection<String> parse(String[] keyOrField, String delimiter) {
        if (Assert.isEmpty(keyOrField)) return null;

        List<String> list = new ArrayList<>();
        Arrays.stream(keyOrField)
                .map(item -> item.split(delimiter))
                .forEach(items -> list.addAll(Arrays.asList(items)));
        return list;
    }


    @Override
    public Collection<String> key() {
        return key;
    }

    @Override
    public Collection<String> field() {
        return field;
    }

    @Override
    public String value() {
        return result;
    }

    @Override
    public String delimiter() {
        return delimiter;
    }

    @Override
    public String condition() {
        return condition;
    }

    @Override
    public String expire() {
        return this.expire;
    }

    @Override
    public Long ttl() {
        return this.ttl;
    }

    @Override
    public TimeUnit timeUnit() {
        return this.timeUnit;
    }

    @Override
    public Integer retry() {
        return this.retry;
    }

    @Override
    public Long sleep() {
        return this.sleep;
    }

    protected abstract static class Builder<O extends AbsCacheOperation> implements CacheOperation.Builder<O, Builder> {
        private Collection<String> key;
        private Collection<String> field;
        private String delimiter;
        private String result;
        private String condition;
        private String expire;
        private Long ttl;
        private TimeUnit timeUnit;
        private Integer retry;
        private Long sleep;

        @Override
        public Builder<O> key(Collection<String> key) {
            this.key = key;
            return this;
        }

        @Override
        public Builder<O> field(Collection<String> field) {
            this.field = field;
            return this;
        }

        @Override
        public Builder<O> value(String result) {
            this.result = result;
            return this;
        }

        @Override
        public Builder<O> delimiter(String delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        @Override
        public Builder<O> condition(String condition) {
            this.condition = condition;
            return this;
        }

        @Override
        public Builder<O> expire(String expire) {
            this.expire = expire;
            return this;
        }

        @Override
        public Builder<O> ttl(Long ttl) {
            this.ttl = ttl;
            return this;
        }

        @Override
        public Builder<O> timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        @Override
        public Builder<O> retry(Integer retry) {
            this.retry = retry;
            return this;
        }

        @Override
        public Builder<O> sleep(Long sleep) {
            this.sleep = sleep;
            return this;
        }
    }

}
