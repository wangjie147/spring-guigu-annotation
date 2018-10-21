package com.facetofront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.facetofront.domain.Car;

/**
 * 
 * bean ���������ڣ�
 *      bean�Ĵ���--BeanPostProcessor.postProcessBeforeInitialization------��ʼ��--BeanPostProcessor.postProcessAfterInitialization--���ٵ�֤��
 *      
 *      //BeanPostProcessor��ԭ��
 *      populateBean(beanName,mbd,instanceWrapper);//��bean�������Ը�ֵ
 *      initializeBean()//ִ���Զ����ʼ��{
 *      �����õ����������е�beanPostProcessor������ִ��beforeInitialization.һ������null������forѭ��������ִ�к����BeanPostProcessor.PostProcessorBeforeInitialization
 *      invokeInitMethods(beanName,wrappedBean,mbd);ִ�г�ʼ��
 *      applyBeanPostProcessorsAfterInitialization(wrappedBean,beanName);
 *      }
 *      
 * 
 *      
 *      
 *      ��������bean���������ڣ����ǿ����Զ����ʼ�������ٷ�����������bean���е���ǰ�������ڵ�ʱ�������������Զ���ĳ�ʼ�������ٷ���
 *      1��ָ����ʼ�����ٷ�����xml�п�������init-method��destory-method������
 *      2��ͨ����Beanʵ��InitializingBean(�����ʼ���߼�)��DisposableBean(���������߼�)
 *      3������ʹ��JSR250�淶��@PostConstruct   ��bean������ɲ������Ը�ֵ��ɣ���ִ�г�ʼ����   @PreDestroy ����������bean֮ǰ֪ͨ���ǽ���������
 *      4��interface  BeanPostProcessor,bean�ĺ��ô�����     ��bean��ʼ��ǰ�����һЩ������   
 *          postProcessBeforeInitialization���ڳ�ʼ��֮ǰ����
 *          postProcessAfterInitialization���ڳ�ʼ��֮����
 *          
 *      spring�ײ��BeanPostProcessor��ʹ��
 *      
 *      ApplicationContextAwareProcessor�������������ע��ioc���������磺com.facetofront.domain.Dog���
 *       
 *       ֻҪʵ��ApplicationContextAwareProcessor�Ľӿ�
 *       public class Dog implements ApplicationContextAwareProcessor {
 *            
 *             
 *            private ApplicationContext applicationContext;
 *             
 *            public void setApplicationContext(ApplicationContext applicationContext){
 *                this.applicationContext=applicationContext;
 *            }
 *       
 *       }
 *       
 *      BeanValidationPostProcessor  ����У��   
 *      ���󴴽���֮�󣬸�javabean��ֵ��ʱ�򣬽���У��
 *      
 *      InitDestroyAnnotationBeanPostProcessor
 *      
 *      ����PostConstruct ��PreDestroy    com.facetofront.domain.Dog�е�ע�⣬������Ϊ���
 *      
 *      AutowiredAnnotationBeanPostProcessor
 *      
 *      bean��ֵ��ע���������  @Autowired��������ע�⹦��   @Async(�첽����),XXXXXX 
 *      
 *         
 * ���죨���󴴽���
 *     ��ʵ����������������ʱ�򴴽�����     
 *     ��ʵ������ÿ�λ�ȡ��ʱ�򴴽�����
 *��ʼ���� ���󴴽���ɣ�����ֵ�á����ó�ʼ��������������     
 *���٣������رյ�ʱ��(��ʵ����ʱ�����������������ٵĲ���)
 * @author wangl
 *
 */
@ComponentScan("com.facetofront.domain")
@Configuration
public class MainConfigOfLifeCycle {
	 
	 //@Scope("prototype")
	 @Bean(initMethod="init",destroyMethod="destory")
	 public Car car(){
		 return new Car();
	 }
	
	
	
	
	   

}
