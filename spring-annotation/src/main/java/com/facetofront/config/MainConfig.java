package com.facetofront.config;

import java.lang.annotation.Repeatable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.facetofront.domain.Person;
//配置类==配置文件
@Configuration   //告诉spring这是一个配置类 
@ComponentScan(value="com.facetofront")  //  相当于 <context:component-scan base-package="com.facetofront"></context:component-scan>这个扫描器
//@ComponentScan(value="com.facetofront"，excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})} ) value :指定要扫描的包   excludeFilters：排除要扫描的包
//@includeFilters(value="com.facetofront"，excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})},userDefaulters=false )
//@ComponentScan是重复注解，如果是jdk8的话，可以使用多次，一个类中，@Repeatable(ComponentScans.class)，如果是jdk1.7的话可以使用@ComponentScans指定扫描策略，里面的value是重复数组。
//FilterType.ANNOTATION:按照注解
//FilterType.ASSIGNABLE_TYPE:按照给定的类型
//FilterType.ASPECTJ：使用ASPECTJ表达式
//FilterType.REGEX：使用正则指定
//FilterType.CUSTOM：使用自定义规则   @Filter(type=FilterType.CUSTOM,classes={MyTypeFilter.class})
public class MainConfig {
      
	
	  /**
	   *  懒加载：容器启动的时候，不创建对象，第一次使用bean的时候，创建对象，并初始化。
	   *      单实例bean；默认在容器启动的时候创建对象
	   * @return
	   */
	  //@Lazy
	  @Bean("person")
	  public Person person01(){
		  return new Person("lili",20);
	  }
	  
	  
	  /**
	  * @conditional({}):按照一定的条件进行哦按段，满足条件给容器中注册bean
	  * 
	  * 如果系统是windows，放bill，如果是linux,放linux
	  * 
	  */
	 @Bean("bill") 
	 public Person person1(){
		 return new Person("Bill Gates",62);
	 }
	 
	 
	 @Bean("linux") 
	 public Person person2(){
		 return new Person("linux",62);
	 }
}
