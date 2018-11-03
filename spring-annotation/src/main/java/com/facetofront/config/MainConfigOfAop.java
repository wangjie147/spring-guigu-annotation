package com.facetofront.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.facetofront.aop.LogAspect;
import com.facetofront.aop.MathCalculator;

/**
 * 
 * AOP:����̬ ����
 *     ָ�ڳ��������ڼ䶯̬�Ľ�ĳ�δ������뵽 ָ�� ����ָ��λ�ý������еı�̷�ʽ��
 *  1�� ����aopģ�飺spring aop ��spring-aspects��
 *  2��  ����һ��ҵ���߼��ࣨMathCalculator������ҵ���߼����е�ʱ����־���д�ӡ������֮ǰ���������н��������������쳣��
 *  3�� ����һ����־�����ࣨLogAspect��������������ķ�����Ҫ��̬��֪MathCalculator.div���е�����Ȼ��ִ��   
 *      ֪ͨ������
 *            ǰ��֪ͨ��logStart  ��Ŀ�귽����div������֮ǰ����      @Before
 *            ����֪ͨ��logEnd  ��Ŀ�귽����div�����н���֮������    @After   �����������Ľ��������쳣�Ľ����������������
 *            ����֪ͨ��logReturn  ��Ŀ�귽����div����������֮������   @AfterReturning
 *            �쳣֪ͨ��logException   ��Ŀ�귽����div�������쳣֮������    @AfterThrowing
 *            ����֪ͨ����̬�����ֶ��ƽ�Ŀ�귽�����У�joinPoint��procced()�� @Around
 *  4�����������Ŀ�귽����ע��ʱ�ε����У�֪ͨע�⣩
 *  5�����������ҵ���߼��ࣨĿ���������ࣩ�����뵽������
 *  6���������spring��Щ���������ࣨ���������һ��ע��@Aspect,����spring�������һ�������ࣩ
 *  7�����������м�@EnableAspectJAutoProxy����������ע���aopģʽ��
 *    ��spring�л��кܶ�@EnableXXXX
 *   ע�⣺��ҵ���߼������������ �����뵽�����У�����spring�ĸ��������ࣨ@Aspect�������������ϵ�ÿһ��֪ͨ�����ϱ�ע֪ͨ������spring��ʱ�ε����У��������ʽ��
 *       ��������ע���aopģʽ��@EnableAspectJAutoProxy
 *  aopԭ��     @EnableAspectJAutoProxy
 *       @Import(AspectJAutoProxyRegistrar.class) @Import�������е������
 *       ImportBeanDefinitionRegistrar  �Զ��� ע�����
 *          �鿴MainConfig.java�е�����
 *          ����AspectJAutoProxyRegistrar�Զ����������ע��bean
 *          class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {
                @Override
				public void registerBeanDefinitions(
						AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
			        
			        //�����Ҫ�Ļ���ע�����AspectJAnnotationAutoProxyCreator���
					AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);
			
					AnnotationAttributes enableAspectJAutoProxy =
							AnnotationConfigUtils.attributesFor(importingClassMetadata, EnableAspectJAutoProxy.class);
					if (enableAspectJAutoProxy.getBoolean("proxyTargetClass")) {
						AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
					}
					if (enableAspectJAutoProxy.getBoolean("exposeProxy")) {
						AopConfigUtils.forceAutoProxyCreatorToExposeProxy(registry);
					}
				}
 *          }
 * @author wangl
 *
 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAop {
	   
	   //��ҵ��������ӵ�������
	   @Bean
	   public MathCalculator calculator(){
		   return new MathCalculator();
	   }
       
	   
	   //��������Ҳ���뵽��������
	   @Bean
	   public LogAspect logAspect(){
		   return new LogAspect();
	   }
	   
	   
	   
}
