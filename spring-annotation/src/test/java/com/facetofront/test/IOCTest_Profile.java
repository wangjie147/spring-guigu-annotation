package com.facetofront.test;

import javax.activation.DataSource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.facetofront.config.MainConfigOfProfile;

public class IOCTest_Profile {
	
	  //1、使用命令行动态参数：在虚拟机参数的位置加载-Dspring.profiles.active=test
	  /* @Test
	     public void test01(){
		  //创建ioc容器
		  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
		  String[] beanNamesForType = applicationContext.getBeanNamesForType(DataSource.class);
		  for(int i=0;i<beanNamesForType.length;i++){
			  System.out.println(beanNamesForType[i]);
		  }
	      applicationContext.close();
	  }*/
	  
	
	 //2\代码的方式激活某种环境
	  @Test
	  public void test01(){
		  //1、创建一个ApplicationContext的
		  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		  //2、设置需要激活的环境
		  applicationContext.getEnvironment().setActiveProfiles("test","dev");
		  //3、注册主配置类
		  applicationContext.register(MainConfigOfProfile.class);
		  //4、启动刷新容器
		  applicationContext.refresh();
		  
		  /*
		   * 有参构造器
		   * 
		  public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
				this();无参创建对象
				register(annotatedClasses);注册
				refresh();刷新
			}*/
		  String[] beanNamesForType = applicationContext.getBeanNamesForType(DataSource.class);
		  for(int i=0;i<beanNamesForType.length;i++){
			  System.out.println(beanNamesForType[i]);
		  }
	      applicationContext.close();
	      
	      
	  
	  }

}
