package test3.user;

import org.springframework.stereotype.Component;
import test3.system.ContainerFilter;

/**
 * bmi筛选器
 *
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 14:33
 */
@Component("bmiFilter") //"bmiFilter"对象
public class StudentBmiFilter implements ContainerFilter {
    @Override
    public boolean doFilter(Object object) {
        if (object == null) {
            return false;
        }
        if (!(object instanceof Student)) {
            return false;
        }
        Student student = (Student) object;
        if (student.getName() == null || "".equalsIgnoreCase(student.getName())) {
            return false;
        }
        if (student.getHeight() < 1 || student.getHeight() > 2.7) {
            System.out.println("身高数据异常" + object.toString());
            return false;
        }
        if (student.getWeight() < 30 || student.getWeight() > 500) {
            System.out.println("体重数据异常" + object.toString());
        }
        return true;
    }
}
