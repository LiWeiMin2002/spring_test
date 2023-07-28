package org.myframework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-28 14:15
 */
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface YcResource {
    String name() default "";
}
