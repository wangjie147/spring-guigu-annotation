package com.facetofront.config;

import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.facetofront.dao.BookDao;
import com.facetofront.domain.Car;
import com.facetofront.domain.Color;


@Configuration
@ComponentScan({"com.facetofront.service","com.facetofront.dao","com.facetofront.controller","com.facetofront.domain"})
public class MainConfigOfAutowired {
	   
	  /**
	   *  自动装配:
	   *     spring利用依赖注入（DI）,完成对ioc容器中各个组件的依赖关系赋值，
 	   *     1\@Autowired  自动装配     默认优先按照类型去容器中找对应的组件：applicationContext.getBean(BookDao.class);
 	   *     2、如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找：applicationContext.getBean("bookDao");
 	   *        BookService {
				   @Autowired
				   private BookDao bookDao;
 	   *        }
 	   *     3\使用@Qualifier("bookDao2")，使用@Qualifier指定需要装配的组件的id，而不是使用属性名
	         4、自动装配    默认一定要将属性赋值好。没有就会报错
	                              也可以使用这个就不会报错了@Autowired(required=false)
	         5\  @Primary ，让spring进行栋装配的时候，默认是用首选的bean，
	                                也可以继续使用@Qualifier制动需要装配的bean的名字
	     spring还支持使用@Resource(JSR250)和@Inject(@JSR330)的规范      java的规范                    
	              @Resource 
	                                                可以和  @Autowired一样实现自动装配功能 ，默认是按照组件名称进行装配，
	                                                 没有能支持@Primary功能 没有支持   @Autowired(required=false) 
	               @Inject  需要导入依赖
	               <dependency>
					    <groupId>javax.inject</groupId>
					    <artifactId>javax.inject</artifactId>
					    <version>1</version>
					</dependency>    
					和   @Autowired的功能一样，没有  required=false的功能  
           AutowiredAnnotationBeanPostProcessor： 解析完成自动装配功能
                 
           @Autowired：构造器、参数、方法、属性，都是从容器中获取参数组件的值
                   1、标注在方法的位置（@Bean+方法参数：参数从容器中获取，默认不写@Autowired效果都是一样的，都能自动装配）
                   2、标注在构造器上面（如果组件只有一个有参构造器：这个有参构造器的@Autowired可以省略，参数位置的组件还是可以自动从容器中获取）
                   3、放在参数的位置      
                   
          4\自定义组件想要使用spring底层的一些组件（ApplicationContext、beanfactory，）    
                                    自定义组件实现xxxAware，在创建对象的时候，会调用接口规定的方法注入相关组件。
                                    
             public interface Aware{} 
                                        实现类：ApplicationContextAware，以red类为例子：
                                                把spring底层一些最贱注入到自定义的bean中，   
                  xxxAware的功能：使用 xxxProcessor来处理。                             
                           ApplicationContextAware===》》ApplicationContextAwareProcessor （后置处理器在创建完bean以后，并实现了xxxAware接口，就将相关的组件传进来）     
                           ApplicationContextAwareProcessor类中 postProcessBeforeInitialization 
		                         if (System.getSecurityManager() != null &&
										(bean instanceof EnvironmentAware || bean instanceof EmbeddedValueResolverAware ||
												bean instanceof ResourceLoaderAware || bean instanceof ApplicationEventPublisherAware ||
												bean instanceof MessageSourceAware || bean instanceof ApplicationContextAware)) {
									acc = this.applicationContext.getBeanFactory().getAccessControlContext();
								}  
								
                          invokeAwareInterfaces
                          private void invokeAwareInterfaces(Object bean) {
							if (bean instanceof Aware) {
								if (bean instanceof EnvironmentAware) {
									((EnvironmentAware) bean).setEnvironment(this.applicationContext.getEnvironment());
								}
								if (bean instanceof EmbeddedValueResolverAware) {
									((EmbeddedValueResolverAware) bean).setEmbeddedValueResolver(this.embeddedValueResolver);
								}
								if (bean instanceof ResourceLoaderAware) {
									((ResourceLoaderAware) bean).setResourceLoader(this.applicationContext);
								}
								if (bean instanceof ApplicationEventPublisherAware) {
									((ApplicationEventPublisherAware) bean).setApplicationEventPublisher(this.applicationContext);
								}
								if (bean instanceof MessageSourceAware) {
									((MessageSourceAware) bean).setMessageSource(this.applicationContext);
								}
								if (bean instanceof ApplicationContextAware) {
									((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
								}
							}
							}
                                 
                                 
	   */  
	   @Bean("bookDao2")
	   public BookDao bookDao(){
		   BookDao bookDao =new BookDao();
		   bookDao.setLabel("2");
		   return bookDao;
	   }
	   
	   /**
	    * 
	    * @Bean 标注的方法创建对象的时候，方法参数的值从容器中获取
	    * @return
	    */
	   @Bean
	   public Color color(Car car){
		   Color color = new Color();
		   color.setCar(car);
		   return color;
	   }
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
}
