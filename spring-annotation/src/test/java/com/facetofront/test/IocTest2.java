package com.facetofront.test;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.facetofront.config.MainConfig2;

public class IocTest2 {
	
	  @Test
	  public void test01(){
		  
		  AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext(MainConfig2.class);
		  
		  String[] definitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
		  
		  for(String definitionName:definitionNames){
			  System.out.println(definitionName);
		  }
		  //默认是单实例
		   Object bean = annotationConfigApplicationContext.getBean("person");
		   
		   Object bean2 = annotationConfigApplicationContext.getBean("person");
		   
		   System.out.println(bean ==bean2);
		   
	  }

}
