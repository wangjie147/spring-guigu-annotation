package com.facetofront.domain;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;


/**
 * 
 * ���ô���������ʼ��ǰ����д�����
 * �����ô��������뵽������
 * @author wangl
 *
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("****************************************************");
		System.out.println("postProcessBeforeInitialization........"+beanName+"=>"+bean);
		System.out.println("****************************************************");
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("****************************************************");
		System.out.println("postProcessAfterInitialization........"+beanName+"=>"+bean);
		System.out.println("****************************************************");
		return bean;
	}

}
