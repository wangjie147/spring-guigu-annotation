package com.facetofront.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

//�ж��Ƿ�linuxϵͳ
public class LinuxCondition implements Condition{

	/**
	 * context:�ж�������ʹ�õ������ģ���������
	 * 
	 * metadata:ע����Ϣ��
	 * 
	 */
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		//�ж��Ƿ���linuxϵͳ
		//1���ܻ�ȡ��ioc��ǰʹ�õ�bean������beanFactory��
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		//2����ȡ�������
		ClassLoader classLoader = context.getClassLoader();
		//org.springframework.core.env;��װһЩ����ʱ�ı���,�������һЩ��������ȡ����Ƿ������Ϣ
		Environment environment = context.getEnvironment();
		BeanDefinitionRegistry registry = context.getRegistry();//��ȡ��bean�����ע����   �����Ƿ�bean����������ж��壬ע��bean�Ķ���
		//�жϲ���ϵͳ����Environment
		String property = environment.getProperty("os.name");
		if(property.contains("linux")){
			return true;
		}
		return false;
	}

}
