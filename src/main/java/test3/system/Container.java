package test3.system;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 容器类
 * 1.能存储大量对象（大虚设BMI）==>集合
 * 2.测量的接口  --->    具体怎么测由用户决定
 * 3.筛选的接口  --->    具体怎么筛选由用户决定
 * 4.返回最大值对象和最小值对象
 *
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 08:38
 */
//要用spring完成托管。选用：@Component、@Service、@Repository、@Controller
@Component
public class Container<T> {
    private List<T> objs = new ArrayList<T>();

    //要使用spring完成DI
//    @Autowired
//    @Qualifier(value = "bmiMeasure")

    //方案2
    @Resource(name = "bmiMeasure")
    private Measure measure;
    @Resource(name = "bmiFilter")
    private ContainerFilter filter;

    private T max;
    private T min;
    private double avg;
    private double sum;

    /**
     * 添加对象的方法
     *
     * @param t
     */
    public void add(T t) {
        //判断t合格，调用筛选的实现
        if (filter != null) {
            if (filter.doFilter(t) == false) {
                return;
            }
        }
        //添加到objs
        objs.add(t);
        //判断大小，记录max,min，计算avg  ----->  调用measure的实现
        if (objs.size() == 1) {
            max = t;
            min = t;
        } else {
            //测出结果，判断大小
            double val = this.measure.doMeasure(t);
            double maxval = this.measure.doMeasure(max);
            double minval = this.measure.doMeasure(min);
            if (val > maxval) {
                max = t;
            }
            if (val < minval) {
                min = t;
            }
            sum += measure.doMeasure(t);
            avg = sum / objs.size();
        }
    }

    public T getMax() {
        return max;
    }

    public T getMin() {
        return min;
    }

    public double getAvg() {
        return avg;
    }

    /**
     * 合法的测量对象有多少个
     *
     * @return
     */
    public int size() {
        return objs.size();
    }

    /**
     * 系统复位
     */
    public void clearAll() {
        objs = new ArrayList<T>();
        measure = null;
        filter = null;
        max = null;
        min = null;
        avg = 0;
    }
}
