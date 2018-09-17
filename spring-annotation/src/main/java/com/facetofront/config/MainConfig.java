package com.facetofront.config;

import java.lang.annotation.Repeatable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.facetofront.domain.Person;
//������==�����ļ�
@Configuration   //����spring����һ�������� 
@ComponentScan(value="com.facetofront")  //  �൱�� <context:component-scan base-package="com.facetofront"></context:component-scan>���ɨ����
//@ComponentScan(value="com.facetofront"��excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})} ) value :ָ��Ҫɨ��İ�   excludeFilters���ų�Ҫɨ��İ�
//@includeFilters(value="com.facetofront"��excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})},userDefaulters=false )
//@ComponentScan���ظ�ע�⣬�����jdk8�Ļ�������ʹ�ö�Σ�һ�����У�@Repeatable(ComponentScans.class)�������jdk1.7�Ļ�����ʹ��@ComponentScansָ��ɨ����ԣ������value���ظ����顣
//FilterType.ANNOTATION:����ע��
//FilterType.ASSIGNABLE_TYPE:���ո���������
//FilterType.ASPECTJ��ʹ��ASPECTJ���ʽ
//FilterType.REGEX��ʹ������ָ��
//FilterType.CUSTOM��ʹ���Զ������   @Filter(type=FilterType.CUSTOM,classes={MyTypeFilter.class})
public class MainConfig {
      
	
	  /**
	   *  �����أ�����������ʱ�򣬲��������󣬵�һ��ʹ��bean��ʱ�򣬴������󣬲���ʼ����
	   *      ��ʵ��bean��Ĭ��������������ʱ�򴴽�����
	   * @return
	   */
	  //@Lazy
	  @Bean("person")
	  public Person person01(){
		  return new Person("lili",20);
	  }
	  
	  
	  /**
	  * @conditional({}):����һ������������Ŷ���Σ�����������������ע��bean
	  * 
	  * ���ϵͳ��windows����bill�������linux,��linux
	  * 
	  */
	 @Bean("bill") 
	 public Person person1(){
		 return new Person("Bill Gates",62);
	 }
	 
	 
	 @Bean("linux") 
	 public Person person2(){
		 return new Person("linux",62);
	 }
}
