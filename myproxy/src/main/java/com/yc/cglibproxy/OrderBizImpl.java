package com.yc.cglibproxy;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 21:13
 */
public class OrderBizImpl {
    public void addOrder(int pid, int num) {
        System.out.println("添加了" + num + "个pid为" + pid + "的商品");
    }

    public void findOrder() {
        System.out.println("查询订单");
    }
}
