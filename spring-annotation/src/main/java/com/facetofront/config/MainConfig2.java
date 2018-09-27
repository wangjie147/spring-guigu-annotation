package com.facetofront.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.facetofront.domain.Person;

@Configuration
public class MainConfig2 {
	
	/*ConfigurableBeanFactory#SCOPE_PROTOTYPE   ��ʵ��  ioc����������û�е��÷�������������������У�ÿ�λ�ȡ��ʱ��Ż���÷����������󣬻�ȡ���ε��ü��Ρ�
	  ConfigurableBeanFactory#SCOPE_SINGLETON   ��ʵ�� ��Ĭ��ֵ��  ioc������������÷�������������ŵ�ioc�����С��Ժ�ÿ�λ�ȡ��ֱ�Ӵ���������
      org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST  ͬһ�����󴴽�һ��ʵ��
	  org.springframework.web.context.WebApplicationContext#SCOPE_SESSION  ͬһ��session����һ��ʵ��
	  */
	 @Scope("prototype")
	 @Bean("person")
	  public Person person01(){
		  return new Person("zhnagsan",20);
	  }
	   
	 
	 
	 
	 
	 
	 

}
