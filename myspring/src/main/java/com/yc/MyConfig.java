package com.yc;

import org.myframework.annotation.YcComponentScan;
import org.myframework.annotation.YcConfiguration;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-27 15:25
 */
@YcConfiguration
@YcComponentScan(basePackages = "com.yc")
public class MyConfig {
}
