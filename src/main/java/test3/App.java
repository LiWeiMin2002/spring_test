package test3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import test3.system.Container;
import test3.user.Student;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 14:42
 */
public class App {
    public static void main(String[]args){
        ApplicationContext ac=new AnnotationConfigApplicationContext(Config.class);
        Container c=(Container)ac.getBean("container");

        c.add(new Student("张三",1.7,90));
        c.add(new Student("李四",1.9,80));
        c.add(new Student("王五",1.68,190));

        c.add(new Student("异常",0.3,10));

        System.out.println(c.getMax());
        System.out.println(c.getMin());
        System.out.println(c.getAvg());
        System.out.println(c.size());
    }
}
