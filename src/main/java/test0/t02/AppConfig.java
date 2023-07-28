package test0.t02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring学习
 * @description: 相当于一个beans.xml配置文件
 * @author: lwm
 * @create: 2023-07-27 16:30
 */
@Configuration//此注释表示这个类时一个配置类相当于beans.xml文件
public class AppConfig {
    @Bean
    public Apple getApple(){
        Apple apple=new Apple();
        apple.setId(100);
        return apple;
    }
}
