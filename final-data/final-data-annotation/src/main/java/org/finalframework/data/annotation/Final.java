package org.finalframework.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The property annotated by {@link Final} will not be update.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @see PrimaryKey
 * @see Creator
 * @since 1.0
 */
@Column
@Documented
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Final {

}
