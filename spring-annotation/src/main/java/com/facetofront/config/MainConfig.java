package com.facetofront.config;

import java.lang.annotation.Repeatable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

import com.facetofront.domain.Person;
//������==�����ļ�
@Configuration   //����spring����һ�������� 
@ComponentScan(value="com.facetofront")  //  �൱�� <context:component-scan base-package="com.facetofront"></context:component-scan>���ɨ����
//@ComponentScan(value="com.facetofront"��excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})} ) value :ָ��Ҫɨ��İ�   excludeFilters���ų�Ҫɨ��İ�
//@includeFilters(value="com.facetofront"��excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})},userDefaulters=false )
//@ComponentScan���ظ�ע�⣬�����jdk8�Ļ�������ʹ�ö�Σ�һ�����У�@Repeatable(ComponentScans.class)�������jdk1.7�Ļ�����ʹ��@ComponentScansָ��ɨ����ԣ������value���ظ����顣
public class MainConfig {
      
	  @Bean("person")
	  public Person person01(){
		  return new Person("lili",20);
	  }
}
