package com.yc.staticproxy;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 18:44
 */
public interface OrderBiz {
    public void addOrder(int pid,int num);

    public void findOrder();
}
