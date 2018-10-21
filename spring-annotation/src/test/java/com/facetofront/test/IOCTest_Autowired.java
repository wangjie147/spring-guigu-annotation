package com.facetofront.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.facetofront.config.MainConfigOfAutowired;
import com.facetofront.dao.BookDao;
import com.facetofront.service.BookService;

public class IOCTest_Autowired {
	
	  @Test
	  public void test01(){
		  //����ioc����
		  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
	      System.out.println("============����=============");
	      BookService bean = applicationContext.getBean(BookService.class);
	      System.out.println(bean);
	      BookService bookService = bean;
	      System.out.println(bookService);
	      //�����رյ�ʱ��
	      applicationContext.close();
	  
	  }

}
