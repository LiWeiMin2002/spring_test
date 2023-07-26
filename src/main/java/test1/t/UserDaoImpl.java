package test1.t;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import test1.dao.UserDao;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-25 17:09
 */
//@Repository       标识这是一个dao层的类，由spring托管
//@Component        由spring托管
@Repository
public class UserDaoImpl implements UserDao {
    public UserDaoImpl(){
        System.out.println("UserDaoImpl类的构造......");
    }

    @Override
    public void add(String uname) {
        System.out.println("添加了"+uname);
    }
}
