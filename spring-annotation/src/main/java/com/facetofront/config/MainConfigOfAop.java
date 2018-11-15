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
 * AOP:【动态 代理】
 *     指在程序运行期间动态的将某段代码切入到 指定 方法指定位置进行运行的编程方式。
 *  1、 导入aop模块：spring aop （spring-aspects）
 *  2、  定义一个业务逻辑类（MathCalculator）：在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束、方法出现异常）
 *  3、 定义一个日志切面类（LogAspect）：切面类里面的方法需要动态感知MathCalculator.div运行到哪里然后执行   
 *      通知方法：
 *            前置通知：logStart  在目标方法（div）运行之前运行      @Before
 *            后置通知：logEnd  在目标方法（div）运行结束之后运行    @After   无论是正常的结束还是异常的结束这个方法都调用
 *            返回通知：logReturn  在目标方法（div）正常返回之后运行   @AfterReturning
 *            异常通知：logException   在目标方法（div）出现异常之后运行    @AfterThrowing
 *            环绕通知：动态代理，手动推进目标方法运行（joinPoint。procced()） @Around
 *  4、给切面类的目标方法标注何时何地运行（通知注解）
 *  5、将切面类和业务逻辑类（目标类所在类）都加入到容器中
 *  6、必须告诉spring哪些类是切面类（给切面类加一个注解@Aspect,告诉spring这个类是一个切面类）
 *  7、给配置类中加@EnableAspectJAutoProxy，开启基于注解的aop模式。
 *    在spring中会有很多@EnableXXXX
 *   注意：将业务逻辑组件和切面类 都加入到容器中，告诉spring哪个是切面类（@Aspect），在切面类上的每一个通知方法上标注通知，告诉spring何时何地运行（切入点表达式）
 *       开启基于注解的aop模式；@EnableAspectJAutoProxy
 *       
 *       
 *       // Register bean processors that intercept bean creation.
		registerBeanPostProcessors(beanFactory);
 *       
 *       
 *       
 *  aop原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件工作时候的功能是什么】
 *       @EnableAspectJAutoProxy
 *       @Import(AspectJAutoProxyRegistrar.class) @Import给容器中导入组件
 *       ImportBeanDefinitionRegistrar  自定义 注册组件
 *          查看MainConfig.java中的例子
 *          利用AspectJAutoProxyRegistrar自定义给容器中注册bean
 *          class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {
                @Override
				public void registerBeanDefinitions(
						AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
			        
			        //如果需要的话，注册这个AspectJAnnotationAutoProxyCreator组件
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
					RootBeanDefinition beanDefinition = new RootBeanDefinition(cls);//就是要创建
					beanDefinition.setSource(source);
					beanDefinition.getPropertyValues().add("order", Ordered.HIGHEST_PRECEDENCE);
					beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
					registry.registerBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME, beanDefinition);
					return beanDefinition;
				}
				internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
			给容器中注册一个	AnnotationAwareAspectJAutoProxyCreator  自动代理创建器
	2、AnnotationAwareAspectJAutoProxyCreator：
	   AnnotationAwareAspectJAutoProxyCreator extends AspectJAwareAdvisorAutoProxyCreator 
		AspectJAwareAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator 
		  AbstractAdvisorAutoProxyCreator extends AbstractAutoProxyCreator	
			AbstractAutoProxyCreator extends ProxyProcessorSupport implements SmartInstantiationAwareBeanPostProcessor（bean的后置处理器）, BeanFactoryAware {
            关注  后置处理器（在bean初始化完成前后做的事情）、自动装配BeanFactory
            AbstractAutoProxyCreator.setBeanFactory()
                                    .postProcessBeforeInstantiation()有后置处理器的逻辑
                                    .postProcessAfterInitialization()
    AbstractAdvisorAutoProxyCreator 通知自动代理创建。setBeanFactory()重写了setBeanFactory()方法
    AbstractAdvisorAutoProxyCreator  initBeanFactory()           
 *     父类需要调用子类的方法。
 *  AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 *  
 *  方法的调用流程：
 *     1、传入配置类，创建ioc容器
 *     2、注册配置类，调用refresh()方法，刷新容器
 *       registerBeanPostProcessors(beanFactory); 注册bean的后置处理器，拦截bean的创建.
 *       PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory, this);
 *         registerBeanPostProcessors(beanFactory, this);
 *           String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
 *           拿到ioc容器中所有都要创建的容器类型。（先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor）
 *              1）使用@EnableAspectJAutoProxy的时候，注入internalAutoProxyCreator组件
 *              2）给容器中加别的beanPostProcessor
 *              3)优先注册实现了PriorityOrdered接口的beanPostProcessor
 *              4)在给容器中注册实现了Ordered接口的BeanPostProcessor
 *              5）注册没有实现优先级接口的beanPostProcessor
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
				6）注册beanPostProcessor，实际上就是创建beanpostprocessor对象，保存在容器中；
				      创建internalAutoProxyCreator的beanpostprocessor（AnnotationAwareAspectJAutoProxyCreator）
					@Override
					protected Object createBean(String beanName, RootBeanDefinition mbd, Object[] args) throws BeanCreationException {
					   Object beanInstance = doCreateBean(beanName, mbdToUse, args);
					}
					
					protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final Object[] args)throws BeanCreationException {
					         // Initialize the bean instance.初始化这个bean实例
							Object exposedObject = bean;
							try {
								populateBean(beanName, mbd, instanceWrapper);
								if (exposedObject != null) {
									exposedObject = initializeBean(beanName, exposedObject, mbd);
								}
							}
					}
					（1）创建bean的实例
					（2）populateBean(beanName, mbd, instanceWrapper);给bean的各种属性赋值。
					（3）initializeBean：初始化bean（beanpostprocessor就是在这个初始化工作前后使用）
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
						初始化bean的流程：1、invokeAwareMethods(beanName, bean);处理aware接口的方法回调
						              2、applyBeanPostProcessorsBeforeInitialization（）应用后置处理器的
						         private void invokeAwareMethods(final String beanName, final Object bean) {
										if (bean instanceof Aware) {判断这几个接口是不是这个bean，如果是就调用相关的方法
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
										for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {拿到所有的后置处理器
											result = beanProcessor.postProcessBeforeInitialization(result, beanName);调用后置处理器的postProcessBeforeInitialization
											if (result == null) {
												return result;
											}
										}
										return result;
									}
									3、invokeInitMethods（）；执行自定义的初始化方法
									4、applyBeanPostProcessorsAfterInitialization：执行后置处理器的
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
									 
						   
					（4）invokeAwareMethods
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
								super.setBeanFactory(beanFactory);  调用父类的方法
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
					  （4）BeanPostProcessor（AnnotationAwareAspectJAutoProxyCreator）创建成功    aspectJAdvisorsBuilder
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
			////////////////////////////////////////////////////////以上代码后置处理器已经在容器中注册进来了//////////////////////////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
			7）把beanpostProcessor注册到beanfactory中：
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
	================================以上是创建和注册AnnotationAwareAspectJAutoProxyCreator（后置处理器）的过程 =========================================================================================================================			
          AnnotationAwareAspectJAutoProxyCreator（的后置处理器）===>>InstantiationAwareBeanPostProcessor
          
          
          // Instantiate all remaining (non-lazy-init) singletons.
		  finishBeanFactoryInitialization(beanFactory);完成beanfactory初始化工作。
		    1、遍历获取容器中所有的bean，依次创建对象getbean（beanname）
		    
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
						2、创建bean    // Eagerly check singleton cache for manually registered singletons.   创建之前看看缓存是否有单例的bean.
						     1）先从缓存中获取当前bean是否已经被创建，说明bean是之前被创建过的，直接使用。否则，创建新的bean。（spring使用这种方法，使得创建bean是单例）。
						     2）只要创建好的bean都会被缓存起来，
						        createBean(String beanName, RootBeanDefinition mbd, Object[] args)  创建bean  
						                ==========================================================
									    // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.给后置处理器一个机会来返回一个代理来代替目标对象的实例。
						                Object bean = resolveBeforeInstantiation(beanName, mbdToUse);  //解析     希望后置处理器处理在此能返回一个代理对象。
						                       if (targetType != null) {
													bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
													if (bean != null) {
														bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
													}
												}
						                                               后置处理器先尝试返回对象
						                
						                ===============================================
						                                           希望后置处理器在此能返回一个代理对象：如果能返回代理对象就使用，如果不能就继续调用doCreateBean()这个方法
						                Object beanInstance = doCreateBean(beanName, mbdToUse, args);  真正的去创建一个bean实例   
						                       createBeanInstance  ：创建bean的实例
						                       populateBean(beanName, mbd, instanceWrapper);bean属性赋值
								               exposedObject = initializeBean(beanName, exposedObject, mbd);
								                                                             初始化bean
								                                                                  （先执行aware接口invokeAwareMethods(beanName, bean);
								                                                                     后置处理器的beafore的applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);）
								                         invokeInitMethods(beanName, wrappedBean, mbd);初始化方法
								                         applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);后置处理器的after
								                                                                                           拿到所有的后置处理器，如果是InstantiationAwareBeanPostProcessor；就执行postProcessBeforeInstantiation这个方法，
								                                       AnnotationAwareAspectJAutoProxyCreator（的后置处理器）===>>InstantiationAwareBeanPostProcessor(前面已经讲过了：AnnotationAwareAspectJAutoProxyCreator就是InstantiationAwareBeanPostProcessor处理器)                                                      
								                                                
								                                       InstantiationAwareBeanPostProcessor（postProcessBeforeInstantiation、postProcessAfterInstantiation、postProcessPropertyValues）                                                    
								                                                                                           
								                                       BeanPostProcessor（postProcessBeforeInstantiation、postProcessAfterInitialization）                                                     
								                                                                                                           【BeanPostProcessor是在bean对象创建完成初始化前后调用的】                                                   
								                                                                                                            【InstantiationAwareBeanPostProcessor是在创建bean实例之前先尝试用后置处理器返回对象的】                                                   
						                                         protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
																	for (BeanPostProcessor bp : getBeanPostProcessors()) {   //拿到所有的后置处理器
																		if (bp instanceof InstantiationAwareBeanPostProcessor) {   //如果后置处理器是InstantiationAwareBeanPostProcessor
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
	   
	   //把业务容器类加到容器内
	   @Bean
	   public MathCalculator calculator(){
		   return new MathCalculator();
	   }
       
	   
	   //把切面类也加入到容器里面
	   @Bean
	   public LogAspect logAspect(){
		   return new LogAspect();
	   }
	   
	   
	   
}
