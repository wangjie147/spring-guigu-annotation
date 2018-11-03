package com.facetofront.test;

import javax.activation.DataSource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.facetofront.config.MainConfigOfProfile;

public class IOCTest_Profile {
	
	  //1��ʹ�������ж�̬�������������������λ�ü���-Dspring.profiles.active=test
	  /* @Test
	     public void test01(){
		  //����ioc����
		  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
		  String[] beanNamesForType = applicationContext.getBeanNamesForType(DataSource.class);
		  for(int i=0;i<beanNamesForType.length;i++){
			  System.out.println(beanNamesForType[i]);
		  }
	      applicationContext.close();
	  }*/
	  
	
	 //2\����ķ�ʽ����ĳ�ֻ���
	  @Test
	  public void test01(){
		  //1������һ��ApplicationContext��
		  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		  //2��������Ҫ����Ļ���
		  applicationContext.getEnvironment().setActiveProfiles("test","dev");
		  //3��ע����������
		  applicationContext.register(MainConfigOfProfile.class);
		  //4������ˢ������
		  applicationContext.refresh();
		  
		  /*
		   * �вι�����
		   * 
		  public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
				this();�޲δ�������
				register(annotatedClasses);ע��
				refresh();ˢ��
			}*/
		  String[] beanNamesForType = applicationContext.getBeanNamesForType(DataSource.class);
		  for(int i=0;i<beanNamesForType.length;i++){
			  System.out.println(beanNamesForType[i]);
		  }
	      applicationContext.close();
	      
	      
	  
	  }

}
