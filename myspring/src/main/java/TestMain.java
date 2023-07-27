import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-27 15:16
 */
public class TestMain {
    public static void main(String[]args){
        Logger logger= LoggerFactory.getLogger(TestMain.class);
        logger.error("error");
        logger.warn("warn");
        logger.info("info");
        logger.debug("debug");
        logger.trace("trace");
    }
}
