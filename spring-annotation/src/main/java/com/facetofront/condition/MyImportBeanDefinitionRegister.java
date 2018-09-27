package com.facetofront.condition;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar{
    
	/**
	 * 
	 * AnnotationMetadata:��ǰ���ע����Ϣ
	 * 
	 * BeanDefinitionRegistry:BeanDefinition��ע����
	 *     ��������Ҫ��ӵ������е�bean�������������BeanDefinitionRegistry��registerBeanDefinition  �Զ���  �ֹ�ע��
	 */
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		   boolean definition = registry.containsBeanDefinition("com.facetofront.domain.Blue");
		   boolean definition2 = registry.containsBeanDefinition("com.facetofront.domain.Red");
		   if(definition&&definition2){
			   //ָ��bean������Ϣ����������bean�����ͣ�bean��������
			   RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
			   //ע��һ��bean
			   registry.registerBeanDefinition("rainBow", rootBeanDefinition);
		   }
	}

}
