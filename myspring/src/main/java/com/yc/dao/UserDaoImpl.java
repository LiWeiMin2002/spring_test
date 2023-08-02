package com.yc.dao;


import com.yc.dao.UserDao;
import org.myframework.annotation.YcLazy;
import org.myframework.annotation.YcRepository;
import org.myframework.annotation.YcScope;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-28 09:34
 */
@YcRepository   //"userDaoImpl"  --->  对象
public class UserDaoImpl implements UserDao {

    @Override
    public void add(String uname){
        System.out.println("dao添加了："+uname);
    }
}
