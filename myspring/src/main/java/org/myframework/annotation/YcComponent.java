package org.myframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-27 15:22
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface YcComponent {
    String value() default "";
}
