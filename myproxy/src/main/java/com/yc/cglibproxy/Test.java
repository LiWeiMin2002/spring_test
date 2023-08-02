package com.yc.cglibproxy;


/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 21:24
 */
public class Test {
    public static void main(String[] args) {
        CglibProxyTool cpt = new CglibProxyTool(new OrderBizImpl());
        OrderBizImpl ob = (OrderBizImpl) cpt.createProxy();
        System.out.println("生成代理类：" + ob.toString());

        ob.findOrder();

        ob.addOrder(1, 99);
    }
}
