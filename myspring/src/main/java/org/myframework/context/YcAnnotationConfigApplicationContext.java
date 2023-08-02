package org.myframework.context;

import org.myframework.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-07-28 09:14
 */
public class YcAnnotationConfigApplicationContext implements YcApplicationContext {
    private Logger logger = LoggerFactory.getLogger(YcAnnotationConfigApplicationContext.class);

    //存每个待托管的Bean的定义信息
    private Map<String, YcBeanDefintion> beanDefintionMap = new HashMap<>();

    //存每个实例化后的bean
    private Map<String, Object> beanMap = new HashMap<>();

    //存系统属性，db.properties
    private Properties pros;


    /**
     * 构造方法
     *
     * @param configClasses
     */
    public YcAnnotationConfigApplicationContext(Class... configClasses) {
        try {
            //读取系统的属性，存好
            pros = System.getProperties();
            List<String> toScanPackagePath = new ArrayList<>();
            for (Class cls : configClasses) {
                if (cls.isAnnotationPresent(YcConfiguration.class) == false) {
                    continue;
                }
                String[] basePackages = null;
                //扫描配置类上的@YcComponentScan注释，读取要扫描的包
                if (cls.isAnnotationPresent(YcComponentScan.class)) {
                    //如果，则说明此配置类上有@YcComponentScan，则读取basePackages
                    YcComponentScan ycComponentScan = (YcComponentScan) cls.getAnnotation(YcComponentScan.class);
                    basePackages = ycComponentScan.basePackages();
                    if (basePackages == null || basePackages.length <= 0) {
                        basePackages = new String[1];
                        basePackages[0] = cls.getPackage().getName();
                    }
                    logger.info(cls.getName() + "类上有@YcComponentScan注解，它要扫描的路径：" + basePackages[0]);
                }
                //开始扫描这些basePackages包下的bean，并加载包装成BeanDefinition对象，存到beanDefinitionMap
                recursiveLoadBeanDefinition(basePackages);
                System.out.println(beanDefintionMap);
            }
            //循环beanDefinitionMap，创建bean（是否为懒加载、是单例），存到beanMap
            createBean();
            //循环所有托管的beanMap中的bean，看属性和方法上是否有@Autowired，@Resource，@Value......，考虑DI
            doDi();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    /**
     * 开始扫描这些basePackages包下的bean，并加载包装成BeanDefinition对象，存到DefinitionMap
     */
    private void doDi() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        //循环的是beanMap，这是托管Bean
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            String beanId = entry.getKey();
            Object beanObj = entry.getValue();
            //情况一：属性上有 @YcResource注解的情况
            //情况二：方法上有@YcResource注解的情况
            //情况三：构造方法上有@YcResource注解的情况
            Field[] fields = beanObj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(YcResource.class)) {
                    YcResource ycResource = field.getAnnotation(YcResource.class);
                    String toDiBeanId = ycResource.name();
                    //从beanMap中找是否singleton、是否lazy
                    Object obj = getToDiBean(toDiBeanId);
                    //注入
                    field.setAccessible(true);//因为属性是private的，所以要将他accessible设为true
                    field.set(beanObj, obj);//userBizImpl.userDao=userDaoImpl
                }
            }
        }
    }


    /**
     * 从beanMap中找是否singleton、是否lazy
     *
     * @param toDiBeanId
     * @return
     */
    private Object getToDiBean(String toDiBeanId) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (beanMap.containsKey(toDiBeanId)) {
            return beanMap.get(toDiBeanId);
        }
        //判断beanMap中没有此bean是否因为lazy
        if (!beanDefintionMap.containsKey(toDiBeanId)) {
            throw new RuntimeException("spring容器中没有加载此class：" + toDiBeanId);
        }
        YcBeanDefintion bd = beanDefintionMap.get(toDiBeanId);
        if (bd.isLazy()) {
            //是因为懒所以没有托管
            String classpath = bd.getClassInfo();
            Object beanObj = Class.forName(classpath).newInstance();
            beanMap.put(toDiBeanId, beanObj);
            return beanObj;
        }
        //是否因为prototype
        if (bd.getScope().equalsIgnoreCase("prototype")) {
            //是因为懒所以没托管
            String classpath = bd.getClassInfo();
            Object beanObj = Class.forName(classpath).newInstance();
            //beanMap.put(toDiBeanId,beanObj);原型模式下每次getBean创建一次bean，所以beanMap不存
            return beanObj;
        }
        return null;
    }


    /**
     * 循环beanDefinitionMap，创建bean（是否为懒加载，是单例），存到beanMap
     */
    private void createBean() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (Map.Entry<String, YcBeanDefintion> entry : beanDefintionMap.entrySet()) {
            String beanId = entry.getKey();
            YcBeanDefintion ybd = entry.getValue();
            if (!ybd.isLazy() && !ybd.getScope().equalsIgnoreCase("prototype")) {
                String classInfo = ybd.getClassInfo();
                Object obj = Class.forName(classInfo).newInstance();
                beanMap.put(beanId, obj);
                logger.trace("spring容器托管了：" + beanId + "==>" + classInfo);
            }
        }
    }


    /**
     * 开始扫描这些basePackages包下的bean，并加载包装成BeanDefinition对象，存到beanDefinitionMap
     *
     * @param basePackages
     */
    private void recursiveLoadBeanDefinition(String[] basePackages) {
        for (String basePackage : basePackages) {
            //将包名中的"."替换成路径中的"/"
            String packagePath = basePackage.replaceAll("\\.", "/");
            //target/classes     com/yc
            //Enumeration集合     URL:每个资源的路径
            Enumeration<URL> files = null;
            try {
                files = Thread.currentThread().getContextClassLoader().getResources(packagePath);
                //循环这个files，看是否是我要加载的资源
                while (files.hasMoreElements()) {
                    URL url = files.nextElement();
                    logger.trace("当前正在递归加载：" + url.getFile());
                    //查找此包下的类       com/yc全路径       com/yc包名
                    findPackageClasses(url.getFile(), basePackage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void findPackageClasses(String packagePath, String basePackage) {
        //路径异常的处理，签名有"/"，则去掉它
        if (packagePath.startsWith("/")) {
            packagePath = packagePath.substring(1);
        }
        //取这个路径下所有的字节码文件（因为目录下肯有其他资源）
        File file = new File(packagePath);
        //只取后缀名为.class的字节码
        //写法一：接口的匿名内部类写法
        //写法二：lamada写法
        File[] classFiles = file.listFiles((pathname) -> {
            if (pathname.getName().endsWith(".class") || pathname.isDirectory()) {
                return true;
            }
            return false;
        });
        //循环此classFiles
        if (classFiles == null || classFiles.length <= 0) {
            return;
        }
        for (File cf : classFiles) {
            if (cf.isDirectory()) {
                //继续递归
                logger.trace("递归：" + cf.getAbsolutePath() + "，他对应的包名为：" + (basePackage + "." + cf.getName()));
                findPackageClasses(cf.getAbsolutePath(), basePackage + "." + cf.getName());
            } else {
                //是class文件，则取出文件，判断此文件对应的class中是否有@Component注解
                URLClassLoader uc = new URLClassLoader(new URL[]{});
                Class cls = null;
                try {
                    cls = uc.loadClass(basePackage + "." + cf.getName().replaceAll(".class", ""));
                    //可以支持Y从Com破net子注解
                    if (cls.isAnnotationPresent(YcComponent.class) ||
                            cls.isAnnotationPresent(YcConfiguration.class) ||
                            cls.isAnnotationPresent(YcController.class) ||
                            cls.isAnnotationPresent(YcService.class)) {
                        logger.info("加载到一个待托管的类：" + cls.getName());
                        //包装成BeanDefinition
                        YcBeanDefintion bd = new YcBeanDefintion();
                        if (cls.isAnnotationPresent(YcLazy.class)) {
                            bd.setLazy(true);
                        }
                        if (cls.isAnnotationPresent(YcScope.class)) {
                            YcScope ycScope = (YcScope) cls.getAnnotation(YcScope.class);
                            String scope = ycScope.value();
                            bd.setScope(scope);
                        }
                        //classInfo中保存这个待托管类的包路径。com.yc.dao.UserDaoImpl
                        //cls.newInstance()
                        bd.setClassInfo(basePackage + "." + cf.getName().replaceAll(".class", ""));
                        //存到beanDefintionMap中"beqnId"--->"BeanDefinition对象"
                        String beanId = getBeanId(cls);
                        this.beanDefintionMap.put(beanId, bd);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param cls
     * @return
     */
    private String getBeanId(Class cls) {
        YcComponent ycComponent = (YcComponent) cls.getAnnotation(YcComponent.class);
        YcController ycController = (YcController) cls.getAnnotation(YcController.class);
        YcService ycService = (YcService) cls.getAnnotation(YcService.class);
        YcRepository ycRepository = (YcRepository) cls.getAnnotation(YcRepository.class);
        YcConfiguration ycConfiguration = (YcConfiguration) cls.getAnnotation(YcConfiguration.class);

        if (ycConfiguration != null) {
            return cls.getName();
        }
        String beanId = null;
        if (ycComponent != null) {
            beanId = ycComponent.value();
        } else if (ycController != null) {
            beanId = ycController.value();
        } else if (ycService != null) {
            beanId = ycService.value();
        } else if (ycRepository != null) {
            beanId = ycRepository.value();
        }
        if(beanId==null||beanId.equalsIgnoreCase("")){
            String typename=cls.getSimpleName();
            beanId=typename.substring(0,1).toLowerCase()+typename.substring(1);
        }
        return beanId;
    }


    @Override
    public Object getBean(String beanId) {
        YcBeanDefintion bd = this.beanDefintionMap.get(beanId);
        if (bd == null) {
            throw new RuntimeException("容器中没有加载此bean");
        }
        String scope = bd.getScope();
        if ("prototype".equalsIgnoreCase(scope)) {
            //原型模式，每次getBean创建
            Object obj = null;
            try {
                obj = Class.forName(bd.getClassInfo()).newInstance();
                //这种原型模式创建的bean不能保存到beanMap中
                return obj;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (this.beanMap.containsKey(beanId)) {
            return this.beanMap.get(beanId);
        }
        if (bd.isLazy()) {
            Object obj = null;
            try {
                obj = Class.forName(bd.getClassInfo()).newInstance();
                //懒加载的bean是要保存的
                this.beanMap.put(beanId, obj);
                return obj;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return obj;
        }
        return null;
    }
}
