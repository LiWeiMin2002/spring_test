package test4;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 15:20
 */
@Configuration
@ComponentScan
@PropertySource(value = "classpath:db.properties")
public class Config {
}
