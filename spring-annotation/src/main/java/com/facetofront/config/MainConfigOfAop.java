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
 *  aop原理：     @EnableAspectJAutoProxy
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
