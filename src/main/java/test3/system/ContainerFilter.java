package test3.system;

/**
 * 容器过滤接口
 *
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 08:41
 */
public interface ContainerFilter {
    /**
     * 判断此对象是否为合法对象
     *
     * @param object
     * @return
     */
    public boolean doFilter(Object object);
}
