package com.facetofront.config;

import java.lang.annotation.Repeatable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

import com.facetofront.domain.Person;
//配置类==配置文件
@Configuration   //告诉spring这是一个配置类 
@ComponentScan(value="com.facetofront")  //  相当于 <context:component-scan base-package="com.facetofront"></context:component-scan>这个扫描器
//@ComponentScan(value="com.facetofront"，excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})} ) value :指定要扫描的包   excludeFilters：排除要扫描的包
//@includeFilters(value="com.facetofront"，excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})},userDefaulters=false )
//@ComponentScan是重复注解，如果是jdk8的话，可以使用多次，一个类中，@Repeatable(ComponentScans.class)，如果是jdk1.7的话可以使用@ComponentScans指定扫描策略，里面的value是重复数组。
public class MainConfig {
      
	  @Bean("person")
	  public Person person01(){
		  return new Person("lili",20);
	  }
}
