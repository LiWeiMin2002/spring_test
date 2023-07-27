package test4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-26 15:36
 */
public class App {
    public static void main(String[]args) throws SQLException {
        ApplicationContext ac=new AnnotationConfigApplicationContext(Config.class);
        DataSource ds=(DataSource)ac.getBean("myDataSource");

//        Connection con=ds.getConnection();
//        System.out.println(con);

        BankAccountDao bad=(BankAccountDao)ac.getBean("bankAccountDao");
        List<BankAccount>list=bad.findAll();
        for(BankAccount ba:list){
            System.out.println(ba);
        }
    }
}
