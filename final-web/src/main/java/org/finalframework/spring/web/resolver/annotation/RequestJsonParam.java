

package org.finalframework.spring.web.resolver.annotation;

import org.finalframework.spring.web.resolver.RequestJsonParamHandlerMethodArgumentResolver;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.ValueConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Parameter;

/**
 * 被{@link RequestJsonParam}注解标记的{@link Parameter}元素，会将表单中的的{@link RequestJsonParam#name()}所对就的值使用Json
 * 反序列化为目标所声明的类型。
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-31 11:17:53
 * @see org.springframework.web.bind.annotation.RequestParam
 * @see org.springframework.web.bind.annotation.RequestBody
 * @see RequestJsonParamHandlerMethodArgumentResolver
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestJsonParam {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean required() default true;

    String defaultValue() default ValueConstants.DEFAULT_NONE;
}