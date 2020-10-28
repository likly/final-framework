package org.finalframework.web.response;


import org.finalframework.annotation.result.R;
import org.finalframework.annotation.result.Result;
import org.finalframework.util.function.Converter;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-09 11:17:03
 * @since 1.0
 */
public class Object2ResultConverter implements Converter<Object, Result<?>> {
    @Override
    public Result<?> convert(Object body) {
        if (body == null) {
            return R.success();
        }

        if (body instanceof Result) {
            return (Result<?>) body;
        }

        if (body instanceof Serializable) {
            return R.success((Serializable) body);
        }

        throw new IllegalArgumentException(body.getClass() + " must impl Serializable");

    }
}

