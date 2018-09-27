package com.facetofront.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WindowCondition implements Condition{

	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		        //�ж��Ƿ���linuxϵͳ
				//1���ܻ�ȡ��ioc��ǰʹ�õ�bean������beanFactory��
				ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
				//2����ȡ�������
				ClassLoader classLoader = context.getClassLoader();
				//org.springframework.core.env;��װһЩ����ʱ�ı���,�������һЩ��������ȡ����Ƿ������Ϣ
				Environment environment = context.getEnvironment();
				BeanDefinitionRegistry registry = context.getRegistry();//��ȡ��bean�����ע����   �����Ƿ�bean����������ж��壬ע��bean�Ķ���
				
				//Ҳ�����ж�registry�Ƿ���personע�����      �����ж������е�bean��ע�������Ҳ���Ը�������ע��bean
				boolean definition = registry.containsBeanDefinition("person");
				//û�еĻ�������ע��һ����ȥ registry.registerBeanDefinition(beanName, beanDefinition);
				//�жϲ���ϵͳ����Environment
				String property = environment.getProperty("os.name");
				if(property.contains("Windows")){
					return true;
				}
				return false;
	}

}
