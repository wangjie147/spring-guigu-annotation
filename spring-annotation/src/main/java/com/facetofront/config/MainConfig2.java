package com.facetofront.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.facetofront.domain.Person;

@Configuration
public class MainConfig2 {
	
	/*ConfigurableBeanFactory#SCOPE_PROTOTYPE   多实例  ioc容器启动并没有调用方法创建对象放在容器中，每次获取的时候才会调用方法创建对象，获取几次调用几次。
	  ConfigurableBeanFactory#SCOPE_SINGLETON   单实例 （默认值）  ioc容器启动会调用方法，创建对象放到ioc容器中。以后每次获取，直接从容器中拿
      org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST  同一次请求创建一个实例
	  org.springframework.web.context.WebApplicationContext#SCOPE_SESSION  同一个session创建一个实例
	  */
	 @Scope("prototype")
	 @Bean("person")
	  public Person person01(){
		  return new Person("zhnagsan",20);
	  }
	   
	 
	 
	 
	 
	 
	 

}
