package com.facetofront.config;

import java.lang.annotation.Repeatable;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import com.facetofront.condition.LinuxCondition;
import com.facetofront.condition.MyImportBeanDefinitionRegister;
import com.facetofront.condition.MyImportSelector;
import com.facetofront.condition.WindowCondition;
import com.facetofront.domain.Color;
import com.facetofront.domain.ColorFacrotyBean;
import com.facetofront.domain.Person;
import com.facetofront.domain.Red;





//@Conditional({WindowCondition.class})   满足当前条件，这个类中的配置的所有bean注册才能生效
//配置类==配置文件
@Configuration   //告诉spring这是一个配置类 
@ComponentScan(value="com.facetofront")  //  相当于 <context:component-scan base-package="com.facetofront"></context:component-scan>这个扫描器
//@ComponentScan(value="com.facetofront"，excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})} ) value :指定要扫描的包   excludeFilters：排除要扫描的包
//@includeFilters(value="com.facetofront"，excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})},userDefaulters=false )
//@ComponentScan是重复注解，如果是jdk8的话，可以使用多次，一个类中，@Repeatable(ComponentScans.class)，如果是jdk1.7的话可以使用@ComponentScans指定扫描策略，里面的value是重复数组。
//FilterType.ANNOTATION:按照注解
//FilterType.ASSIGNABLE_TYPE:按照给定的类型
//FilterType.ASPECTJ：使用ASPECTJ表达式
//FilterType.REGEX：使用正则指定
//FilterType.CUSTOM：使用自定义规则   @Filter(type=FilterType.CUSTOM,classes={MyTypeFilter.class}).
@Import({Color.class,Red.class,MyImportSelector.class,MyImportBeanDefinitionRegister.class})   //导入组件，id为默认的组件的全类名
public class MainConfig {
      
	
	  /**
	   *  懒加载：容器启动的时候，不创建对象，第一次使用bean的时候，创建对象，并初始化。
	   *      单实例bean；默认在容器启动的时候创建对象
	   * @return
	   */
	  //@Lazy
	  @Bean("person")
	  public Person person01(){
		  return new Person("lili",20);
	  }
	  
	  
	  /**
	  * @conditional({}):按照一定的条件进行哦按段，满足条件给容器中注册bean
	  * 
	  * 如果系统是windows，放bill，如果是linux,放linux
	  * 
	  */
	 @Conditional({WindowCondition.class})//不仅可以标注方法上面，也可以标注在类上面
	 @Bean("bill") 
	 public Person person1(){
		 return new Person("Bill Gates",62);
	 }
	 
	 @Conditional({LinuxCondition.class})
	 @Bean("linux") 
	 public Person person2(){
		 return new Person("linux",62);
	 }
	 
	 
	 /**
	  * 普通的bean直接导在容器里面，容器会调用它的无参构造器，默认创建一个对象，注册到容器中去
	  * 工厂bean是一个接口，容器会调用getObject的方法，返回的对象，放到容器中去
	  * 给容器中注册组件：
	  * 1、包扫描+组件标注注解（@Controller,@Service,@Repository,@Component）【自己写的】
	  * 
	  * 2、@Bean [导入第三方包里面的组件]
	  * 
	  * 3、@Import 【快速给容器中导入一个组件】
	  *       1)\@Import(要导入到容器中的组件)，容器中就会自动注册这个组件，id默认是全类名数组，
	  *       2)\ImportSelector：返回需要导入的组件的全类名，
	  *       3)\ImportSelector：  手动注册bean到容器中
	  * 4、使用spring提供的FactoryBean（工厂bean）
	  *       1)、默认获取到的是工厂bean调用getObject创建的对象
	  *       2)、要获取工厂Bean本身，我们需要给id前面加一个&标识
	  *       
	  *       
	  */
	 @Bean
	 public ColorFacrotyBean colorFactoryBean(){
		return new ColorFacrotyBean();
	 }
	 
	 
}
