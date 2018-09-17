package com.facetofront.test;

import java.util.Map;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.facetofront.config.MainConfig;
import com.facetofront.domain.Person;

public class IocTest {
	
	  @Test
	  public void test01(){
		  
		  AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext(MainConfig.class);
		  
		  String[] definitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
		  
		  for(String definitionName:definitionNames){
			  System.out.println(definitionName);
		  }
		  
	  }
	  
	  @Test
	  public void test02(){
		  
		  AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext(MainConfig.class);
		  
		  String[] definitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
		  
		 /* for(String definitionName:definitionNames){
			  System.out.println(definitionName);
		  }*/
		  ConfigurableEnvironment environment = annotationConfigApplicationContext.getEnvironment();
		  String property = environment.getProperty("os.name");
		  System.out.println(property);
		  
		  Map<String, Person> persons = annotationConfigApplicationContext.getBeansOfType(Person.class);
		  
		  System.out.println(persons);
		  
	  }
	  
	  
	  

}
