package test4;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 20:20
 */
public interface RowMapper<T> {
    /**
     * 对第i行的ResultSet转换成T对象，这个具体的实现由用户完成
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    public T mapper(ResultSet rs, int i) throws SQLException;
}
