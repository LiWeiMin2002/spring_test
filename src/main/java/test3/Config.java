package test3;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 14:41
 */
@Configuration
@ComponentScan(basePackages = {"test3.system","test3.user"})
public class Config {
}
