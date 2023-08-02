package org.myframework.context;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-28 09:07
 */
public interface YcApplicationContext {
    public Object getBean(String beanId) throws ClassNotFoundException;
}
