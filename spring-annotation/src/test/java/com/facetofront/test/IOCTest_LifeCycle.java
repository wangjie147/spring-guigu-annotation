package com.facetofront.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.facetofront.config.MainConfigOfLifeCycle;

public class IOCTest_LifeCycle {
	
	  @Test
	  public void test01(){
		  //����ioc����
		  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
	      System.out.println("============����=============");
	      //�����رյ�ʱ��
	      applicationContext.close();
	  
	  }

}
