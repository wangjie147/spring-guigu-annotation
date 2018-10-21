package com.facetofront.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.facetofront.config.MainConfigOfAutowired;
import com.facetofront.dao.BookDao;
import com.facetofront.service.BookService;

public class IOCTest_Autowired {
	
	  @Test
	  public void test01(){
		  //创建ioc容器
		  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
	      System.out.println("============创建=============");
	      BookService bean = applicationContext.getBean(BookService.class);
	      System.out.println(bean);
	      BookService bookService = bean;
	      System.out.println(bookService);
	      //容器关闭的时候
	      applicationContext.close();
	  
	  }

}
