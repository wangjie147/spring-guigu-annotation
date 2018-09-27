package com.facetofront.test;

import java.util.Map;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.facetofront.config.MainConfig;
import com.facetofront.domain.Blue;
import com.facetofront.domain.Person;

public class IocTest {
	
	  AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext(MainConfig.class);
	
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
	  
	  private void printBeans(AnnotationConfigApplicationContext annotationConfigApplicationContext){
		  String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
		  for(String definitionName:beanDefinitionNames){
			  System.out.println(definitionName);
		  }
	  }
	  
	  
	  @Test
	  public void testImport(){
		  printBeans(annotationConfigApplicationContext);
		  Blue bean = annotationConfigApplicationContext.getBean(Blue.class);
		  System.out.println(bean);
		  //工厂bean获取的是调用getObject创建的对象
		  Object bean2 = annotationConfigApplicationContext.getBean("colorFactoryBean");
		  Object bean33 = annotationConfigApplicationContext.getBean("colorFactoryBean");
		  Object bean332= annotationConfigApplicationContext.getBean("&colorFactoryBean");
		  System.out.println("colorFactoryBean的类型："+bean2.getClass());
		  System.out.println(bean2==bean33);
		  System.out.println(bean332.getClass());
	  }
	  
	  

}
