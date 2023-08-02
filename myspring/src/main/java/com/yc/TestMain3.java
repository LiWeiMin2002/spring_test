package com.yc;

import com.yc.MyConfig;
import com.yc.service.UserBiz;
import org.myframework.context.YcAnnotationConfigApplicationContext;
import org.myframework.context.YcApplicationContext;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-28 09:38
 */
public class TestMain3 {
    public static void main(String[]args) throws ClassNotFoundException {
        YcApplicationContext ac=new YcAnnotationConfigApplicationContext(MyConfig.class);
        System.out.println("容器创建成功");
//        UserBiz ub=(UserBiz)ac.getBean("userBizImpl");
//        ub.add("张三");
    }
}












