package org.myframework.context;

/**
 * 对一个Bean的特征的包装的类
 * 特征：scope(singleton、prototype......)
 *      lazy(true、false)懒加载
 *      primary：实例|优先实例      UserDao接口：@Primary  UserDaoMybatisImpl类
 *                              业务层@Autowired+@Qualifier
 *                              业务层@Autowired+@Primary
 *
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-28 09:08
 */
public class YcBeanDefintion {
    private boolean isLazy;
    private  String scope="singleton";
    private boolean isPrimary;
    //......
    private String classInfo;//类的实例信息

    public boolean isLazy(){
        return isLazy;
    }

    public void setLazy(boolean lazy){
        this.isLazy=lazy;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    @Override
    public String toString() {
        return "YcBeanDefintion{" +
                "isLazy=" + isLazy +
                ", scope='" + scope + '\'' +
                ", isPrimary=" + isPrimary +
                ", classInfo='" + classInfo + '\'' +
                '}';
    }
}
