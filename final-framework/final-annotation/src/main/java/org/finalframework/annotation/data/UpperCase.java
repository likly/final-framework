package org.finalframework.annotation.data;

import java.lang.annotation.*;
import java.lang.reflect.Field;


/**
 * Annotated the name of {@link Class table} or {@link Field column} should be convert to upper case use {@link String#toUpperCase()}.
 *
 * @author likly
 * @version 1.0
 * @date 2020-05-20 22:00:28
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface UpperCase {
}