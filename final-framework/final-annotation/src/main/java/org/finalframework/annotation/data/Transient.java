package org.finalframework.annotation.data;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Annotate the element of {@link Field},{@link Method},{@link Class} is {@link Transient}.
 *
 * @author likly
 * @version 1.0
 * @date 2019-08-08 19:51:19
 * @see Column
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@org.springframework.data.annotation.Transient
public @interface Transient {

}