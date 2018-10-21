package com.facetofront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.facetofront.domain.Girl;

//使用@PropertySource读取外部配置文件的k/v,保存到运行的环境变量中,加载完外部的配置文件以后使用${}获取配置文件的值
@PropertySource(value={"classpath:/propertyvalue.properties"})
@Configuration
public class MainConfigOfPropertyValue {
	
	    @Bean
	    public Girl girl(){
	    	return new Girl();
	    }

}
