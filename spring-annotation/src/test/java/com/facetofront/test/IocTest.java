package com.facetofront.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.facetofront.config.MainConfig;

public class IocTest {
	
	  @Test
	  public void test01(){
		  
		  AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext(MainConfig.class);
		  
		  String[] definitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
		  
		  for(String definitionName:definitionNames){
			  System.out.println(definitionName);
		  }
		  
	  }

}
