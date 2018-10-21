package com.facetofront.config;

import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.facetofront.dao.BookDao;
import com.facetofront.domain.Car;
import com.facetofront.domain.Color;


@Configuration
@ComponentScan({"com.facetofront.service","com.facetofront.dao","com.facetofront.controller","com.facetofront.domain"})
public class MainConfigOfAutowired {
	   
	  /**
	   *  �Զ�װ��:
	   *     spring��������ע�루DI��,��ɶ�ioc�����и��������������ϵ��ֵ��
 	   *     1\@Autowired  �Զ�װ��     Ĭ�����Ȱ�������ȥ�������Ҷ�Ӧ�������applicationContext.getBean(BookDao.class);
 	   *     2������ҵ������ͬ���͵�������ٽ����Ե�������Ϊ�����idȥ�����в��ң�applicationContext.getBean("bookDao");
 	   *        BookService {
				   @Autowired
				   private BookDao bookDao;
 	   *        }
 	   *     3\ʹ��@Qualifier("bookDao2")��ʹ��@Qualifierָ����Ҫװ��������id��������ʹ��������
	         4���Զ�װ��    Ĭ��һ��Ҫ�����Ը�ֵ�á�û�оͻᱨ��
	                              Ҳ����ʹ������Ͳ��ᱨ����@Autowired(required=false)
	         5\  @Primary ����spring���ж�װ���ʱ��Ĭ��������ѡ��bean��
	                                Ҳ���Լ���ʹ��@Qualifier�ƶ���Ҫװ���bean������
	     spring��֧��ʹ��@Resource(JSR250)��@Inject(@JSR330)�Ĺ淶      java�Ĺ淶                    
	              @Resource 
	                                                ���Ժ�  @Autowiredһ��ʵ���Զ�װ�书�� ��Ĭ���ǰ���������ƽ���װ�䣬
	                                                 û����֧��@Primary���� û��֧��   @Autowired(required=false) 
	               @Inject  ��Ҫ��������
	               <dependency>
					    <groupId>javax.inject</groupId>
					    <artifactId>javax.inject</artifactId>
					    <version>1</version>
					</dependency>    
					��   @Autowired�Ĺ���һ����û��  required=false�Ĺ���  
           AutowiredAnnotationBeanPostProcessor�� ��������Զ�װ�书��
                 
           @Autowired�������������������������ԣ����Ǵ������л�ȡ���������ֵ
                   1����ע�ڷ�����λ�ã�@Bean+���������������������л�ȡ��Ĭ�ϲ�д@AutowiredЧ������һ���ģ������Զ�װ�䣩
                   2����ע�ڹ��������棨������ֻ��һ���вι�����������вι�������@Autowired����ʡ�ԣ�����λ�õ�������ǿ����Զ��������л�ȡ��
                   3�����ڲ�����λ��      
                   
          4\�Զ��������Ҫʹ��spring�ײ��һЩ�����ApplicationContext��beanfactory����    
                                    �Զ������ʵ��xxxAware���ڴ��������ʱ�򣬻���ýӿڹ涨�ķ���ע����������
                                    
             public interface Aware{} 
                                        ʵ���ࣺApplicationContextAware����red��Ϊ���ӣ�
                                                ��spring�ײ�һЩ���ע�뵽�Զ����bean�У�   
                  xxxAware�Ĺ��ܣ�ʹ�� xxxProcessor������                             
                           ApplicationContextAware===����ApplicationContextAwareProcessor �����ô������ڴ�����bean�Ժ󣬲�ʵ����xxxAware�ӿڣ��ͽ���ص������������     
                           ApplicationContextAwareProcessor���� postProcessBeforeInitialization 
		                         if (System.getSecurityManager() != null &&
										(bean instanceof EnvironmentAware || bean instanceof EmbeddedValueResolverAware ||
												bean instanceof ResourceLoaderAware || bean instanceof ApplicationEventPublisherAware ||
												bean instanceof MessageSourceAware || bean instanceof ApplicationContextAware)) {
									acc = this.applicationContext.getBeanFactory().getAccessControlContext();
								}  
								
                          invokeAwareInterfaces
                          private void invokeAwareInterfaces(Object bean) {
							if (bean instanceof Aware) {
								if (bean instanceof EnvironmentAware) {
									((EnvironmentAware) bean).setEnvironment(this.applicationContext.getEnvironment());
								}
								if (bean instanceof EmbeddedValueResolverAware) {
									((EmbeddedValueResolverAware) bean).setEmbeddedValueResolver(this.embeddedValueResolver);
								}
								if (bean instanceof ResourceLoaderAware) {
									((ResourceLoaderAware) bean).setResourceLoader(this.applicationContext);
								}
								if (bean instanceof ApplicationEventPublisherAware) {
									((ApplicationEventPublisherAware) bean).setApplicationEventPublisher(this.applicationContext);
								}
								if (bean instanceof MessageSourceAware) {
									((MessageSourceAware) bean).setMessageSource(this.applicationContext);
								}
								if (bean instanceof ApplicationContextAware) {
									((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
								}
							}
							}
                                 
                                 
	   */  
	   @Bean("bookDao2")
	   public BookDao bookDao(){
		   BookDao bookDao =new BookDao();
		   bookDao.setLabel("2");
		   return bookDao;
	   }
	   
	   /**
	    * 
	    * @Bean ��ע�ķ������������ʱ�򣬷���������ֵ�������л�ȡ
	    * @return
	    */
	   @Bean
	   public Color color(Car car){
		   Color color = new Color();
		   color.setCar(car);
		   return color;
	   }
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
}
