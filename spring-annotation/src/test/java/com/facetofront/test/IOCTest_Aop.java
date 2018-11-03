package com.facetofront.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.facetofront.aop.MathCalculator;
import com.facetofront.config.MainConfigOfAop;
import com.facetofront.service.BookService;

public class IOCTest_Aop {
	
	  @Test
	  public void test01(){
		  //创建ioc容器
		  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAop.class);
		  System.out.println("============创建=============");
	      MathCalculator bean = applicationContext.getBean(MathCalculator.class);
	      bean.div(1,1);
	      //容器关闭的时候
	  }
	  

}
