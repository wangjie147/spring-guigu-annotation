package com.facetofront.condition;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar{
    
	/**
	 * 
	 * AnnotationMetadata:当前类的注解信息
	 * 
	 * BeanDefinitionRegistry:BeanDefinition的注册类
	 *     把所有需要添加到容器中的bean：调用这个方法BeanDefinitionRegistry。registerBeanDefinition  自定义  手工注册
	 */
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		   boolean definition = registry.containsBeanDefinition("com.facetofront.domain.Blue");
		   boolean definition2 = registry.containsBeanDefinition("com.facetofront.domain.Red");
		   if(definition&&definition2){
			   //指定bean定义信息，，，，（bean的类型，bean的作用域）
			   RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
			   //注册一个bean
			   registry.registerBeanDefinition("rainBow", rootBeanDefinition);
		   }
	}

}
