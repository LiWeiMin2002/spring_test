package com.yc.staticproxy;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 18:44
 */
public class OrderBizImpl implements OrderBiz {
    @Override
    public void addOrder(int pid, int num) {
        System.out.println("添加了" + num + "个pid为" + pid + "的商品");
    }

    @Override
    public void findOrder() {
        System.out.println("查询订单");
    }
}
