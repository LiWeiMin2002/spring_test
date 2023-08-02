package com.yc.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 21:13
 */
public class CglibProxyTool implements MethodInterceptor {
    private Object target;

    public CglibProxyTool(Object target){
        this.target=target;
    }


    public Object createProxy() {
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        Object proxy=enhancer.create();
        return proxy;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(method.getName().startsWith("add")){
            showHello();
        }
        Object returnValue=method.invoke(target,objects);
        return returnValue;
    }

    public void showHello(){
        System.out.println("hello......");
    }
}
