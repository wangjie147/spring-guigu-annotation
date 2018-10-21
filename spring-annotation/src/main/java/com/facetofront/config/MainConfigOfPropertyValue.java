package com.facetofront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.facetofront.domain.Girl;

//ʹ��@PropertySource��ȡ�ⲿ�����ļ���k/v,���浽���еĻ���������,�������ⲿ�������ļ��Ժ�ʹ��${}��ȡ�����ļ���ֵ
@PropertySource(value={"classpath:/propertyvalue.properties"})
@Configuration
public class MainConfigOfPropertyValue {
	
	    @Bean
	    public Girl girl(){
	    	return new Girl();
	    }

}
