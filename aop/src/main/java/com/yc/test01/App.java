package com.yc.test01;

import com.yc.test01.biz.OrderBiz;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 09:54
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        OrderBiz orderBiz = ac.getBean(OrderBiz.class);
//        orderBiz.makeOrder(1,99);
//        orderBiz.findOrderId("apple");

//        orderBiz.findOrderId("apple");
//        orderBiz.findOrderId("apple");
//        orderBiz.findOrderId("pear");

//        orderBiz.makeOrder(1,999);
//        orderBiz.findOrderId("apple");
        orderBiz.findPid("apple");
    }
}
