package test1.t;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-25 17:08
 */
@Configuration      //此类是一个配置类（这个类也会被spring创建），声明容器运行时的一些配置信息（1.扫描的路径，这个路径所有带有@Configuration，spring托管
@ComponentScan(basePackages = {"test1.t"})
public class Config {


}
