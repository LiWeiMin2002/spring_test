package com.yc.service;


import com.yc.dao.UserDao;
import org.myframework.annotation.YcResource;
import org.myframework.annotation.YcService;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-28 09:33
 */
@YcService
public class UserBizImpl implements UserBiz {

    @YcResource(name = "userDaoImpl")
    private UserDao userDao;


    @Override
    public void add(String uname) {
        userDao.add(uname);
    }
}
