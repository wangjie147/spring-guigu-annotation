package com.facetofront.test;

import javax.activation.DataSource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.facetofront.config.MainConfigOfProfile;

public class IOCTest_Profile {
	
	  @Test
	  public void test01(){
		  //´´½¨iocÈÝÆ÷
		  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
		  String[] beanNamesForType = applicationContext.getBeanNamesForType(DataSource.class);
		  for(int i=0;i<beanNamesForType.length;i++){
			  System.out.println(beanNamesForType[i]);
		  }
	      applicationContext.close();
	  
	  }

}
