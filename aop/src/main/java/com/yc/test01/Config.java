package com.yc.test01;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 09:59
 */
@Configuration
@ComponentScan(basePackages = "com.yc")
@EnableAspectJAutoProxy     //表示启用AspectJ支持
public class Config {
}
