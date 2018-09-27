package com.facetofront.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.facetofront.config.MainConfigOfLifeCycle;

public class IOCTest_LifeCycle {
	
	  @Test
	  public void test01(){
		  //创建ioc容器
		  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
	      System.out.println("============创建=============");
	      //容器关闭的时候
	      applicationContext.close();
	  
	  }

}
