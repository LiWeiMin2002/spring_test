package com.yc.test01.biz;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 09:54
 */
public interface OrderBiz {
    public void makeOrder(int pid,int num);

    public int findOrderId(String pname);

    public int findPid(String pname);
}
