package aaa;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @program: 三期测试
 * @description: 明日之后草木灰小工具
 * @author: lwm
 * @create: 2023-07-18 15:29
 */
public class MrzhTool {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        //获取第一次喷发、第二次喷发的时间
        System.out.print("请输入第一次喷发时间：");
        Timestamp timestamp1 = Timestamp.valueOf(scanner.nextLine());
        System.out.print("请输入第二次喷发时间：");
        Timestamp timestamp2 = Timestamp.valueOf(scanner.nextLine());


        int intervalTime = (int) (timestamp2.getTime() - timestamp1.getTime());//两次喷发的时间间隔（毫秒）
        int minutes = intervalTime / 60000;//间隔分钟
        int seconds = (intervalTime - minutes * 60000) / 1000;//间隔秒钟


        Timestamp resultTime = timestamp2;
        List<Timestamp> timestamps = new ArrayList<>();
        while (true) {
            int resultSeconds = resultTime.getSeconds() + seconds;
            int resultMinutes = resultTime.getMinutes() + minutes;
            int resultHours = resultTime.getHours();
            if (resultSeconds >= 60) {
                resultSeconds -= 60;
                resultMinutes += 1;
            }
            if (resultMinutes >= 60) {
                resultMinutes -= 60;
                resultHours += 1;
            }
            if (resultHours >= 24) {
                break;
            }
            resultTime = Timestamp.valueOf((timestamp2.getYear() + 1900) + "-" + (timestamp2.getMonth()+1) + "-" + (timestamp2.getDay() + 16) + " " + resultHours + ":" + resultMinutes + ":" + resultSeconds);
            timestamps.add(resultTime);
        }


        for (Timestamp timestamp : timestamps) {
            System.out.println(timestamp);
        }


        System.out.println();
        System.out.println();


    }
}
/**
 * 2023-07-23 00:32:00
 *
 *
 * 2023-07-23 00:49:30
 */