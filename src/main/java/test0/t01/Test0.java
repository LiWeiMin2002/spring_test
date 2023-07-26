package test0.t01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 10:16
 */
public class Test0 {
    public static void main(String[] args) {
        //启动容器，读取class路径下的beans.xml文件，并获取bean配置的class，实例化对象存到容器中
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        Student student=(Student)context.getBean("student");
        System.out.println(student);

        ///di       依赖注入



    }
}
