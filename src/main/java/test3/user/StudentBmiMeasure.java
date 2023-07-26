package test3.user;

import org.springframework.stereotype.Component;
import test3.system.Measure;

import java.util.Map;

/**学生的BMI测量接口
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 14:38
 */
@Component("bmiMeasure")
public class StudentBmiMeasure implements Measure {
    @Override
    public double doMeasure(Object object) {
        if(object==null){
            throw new RuntimeException("待测数据异常");
        }
        if(!(object instanceof Student)){
            throw new RuntimeException("待测数据异常");
        }
        Student student=(Student)object;
        return student.getWeight()/(student.getHeight()*student.getHeight());
    }
}
