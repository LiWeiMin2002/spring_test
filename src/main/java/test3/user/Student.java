package test3.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 14:31
 */
@Data
@AllArgsConstructor //带所有参数构造方法
@NoArgsConstructor  //无参构造方法
public class Student {
    private String name;
    private double height;
    private double weight;
}
