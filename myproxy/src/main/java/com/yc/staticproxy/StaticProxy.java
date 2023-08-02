package com.yc.staticproxy;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 18:44
 */
public class StaticProxy implements OrderBiz {
    private OrderBiz orderBiz;//目标类的引用

    public StaticProxy(OrderBiz orderBiz) {
        this.orderBiz = orderBiz;
    }

    @Override
    public void addOrder(int pid, int num) {
        //前置增强
        showHello();
        this.orderBiz.addOrder(pid, num);
        //后置增强
        showBye();
    }

    @Override
    public void findOrder() {

    }


    public void showHello(){
        System.out.println("前置增强");
    }

    public void showBye(){
        System.out.println("后置增强");
    }
}
