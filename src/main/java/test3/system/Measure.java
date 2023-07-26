package test3.system;

/**
 * 测量接口
 *
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 08:40
 */
public interface Measure {
    /**
     * @param object 带测量的对象
     * @return 测量的结果
     */
    public double doMeasure(Object object);
}
