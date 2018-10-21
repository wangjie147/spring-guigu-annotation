package com.facetofront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.facetofront.domain.Car;

/**
 * 
 * bean 的生命周期：
 *      bean的创建--BeanPostProcessor.postProcessBeforeInitialization------初始化--BeanPostProcessor.postProcessAfterInitialization--销毁的证据
 *      
 *      //BeanPostProcessor的原理
 *      populateBean(beanName,mbd,instanceWrapper);//给bean进行属性赋值
 *      initializeBean()//执行自定义初始化{
 *      遍历得到容器中所有的beanPostProcessor：挨个执行beforeInitialization.一旦返回null，跳出for循环，不会执行后面的BeanPostProcessor.PostProcessorBeforeInitialization
 *      invokeInitMethods(beanName,wrappedBean,mbd);执行初始化
 *      applyBeanPostProcessorsAfterInitialization(wrappedBean,beanName);
 *      }
 *      
 * 
 *      
 *      
 *      容器管理bean的生命周期：我们可以自定义初始化和销毁方法，容器在bean进行到当前生命周期的时候来调用我们自定义的初始化和销毁方法
 *      1、指定初始化销毁方法（xml中可以配置init-method和destory-method方法）
 *      2、通过让Bean实现InitializingBean(定义初始化逻辑)，DisposableBean(定义销毁逻辑)
 *      3、可以使用JSR250规范，@PostConstruct   在bean创建完成并且属性赋值完成，来执行初始化。   @PreDestroy 在容器销毁bean之前通知我们进行清理工作
 *      4、interface  BeanPostProcessor,bean的后置处理器     在bean初始化前后进行一些处理工作   
 *          postProcessBeforeInitialization；在初始化之前工作
 *          postProcessAfterInitialization：在初始化之后工作
 *          
 *      spring底层对BeanPostProcessor的使用
 *      
 *      ApplicationContextAwareProcessor帮我们组件里面注入ioc容器，比如：com.facetofront.domain.Dog组件
 *       
 *       只要实现ApplicationContextAwareProcessor的接口
 *       public class Dog implements ApplicationContextAwareProcessor {
 *            
 *             
 *            private ApplicationContext applicationContext;
 *             
 *            public void setApplicationContext(ApplicationContext applicationContext){
 *                this.applicationContext=applicationContext;
 *            }
 *       
 *       }
 *       
 *      BeanValidationPostProcessor  数据校验   
 *      对象创建完之后，给javabean赋值的时候，进行校验
 *      
 *      InitDestroyAnnotationBeanPostProcessor
 *      
 *      处理PostConstruct 和PreDestroy    com.facetofront.domain.Dog中的注解，就是因为这个
 *      
 *      AutowiredAnnotationBeanPostProcessor
 *      
 *      bean赋值，注入其他组件  @Autowired生命周期注解功能   @Async(异步方法),XXXXXX 
 *      
 *         
 * 构造（对象创建）
 *     单实例：在容器启动的时候创建对象     
 *     多实例：在每次获取的时候创建对象
 *初始化： 对象创建完成，并赋值好。调用初始化方法。。。。     
 *销毁：容器关闭的时候(多实例的时候，容器不帮助做销毁的操作)
 * @author wangl
 *
 */
@ComponentScan("com.facetofront.domain")
@Configuration
public class MainConfigOfLifeCycle {
	 
	 //@Scope("prototype")
	 @Bean(initMethod="init",destroyMethod="destory")
	 public Car car(){
		 return new Car();
	 }
	
	
	
	
	   

}
