package com.facetofront.config;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator.BeanFactoryAspectJAdvisorsBuilderAdapter;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.ProxyProcessorSupport;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.BeanFactoryAdvisorRetrievalHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.Scope;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.FactoryBeanRegistrySupport;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.weaving.LoadTimeWeaverAware;
import org.springframework.core.Ordered;
import org.springframework.core.SimpleAliasRegistry;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringValueResolver;

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
 *       
 *       
 *       // Register bean processors that intercept bean creation.
		registerBeanPostProcessors(beanFactory);
 *       
 *       
 *       
 *  aopԭ��������������ע����ʲô�����������ʲôʱ����������������ʱ��Ĺ�����ʲô��
 *       @EnableAspectJAutoProxy
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
 *          
 *          public static BeanDefinition registerAspectJAnnotationAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry) {
				return registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry, null);
			}
		
			public static BeanDefinition registerAspectJAnnotationAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry, Object source) {
				return registerOrEscalateApcAsRequired(AnnotationAwareAspectJAutoProxyCreator.class, registry, source);
			}
			
			private static BeanDefinition registerOrEscalateApcAsRequired(Class<?> cls, BeanDefinitionRegistry registry, Object source) {
					Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
					if (registry.containsBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME)) {
						BeanDefinition apcDefinition = registry.getBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME);
						if (!cls.getName().equals(apcDefinition.getBeanClassName())) {
							int currentPriority = findPriorityForClass(apcDefinition.getBeanClassName());
							int requiredPriority = findPriorityForClass(cls);
							if (currentPriority < requiredPriority) {
								apcDefinition.setBeanClassName(cls.getName());
							}
						}
						return null;
					}
					RootBeanDefinition beanDefinition = new RootBeanDefinition(cls);//����Ҫ����
					beanDefinition.setSource(source);
					beanDefinition.getPropertyValues().add("order", Ordered.HIGHEST_PRECEDENCE);
					beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
					registry.registerBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME, beanDefinition);
					return beanDefinition;
				}
				internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
			��������ע��һ��	AnnotationAwareAspectJAutoProxyCreator  �Զ���������
	2��AnnotationAwareAspectJAutoProxyCreator��
	   AnnotationAwareAspectJAutoProxyCreator extends AspectJAwareAdvisorAutoProxyCreator 
		AspectJAwareAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator 
		  AbstractAdvisorAutoProxyCreator extends AbstractAutoProxyCreator	
			AbstractAutoProxyCreator extends ProxyProcessorSupport implements SmartInstantiationAwareBeanPostProcessor��bean�ĺ��ô�������, BeanFactoryAware {
            ��ע  ���ô���������bean��ʼ�����ǰ���������飩���Զ�װ��BeanFactory
            AbstractAutoProxyCreator.setBeanFactory()
                                    .postProcessBeforeInstantiation()�к��ô��������߼�
                                    .postProcessAfterInitialization()
    AbstractAdvisorAutoProxyCreator ֪ͨ�Զ���������setBeanFactory()��д��setBeanFactory()����
    AbstractAdvisorAutoProxyCreator  initBeanFactory()           
 *     ������Ҫ��������ķ�����
 *  AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 *  
 *  �����ĵ������̣�
 *     1�����������࣬����ioc����
 *     2��ע�������࣬����refresh()������ˢ������
 *       registerBeanPostProcessors(beanFactory); ע��bean�ĺ��ô�����������bean�Ĵ���.
 *       PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory, this);
 *         registerBeanPostProcessors(beanFactory, this);
 *           String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
 *           �õ�ioc���������ж�Ҫ�������������͡����Ȼ�ȡioc�����Ѿ������˵���Ҫ�������������BeanPostProcessor��
 *              1��ʹ��@EnableAspectJAutoProxy��ʱ��ע��internalAutoProxyCreator���
 *              2���������мӱ��beanPostProcessor
 *              3)����ע��ʵ����PriorityOrdered�ӿڵ�beanPostProcessor
 *              4)�ڸ�������ע��ʵ����Ordered�ӿڵ�BeanPostProcessor
 *              5��ע��û��ʵ�����ȼ��ӿڵ�beanPostProcessor
 *              
 *              // Next, register the BeanPostProcessors that implement Ordered.
				List<BeanPostProcessor> orderedPostProcessors = new ArrayList<BeanPostProcessor>();
				for (String ppName : orderedPostProcessorNames) {
					BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
					orderedPostProcessors.add(pp);
					if (pp instanceof MergedBeanDefinitionPostProcessor) {
						internalPostProcessors.add(pp);
					}
				}
				sortPostProcessors(orderedPostProcessors, beanFactory);
				registerBeanPostProcessors(beanFactory, orderedPostProcessors);
				
				BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
				
				
				public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
					@Override
					public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
						return doGetBean(name, requiredType, null, false);
					}
				}
				
				
				@SuppressWarnings("unchecked")
				protected <T> T doGetBean(final String name, final Class<T> requiredType, final Object[] args, boolean typeCheckOnly)throws BeansException {
				        // Create bean instance.
						if (mbd.isSingleton()) {
							sharedInstance = getSingleton(beanName, new ObjectFactory<Object>() {
								@Override
								public Object getObject() throws BeansException {
									try {
										return createBean(beanName, mbd, args);
									}
									catch (BeansException ex) {
										// Explicitly remove instance from singleton cache: It might have been put there
										// eagerly by the creation process, to allow for circular reference resolution.
										// Also remove any beans that received a temporary reference to the bean.
										destroySingleton(beanName);
										throw ex;
									}
								}
							});
							bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
						}
				}
				
				public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {
				       
				         public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
				             
				         }
				
				}
				6��ע��beanPostProcessor��ʵ���Ͼ��Ǵ���beanpostprocessor���󣬱����������У�
				      ����internalAutoProxyCreator��beanpostprocessor��AnnotationAwareAspectJAutoProxyCreator��
					@Override
					protected Object createBean(String beanName, RootBeanDefinition mbd, Object[] args) throws BeanCreationException {
					   Object beanInstance = doCreateBean(beanName, mbdToUse, args);
					}
					
					protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final Object[] args)throws BeanCreationException {
					         // Initialize the bean instance.��ʼ�����beanʵ��
							Object exposedObject = bean;
							try {
								populateBean(beanName, mbd, instanceWrapper);
								if (exposedObject != null) {
									exposedObject = initializeBean(beanName, exposedObject, mbd);
								}
							}
					}
					��1������bean��ʵ��
					��2��populateBean(beanName, mbd, instanceWrapper);��bean�ĸ������Ը�ֵ��
					��3��initializeBean����ʼ��bean��beanpostprocessor�����������ʼ������ǰ��ʹ�ã�
					    protected Object initializeBean(final String beanName, final Object bean, RootBeanDefinition mbd) {
							if (System.getSecurityManager() != null) {
								AccessController.doPrivileged(new PrivilegedAction<Object>() {
									@Override
									public Object run() {
										invokeAwareMethods(beanName, bean);
										return null;
									}
								}, getAccessControlContext());
							}
							else {
								invokeAwareMethods(beanName, bean);
							}
							if (mbd == null || !mbd.isSynthetic()) {
									wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
							}
							
							try {
									invokeInitMethods(beanName, wrappedBean, mbd);
								}
								catch (Throwable ex) {
									throw new BeanCreationException(
											(mbd != null ? mbd.getResourceDescription() : null),
											beanName, "Invocation of init method failed", ex);
								}
						
								if (mbd == null || !mbd.isSynthetic()) {
									wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
								}
								return wrappedBean;
						}
						��ʼ��bean�����̣�1��invokeAwareMethods(beanName, bean);����aware�ӿڵķ����ص�
						              2��applyBeanPostProcessorsBeforeInitialization����Ӧ�ú��ô�������
						         private void invokeAwareMethods(final String beanName, final Object bean) {
										if (bean instanceof Aware) {�ж��⼸���ӿ��ǲ������bean������Ǿ͵�����صķ���
											if (bean instanceof BeanNameAware) {
												((BeanNameAware) bean).setBeanName(beanName);
											}
											if (bean instanceof BeanClassLoaderAware) {
												((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
											}
											if (bean instanceof BeanFactoryAware) {
												((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
											}
										}
									}
									
									
									@Override
									public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
											throws BeansException {
								
										Object result = existingBean;
										for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {�õ����еĺ��ô�����
											result = beanProcessor.postProcessBeforeInitialization(result, beanName);���ú��ô�������postProcessBeforeInitialization
											if (result == null) {
												return result;
											}
										}
										return result;
									}
									3��invokeInitMethods������ִ���Զ���ĳ�ʼ������
									4��applyBeanPostProcessorsAfterInitialization��ִ�к��ô�������
									    @Override
										public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
												throws BeansException {
									
											Object result = existingBean;
											for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
												result = beanProcessor.postProcessAfterInitialization(result, beanName);
												if (result == null) {
													return result;
												}
											}
											return result;
										}
									 
						   
					��4��invokeAwareMethods
					    private void invokeAwareMethods(final String beanName, final Object bean) {
							if (bean instanceof Aware) {
								if (bean instanceof BeanNameAware) {
									((BeanNameAware) bean).setBeanName(beanName);
								}
								if (bean instanceof BeanClassLoaderAware) {
									((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
								}
								if (bean instanceof BeanFactoryAware) {
									((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
								}
							}
						}
						
						@SuppressWarnings("serial")
						public abstract class AbstractAdvisorAutoProxyCreator extends AbstractAutoProxyCreator {
							@Override
							public void setBeanFactory(BeanFactory beanFactory) {
								super.setBeanFactory(beanFactory);  ���ø���ķ���
								if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
									throw new IllegalArgumentException(
											"AdvisorAutoProxyCreator requires a ConfigurableListableBeanFactory: " + beanFactory);
								}
								initBeanFactory((ConfigurableListableBeanFactory) beanFactory);
							}
				        }
					    
					    public class AnnotationAwareAspectJAutoProxyCreator extends AspectJAwareAdvisorAutoProxyCreator {
					            @Override
								protected void initBeanFactory(ConfigurableListableBeanFactory beanFactory) {
									super.initBeanFactory(beanFactory);
									if (this.aspectJAdvisorFactory == null) {
										this.aspectJAdvisorFactory = new ReflectiveAspectJAdvisorFactory(beanFactory);
									}
									this.aspectJAdvisorsBuilder =
											new BeanFactoryAspectJAdvisorsBuilderAdapter(beanFactory, this.aspectJAdvisorFactory);
								}
					    
					    }
					  ��4��BeanPostProcessor��AnnotationAwareAspectJAutoProxyCreator�������ɹ�    aspectJAdvisorsBuilder
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
			////////////////////////////////////////////////////////���ϴ�����ô������Ѿ���������ע�������//////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
			7����beanpostProcessorע�ᵽbeanfactory�У�
			  for (String ppName : orderedPostProcessorNames) {
					BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
					orderedPostProcessors.add(pp);
					if (pp instanceof MergedBeanDefinitionPostProcessor) {
						internalPostProcessors.add(pp);
					}
			  }
			   sortPostProcessors(orderedPostProcessors, beanFactory);
		       registerBeanPostProcessors(beanFactory, orderedPostProcessors);	
		       private static void registerBeanPostProcessors(
						ConfigurableListableBeanFactory beanFactory, List<BeanPostProcessor> postProcessors) {
			
					for (BeanPostProcessor postProcessor : postProcessors) {
						beanFactory.addBeanPostProcessor(postProcessor);
					}
				}	    
	================================�����Ǵ�����ע��AnnotationAwareAspectJAutoProxyCreator�����ô��������Ĺ��� =========================================================================================================================			
          AnnotationAwareAspectJAutoProxyCreator���ĺ��ô�������===>>InstantiationAwareBeanPostProcessor
          
          
          // Instantiate all remaining (non-lazy-init) singletons.
		  finishBeanFactoryInitialization(beanFactory);���beanfactory��ʼ��������
		    1��������ȡ���������е�bean�����δ�������getbean��beanname��
		    
		          getbean->dogetbean()->
		          
				  protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
								// Initialize conversion service for this context.
								if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME) &&
										beanFactory.isTypeMatch(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class)) {
									    beanFactory.setConversionService(
											beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME, ConversionService.class));
								}
						
								// Register a default embedded value resolver if no bean post-processor
								// (such as a PropertyPlaceholderConfigurer bean) registered any before:
								// at this point, primarily for resolution in annotation attribute values.
								if (!beanFactory.hasEmbeddedValueResolver()) {
									beanFactory.addEmbeddedValueResolver(new StringValueResolver() {
										@Override
										public String resolveStringValue(String strVal) {
											return getEnvironment().resolvePlaceholders(strVal);
										}
									});
								}
						
								// Initialize LoadTimeWeaverAware beans early to allow for registering their transformers early.
								String[] weaverAwareNames = beanFactory.getBeanNamesForType(LoadTimeWeaverAware.class, false, false);
								for (String weaverAwareName : weaverAwareNames) {
									getBean(weaverAwareName);
								}
						
								// Stop using the temporary ClassLoader for type matching.
								beanFactory.setTempClassLoader(null);
						
								// Allow for caching all bean definition metadata, not expecting further changes.
								beanFactory.freezeConfiguration();
						
								// Instantiate all remaining (non-lazy-init) singletons.
								beanFactory.preInstantiateSingletons();
					}
			
					// Instantiate all remaining (non-lazy-init) singletons.
					beanFactory.preInstantiateSingletons();
					
					@Override
					public void preInstantiateSingletons() throws BeansException {
						if (this.logger.isDebugEnabled()) {
							this.logger.debug("Pre-instantiating singletons in " + this);
						}
				
						// Iterate over a copy to allow for init methods which in turn register new bean definitions.
						// While this may not be part of the regular factory bootstrap, it does otherwise work fine.
						List<String> beanNames = new ArrayList<String>(this.beanDefinitionNames);
				
						// Trigger initialization of all non-lazy singleton beans...
						for (String beanName : beanNames) {
							RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);
							if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {
								if (isFactoryBean(beanName)) {
									final FactoryBean<?> factory = (FactoryBean<?>) getBean(FACTORY_BEAN_PREFIX + beanName);
									boolean isEagerInit;
									if (System.getSecurityManager() != null && factory instanceof SmartFactoryBean) {
										isEagerInit = AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
											@Override
											public Boolean run() {
												return ((SmartFactoryBean<?>) factory).isEagerInit();
											}
										}, getAccessControlContext());
									}
									else {
										isEagerInit = (factory instanceof SmartFactoryBean &&
												((SmartFactoryBean<?>) factory).isEagerInit());
									}
									if (isEagerInit) {
										getBean(beanName);
									}
								}
								else {
									getBean(beanName);
								}
							}
						}
				
						// Trigger post-initialization callback for all applicable beans...
						for (String beanName : beanNames) {
							Object singletonInstance = getSingleton(beanName);
							if (singletonInstance instanceof SmartInitializingSingleton) {
								final SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton) singletonInstance;
								if (System.getSecurityManager() != null) {
									AccessController.doPrivileged(new PrivilegedAction<Object>() {
										@Override
										public Object run() {
											smartSingleton.afterSingletonsInstantiated();
											return null;
										}
									}, getAccessControlContext());
								}
								else {
									smartSingleton.afterSingletonsInstantiated();
								}
							}
						}
					}
					
					@Override
					public Object getBean(String name) throws BeansException {
						return doGetBean(name, null, null, false);
					}
 *                  ////
 *                  protected <T> T doGetBean(
								final String name, final Class<T> requiredType, final Object[] args, boolean typeCheckOnly)
								throws BeansException {
					
							final String beanName = transformedBeanName(name);
							Object bean;
					
							// Eagerly check singleton cache for manually registered singletons.
							Object sharedInstance = getSingleton(beanName);
							if (sharedInstance != null && args == null) {
								if (logger.isDebugEnabled()) {
									if (isSingletonCurrentlyInCreation(beanName)) {
										logger.debug("Returning eagerly cached instance of singleton bean '" + beanName +
												"' that is not fully initialized yet - a consequence of a circular reference");
									}
									else {
										logger.debug("Returning cached instance of singleton bean '" + beanName + "'");
									}
								}
								bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
							}
					
							else {
								// Fail if we're already creating this bean instance:
								// We're assumably within a circular reference.
								if (isPrototypeCurrentlyInCreation(beanName)) {
									throw new BeanCurrentlyInCreationException(beanName);
								}
					
								// Check if bean definition exists in this factory.
								BeanFactory parentBeanFactory = getParentBeanFactory();
								if (parentBeanFactory != null && !containsBeanDefinition(beanName)) {
									// Not found -> check parent.
									String nameToLookup = originalBeanName(name);
									if (args != null) {
										// Delegation to parent with explicit args.
										return (T) parentBeanFactory.getBean(nameToLookup, args);
									}
									else {
										// No args -> delegate to standard getBean method.
										return parentBeanFactory.getBean(nameToLookup, requiredType);
									}
								}
					
								if (!typeCheckOnly) {
									markBeanAsCreated(beanName);
								}
					
								try {
									final RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
									checkMergedBeanDefinition(mbd, beanName, args);
					
									// Guarantee initialization of beans that the current bean depends on.
									String[] dependsOn = mbd.getDependsOn();
									if (dependsOn != null) {
										for (String dep : dependsOn) {
											if (isDependent(beanName, dep)) {
												throw new BeanCreationException(mbd.getResourceDescription(), beanName,
														"Circular depends-on relationship between '" + beanName + "' and '" + dep + "'");
											}
											registerDependentBean(dep, beanName);
											getBean(dep);
										}
									}
					
									// Create bean instance.
									if (mbd.isSingleton()) {
										sharedInstance = getSingleton(beanName, new ObjectFactory<Object>() {
											@Override
											public Object getObject() throws BeansException {
												try {
													return createBean(beanName, mbd, args);
												}
												catch (BeansException ex) {
													// Explicitly remove instance from singleton cache: It might have been put there
													// eagerly by the creation process, to allow for circular reference resolution.
													// Also remove any beans that received a temporary reference to the bean.
													destroySingleton(beanName);
													throw ex;
												}
											}
										});
										bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
									}
					
									else if (mbd.isPrototype()) {
										// It's a prototype -> create a new instance.
										Object prototypeInstance = null;
										try {
											beforePrototypeCreation(beanName);
											prototypeInstance = createBean(beanName, mbd, args);
										}
										finally {
											afterPrototypeCreation(beanName);
										}
										bean = getObjectForBeanInstance(prototypeInstance, name, beanName, mbd);
									}
					
									else {
										String scopeName = mbd.getScope();
										final Scope scope = this.scopes.get(scopeName);
										if (scope == null) {
											throw new IllegalStateException("No Scope registered for scope name '" + scopeName + "'");
										}
										try {
											Object scopedInstance = scope.get(beanName, new ObjectFactory<Object>() {
												@Override
												public Object getObject() throws BeansException {
													beforePrototypeCreation(beanName);
													try {
														return createBean(beanName, mbd, args);
													}
													finally {
														afterPrototypeCreation(beanName);
													}
												}
											});
											bean = getObjectForBeanInstance(scopedInstance, name, beanName, mbd);
										}
										catch (IllegalStateException ex) {
											throw new BeanCreationException(beanName,
													"Scope '" + scopeName + "' is not active for the current thread; consider " +
													"defining a scoped proxy for this bean if you intend to refer to it from a singleton",
													ex);
										}
									}
								}
								catch (BeansException ex) {
									cleanupAfterBeanCreationFailure(beanName);
									throw ex;
								}
							}
					
							// Check if required type matches the type of the actual bean instance.
							if (requiredType != null && bean != null && !requiredType.isInstance(bean)) {
								try {
									return getTypeConverter().convertIfNecessary(bean, requiredType);
								}
								catch (TypeMismatchException ex) {
									if (logger.isDebugEnabled()) {
										logger.debug("Failed to convert bean '" + name + "' to required type '" +
												ClassUtils.getQualifiedName(requiredType) + "'", ex);
									}
									throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
								}
							}
							return (T) bean;
						}
						2������bean    // Eagerly check singleton cache for manually registered singletons.   ����֮ǰ���������Ƿ��е�����bean.
						     1���ȴӻ����л�ȡ��ǰbean�Ƿ��Ѿ���������˵��bean��֮ǰ���������ģ�ֱ��ʹ�á����򣬴����µ�bean����springʹ�����ַ�����ʹ�ô���bean�ǵ�������
						     2��ֻҪ�����õ�bean���ᱻ����������
						        createBean(String beanName, RootBeanDefinition mbd, Object[] args)  ����bean  
						                ==========================================================
									    // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.�����ô�����һ������������һ������������Ŀ������ʵ����
						                Object bean = resolveBeforeInstantiation(beanName, mbdToUse);  //����     ϣ�����ô����������ڴ��ܷ���һ���������
						                       if (targetType != null) {
													bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
													if (bean != null) {
														bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
													}
												}
						                                               ���ô������ȳ��Է��ض���
						                
						                ===============================================
						                                           ϣ�����ô������ڴ��ܷ���һ�������������ܷ��ش�������ʹ�ã�������ܾͼ�������doCreateBean()�������
						                Object beanInstance = doCreateBean(beanName, mbdToUse, args);  ������ȥ����һ��beanʵ��   
						                       createBeanInstance  ������bean��ʵ��
						                       populateBean(beanName, mbd, instanceWrapper);bean���Ը�ֵ
								               exposedObject = initializeBean(beanName, exposedObject, mbd);
								                                                             ��ʼ��bean
								                                                                  ����ִ��aware�ӿ�invokeAwareMethods(beanName, bean);
								                                                                     ���ô�������beafore��applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);��
								                         invokeInitMethods(beanName, wrappedBean, mbd);��ʼ������
								                         applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);���ô�������after
								                                                                                           �õ����еĺ��ô������������InstantiationAwareBeanPostProcessor����ִ��postProcessBeforeInstantiation���������
								                                       AnnotationAwareAspectJAutoProxyCreator���ĺ��ô�������===>>InstantiationAwareBeanPostProcessor(ǰ���Ѿ������ˣ�AnnotationAwareAspectJAutoProxyCreator����InstantiationAwareBeanPostProcessor������)                                                      
								                                                
								                                       InstantiationAwareBeanPostProcessor��postProcessBeforeInstantiation��postProcessAfterInstantiation��postProcessPropertyValues��                                                    
								                                                                                           
								                                       BeanPostProcessor��postProcessBeforeInstantiation��postProcessAfterInitialization��                                                     
								                                                                                                           ��BeanPostProcessor����bean���󴴽���ɳ�ʼ��ǰ����õġ�                                                   
								                                                                                                            ��InstantiationAwareBeanPostProcessor���ڴ���beanʵ��֮ǰ�ȳ����ú��ô��������ض���ġ�                                                   
						                                         protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
																	for (BeanPostProcessor bp : getBeanPostProcessors()) {   //�õ����еĺ��ô�����
																		if (bp instanceof InstantiationAwareBeanPostProcessor) {   //������ô�������InstantiationAwareBeanPostProcessor
																			InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
																			Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName);
																			if (result != null) {
																				return result;
																			}
																		}
																	}
																	return null;
																 }  
														 		 
																                             
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
