package commons;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DbHelper类：连接数据库
 *
 * @program: game_zhenghe
 * @description:
 * @author: lwm
 * @create: 2023-07-08 09:19
 */
public class DbHelper {
    private Connection conn;//连接对象：Connection
    private PreparedStatement stmt;//预编译对象：PreparedStatement
    private ResultSet rs;//结果集对象：ResultSet


    //静态块
    static {
        try {
            //Class.forName("")
            com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();//创建驱动类
            DriverManager.registerDriver(driver); //注册驱动
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取连接对象（Connection）：通过驱动管理器获取
     *
     * @return
     */
    public Connection getConn() {
        try {
            conn = DriverManager.getConnection(MyProperties.getInstance().getProperty("url"),
                    MyProperties.getInstance());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    /**
     * 关闭所有的资源：Connection、stmt、rs
     *
     * @param conn
     * @param stmt
     * @param rs
     */
    public void closeAll(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 参数设置
     *
     * @param stmt
     * @param params
     * @throws SQLException
     */
    private void setParams(PreparedStatement stmt, Object... params) throws SQLException {
        if (null == params || params.length <= 0) {
            return;
        }
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]); //从1开始  第一个参数 i+1
        }
    }


    /**
     * 多条sql语句,更新操作   事务
     *
     * @param sqls   sql语句添加的顺序必须和小List集合的顺序必须一致
     * @param params
     * @return
     * @throws SQLException
     */
    public int update(List<String> sqls, List<List<Object>> params) throws SQLException {
        int result = 0;
        try {
            //获取连接对象
            conn = this.getConn();
            //设置事务手动提交，禁用自动提交
            conn.setAutoCommit(false);
            //循环sql语句
            for (int i = 0; i < sqls.size(); i++) {
                String sql = sqls.get(i);//参数与一一对应的
                //获取当前sql语句对应的参数集合
                List<Object> list = params.get(i);
                //根据连接对象创建预编译对象
                stmt = conn.prepareStatement(sql);
                //参数设置
                setParams(stmt, list.toArray());
                //执行更新操作
                result = stmt.executeUpdate();
                if (result <= 0) {
                    //失败
                    //事务回滚
                    conn.rollback();
                    return result;
                }
            }
            //事务提交
            conn.commit();
        } catch (Exception e) {
            result = 0;
            //事务回滚
            conn.rollback();
            e.printStackTrace();
        } finally {
            //还原事务状态
            conn.setAutoCommit(true);
            //关闭资源
            this.closeAll(conn, stmt, null);
        }
        return result;
    }


    /**
     * 单条sql语句更新（Insert、update、delete ）：返回受影响的函数
     *
     * @param sql
     * @param params 参数   参数传入的顺序必须和sql语句中?号对应的值的顺序一致
     * @return
     * @throws SQLException
     */
    public int update(String sql, Object... params) throws SQLException {
        int result = 0;
        try {
            //获取连接对象
            conn = this.getConn();
            //根据连接对象创建预编译对象
            stmt = conn.prepareStatement(sql);
            //占位符‘?’设置
            setParams(stmt, params);
            //执行更新操作
            result = stmt.executeUpdate();
        } finally {
            //关闭资源
            this.closeAll(conn, stmt, null);
        }
        return result;
    }


    /**
     * 根据结果集对象获取当前结果集中所有的列名
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private List<String> getColumnNames(ResultSet rs) throws SQLException {
        List<String> columnNames = new ArrayList<String>();
        //结果集元数据对象
        ResultSetMetaData metaData = rs.getMetaData();
        //获取总列数
        int count = metaData.getColumnCount();
        for (int i = 1; i <= count; i++) {  //列编号从1开始
            columnNames.add(metaData.getColumnName(i));  //获取列名添加到集合中 注意：getColumnName
        }
        return columnNames;
    }


    /**
     * 查询封装（查询多条记录）
     *
     * @param sql    查询语句
     * @param params 参数顺序必须和问号一致
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> find(String sql, Object... params) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        try {
            conn = this.getConn();
            stmt = conn.prepareStatement(sql);
            //参数设置
            setParams(stmt, params);
            //执行查询操作，返回结果集对象
            rs = stmt.executeQuery();
            //获取结果集中所有的字段名称
            List<String> columnNames = getColumnNames(rs);
            //多条记录
            while (rs.next()) {
                map = new HashMap<String, Object>();
                //以列名为键，对应列的值为值
                for (String name : columnNames) {
                    //获取到当前列的值
                    Object value = rs.getObject(name);
                    if (null == value) {
                        map.put(name, value);//将空值存到map中
                        continue;
                    }
                    //获取当前值得数据类型
                    String typeName = value.getClass().getName();
                    map.put(name, rs.getObject(name));
                }
                list.add(map);
            }
        } finally {
            this.closeAll(conn, stmt, rs);
        }
        return list;
    }


    /**
     * 查询封装（查询单条记录）：select * from table_name where id =?
     *
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String, Object> findSingle(String sql, Object... params) throws Exception {
        List<Map<String, Object>> list = this.find(sql, params);
        if (null == list || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    /**
     * 查询封装<T>（查询多条记录，封装到对象中）
     *
     * @param sql
     * @param cls
     * @param params
     * @param <T>
     * @return
     */
    public <T> List<T> finds(String sql, Class<T> cls, Object... params) {
        List<T> list = new ArrayList<>();
        T t = null;
        try {
            conn = this.getConn();
            stmt = conn.prepareStatement(sql);
            //参数设置
            setParams(stmt, params);
            //执行查询操作，返回结果集
            rs = stmt.executeQuery();
            //获取结果集中所有字段名称
            List<String> columnNames = getColumnNames(rs);
            //根据反射获取所有的属性和方法
            Field[] fields = cls.getDeclaredFields();
            Method[] methods = cls.getDeclaredMethods();
            Object obj = null;
            while (rs.next()) {
                //根据反射创建对象
                t = cls.newInstance();//底层调用当前T类的无参构造函数
                for (Field f : fields) {
                    //获取字段名
                    String fname = f.getName();
                    if ("serialVersionUID".equalsIgnoreCase(fname) ||
                            "gameAccountPropInfos".equalsIgnoreCase(fname) ||
                            "sort".equalsIgnoreCase(fname)
                    ) {
                        continue;
                    }
                    //获取对应的字段值
                    obj = rs.getObject(fname);
                    if (obj == null) {
                        continue;
                    }
                    for (Method m : methods) {
                        //set+属性
                        if (("set" + fname).equalsIgnoreCase(m.getName())) {
                            //激活底层方法 obj.setXx();
                            //获取set方法形参的数据类型
                            String typeName = m.getParameterTypes()[0].getName();
                            //java.lang.Integer   java.lang.String, int
                            if ("java.lang.Integer".equals(typeName) || "int".equals(typeName)) {
                                m.invoke(t, rs.getInt(fname));
                            } else if ("java.lang.Long".equals(typeName) || "long".equals(typeName)) {
                                m.invoke(t, rs.getLong(fname));
                            } else if ("java.lang.Double".equals(typeName) || "double".equals(typeName)) {
                                m.invoke(t, rs.getDouble(fname));
                            } else if ("java.lang.Float".equals(typeName) || "float".equals(typeName)) {
                                m.invoke(t, rs.getFloat(fname));
                            } else if ("java.sql.Time".equals(typeName) || "Time".equals(typeName)) {
                                m.invoke(t, rs.getTime(fname));
                            } else if ("java.sql.Timestamp".equals(typeName) || "Timestamp".equals(typeName)) {
                                m.invoke(t, rs.getTimestamp(fname));
                            } else if ("java.util.Date".equals(typeName) || "Date".equals(typeName)) {
                                m.invoke(t, rs.getTimestamp(fname));
                            } else {
                                m.invoke(t, rs.getString(fname));
                            }
                        }
                    }
                }
                //将对象添加到集合中
                list.add(t);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, stmt, rs);
        }
        return list;
    }


    /**
     * 查询封装<T>（查询单条记录，封装到对象中）
     *
     * @param sql
     * @param cls
     * @param params
     * @param <T>
     * @return
     */
    public <T> T findSingle(String sql, Class<T> cls, Object... params) {
        List<T> list = this.finds(sql, cls, params);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    /**
     * 使用聚合函数 select count（*） from 表名
     *
     * @param sql
     * @param params
     * @return
     */
    public double getPloymer(String sql, Object... params) {
        double number = 0;
        try {
            conn = getConn();
            stmt = conn.prepareStatement(sql);
            setParams(stmt, params);
            rs = stmt.executeQuery();
            if (rs.next()) {
                number = rs.getDouble(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeAll(conn, stmt, rs);
        }
        return number;
    }


    /**
     * 返回结果集对象
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql, Object... params) throws SQLException {
        conn = getConn();
        stmt = conn.prepareStatement(sql);
        setParams(stmt, params);
        return stmt.executeQuery();
    }
}
