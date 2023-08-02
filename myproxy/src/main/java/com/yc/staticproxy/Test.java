package com.yc.staticproxy;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 18:51
 */
public class Test {
    public static void main(String []args){
        OrderBiz ob=new StaticProxy(new OrderBizImpl());
        ob.addOrder(1,99);
    }
}
