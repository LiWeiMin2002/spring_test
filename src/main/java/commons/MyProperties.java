package commons;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取配置文件且只需读取一次即可   --》单例设计模式  --》饿汉式
 *
 * @program: filmSystem
 * @description:
 * @author: lwm
 * @create: 2023-05-19 19:17
 */
public class MyProperties extends Properties {
    private static final long serialVersionUID = 2809456923943821836L;
    private static MyProperties instance = new MyProperties();


    //私有构造方法
    private MyProperties() {
        //加载文件
        try {
            this.load(MyProperties.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //获取MyProperties对象（单例设计模式-->饿汉式）
    public static MyProperties getInstance() {
        return instance;
    }
}
