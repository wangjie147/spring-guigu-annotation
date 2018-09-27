package com.facetofront.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WindowCondition implements Condition{

	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		        //判断是否是linux系统
				//1、能获取到ioc当前使用的bean工厂（beanFactory）
				ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
				//2、获取类加载器
				ClassLoader classLoader = context.getClassLoader();
				//org.springframework.core.env;封装一些运行时的变量,虚拟机的一些变量。获取当还欠环境信息
				Environment environment = context.getEnvironment();
				BeanDefinitionRegistry registry = context.getRegistry();//获取到bean定义的注册类   查找是否bean在这里面进行定义，注册bean的定义
				
				//也可以判断registry是否有person注册进来      可以判断容器中的bean的注册情况，也可以给容器中注册bean
				boolean definition = registry.containsBeanDefinition("person");
				//没有的话，可以注册一个进去 registry.registerBeanDefinition(beanName, beanDefinition);
				//判断操作系统，用Environment
				String property = environment.getProperty("os.name");
				if(property.contains("Windows")){
					return true;
				}
				return false;
	}

}
