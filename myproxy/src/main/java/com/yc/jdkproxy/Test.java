package com.yc.jdkproxy;

import com.yc.staticproxy.OrderBiz;
import com.yc.staticproxy.OrderBizImpl;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 19:54
 */
public class Test {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        JdkProxyTool jpt = new JdkProxyTool(new OrderBizImpl());
        OrderBiz ob = (OrderBiz) jpt.creatProxy();

        ob.findOrder();
        ob.addOrder(1, 99);

    }
}
