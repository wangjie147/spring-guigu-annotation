package com.facetofront.method;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.facetofront.config.MainConfig;
import com.facetofront.domain.Person;

public class Test {
	
	public static void main(String[] args) {
		
		ApplicationContext applica=new ClassPathXmlApplicationContext("beans.xml");
		Person bean = (Person) applica.getBean("person");
		System.out.println(bean);
		
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
		
		Person configBean = (Person) applicationContext.getBean("person");
		
		System.out.println(configBean);
		
		String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
		
		for(String beanName:beanNamesForType){
			System.out.println(beanName);
		}
		
	}

}
