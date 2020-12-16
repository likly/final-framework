package org.ifinal.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Criterion(GreatThanEqual.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GreatThanEqual {

    String property() default "";

    String[] value() default {
        "<if test=\"${value} != null\">",
        "   <![CDATA[",
        "       ${andOr} ${column} >= #{${value}",
        "       #if($javaType), javaType=$!{javaType.canonicalName}#end",
        "       #if($typeHandler), typeHandler=$!{typeHandler.canonicalName}#end}",
        "   ]]>",
        "</if>",
    };

    Class<?> javaType() default Object.class;

}
