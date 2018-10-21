package com.facetofront.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.facetofront.config.MainConfigOfPropertyValue;
import com.facetofront.domain.Girl;

public class IOCTest_PropertyValue {
	
	  @Test
	  public void test01(){
		  //����ioc����
		  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValue.class);
	      System.out.println("============����=============");
	      String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
	      for(String name:beanDefinitionNames){
	    	  System.out.println(name);
	      }
	      Girl girl =(Girl) applicationContext.getBean("girl");
	      System.out.println(girl);
	      
	      ConfigurableEnvironment environment = applicationContext.getEnvironment();
	      
	      String property = environment.getProperty("person.sex");
	      System.out.println("property��"+property);
	      //�����رյ�ʱ��
	      applicationContext.close();
	  }

}
