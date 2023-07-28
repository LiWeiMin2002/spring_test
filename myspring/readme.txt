1. maven中的依赖继承, 父子模块的关系.
2. 日志的配置
3. spring分析. spring原版注解:
     (1)容器配置相关注解
         @Configuration:  加了 Component,说明此配置类也要被spring托管.
         @ComponentScan

     (2)IOC相关注解
         @Component:  基础IOC注解,以下IOC注解都是 此注解的具体实现.
         @Repository
         @Service
         @Controller

         @Bean:  在配置类中加在方法上,用于托管第三方类

     (3)DI注解:
         @Autowired
         @Qualifier
         @Value

     (4)生命周期管理注解
          @Lazy
          @Scope


     并请根据你的理解，做自定义的实现( 要求名字稍改一下，在前面加一个Yc以示区分).

         重点:
          1. 这些注解是加在什么位置(类，方法，属性。。。。)?, 怎么定义?
          2. 这些注解是否有参数？参数是什么，有没有默认值？如何实现?
          3. 这些注解的retentionPolicy保持策略( 即什么时候有效,取值有  source->compile->runtime )















git init    初始化
git add .     从工作区加入到暂存区
git commit -m "信息"    从暂存区到版本库
git push      git hub.

========================
日志:
	级别 ：OFF > FATAL > ERROR >          WARN >    INFO >  DEBUG >  TRACE > ALL
	appender:  输出
         		 console, file, rollingfile(按时间, 按大小,间隔)
      		 模式: PatternLayout
	logger:日志器
       		配置特定目录怎么输出
       		 root  默认情况.
	Logger对象    debug() error()  trace()....
==============================
注解:   j2se   标记

   @Target(  TYPE, METHOD, FIELD, CONSTRUCTOR )
   @Retention( Runtime )              保持策略 RESOURCE, CLASS
@interface 注解名{

}


注解解析器:
    类的Class实例,        isAnnotationPresent()
                              注解类     cls.getAnnotation(注解.class)
                             值=  注解类.属性( );




















