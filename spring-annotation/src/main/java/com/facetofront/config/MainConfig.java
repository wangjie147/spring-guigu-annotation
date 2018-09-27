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





//@Conditional({WindowCondition.class})   ���㵱ǰ������������е����õ�����beanע�������Ч
//������==�����ļ�
@Configuration   //����spring����һ�������� 
@ComponentScan(value="com.facetofront")  //  �൱�� <context:component-scan base-package="com.facetofront"></context:component-scan>���ɨ����
//@ComponentScan(value="com.facetofront"��excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})} ) value :ָ��Ҫɨ��İ�   excludeFilters���ų�Ҫɨ��İ�
//@includeFilters(value="com.facetofront"��excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})},userDefaulters=false )
//@ComponentScan���ظ�ע�⣬�����jdk8�Ļ�������ʹ�ö�Σ�һ�����У�@Repeatable(ComponentScans.class)�������jdk1.7�Ļ�����ʹ��@ComponentScansָ��ɨ����ԣ������value���ظ����顣
//FilterType.ANNOTATION:����ע��
//FilterType.ASSIGNABLE_TYPE:���ո���������
//FilterType.ASPECTJ��ʹ��ASPECTJ���ʽ
//FilterType.REGEX��ʹ������ָ��
//FilterType.CUSTOM��ʹ���Զ������   @Filter(type=FilterType.CUSTOM,classes={MyTypeFilter.class}).
@Import({Color.class,Red.class,MyImportSelector.class,MyImportBeanDefinitionRegister.class})   //���������idΪĬ�ϵ������ȫ����
public class MainConfig {
      
	
	  /**
	   *  �����أ�����������ʱ�򣬲��������󣬵�һ��ʹ��bean��ʱ�򣬴������󣬲���ʼ����
	   *      ��ʵ��bean��Ĭ��������������ʱ�򴴽�����
	   * @return
	   */
	  //@Lazy
	  @Bean("person")
	  public Person person01(){
		  return new Person("lili",20);
	  }
	  
	  
	  /**
	  * @conditional({}):����һ������������Ŷ���Σ�����������������ע��bean
	  * 
	  * ���ϵͳ��windows����bill�������linux,��linux
	  * 
	  */
	 @Conditional({WindowCondition.class})//�������Ա�ע�������棬Ҳ���Ա�ע��������
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
	  * ��ͨ��beanֱ�ӵ����������棬��������������޲ι�������Ĭ�ϴ���һ������ע�ᵽ������ȥ
	  * ����bean��һ���ӿڣ����������getObject�ķ��������صĶ��󣬷ŵ�������ȥ
	  * ��������ע�������
	  * 1����ɨ��+�����עע�⣨@Controller,@Service,@Repository,@Component�����Լ�д�ġ�
	  * 
	  * 2��@Bean [�������������������]
	  * 
	  * 3��@Import �����ٸ������е���һ�������
	  *       1)\@Import(Ҫ���뵽�����е����)�������оͻ��Զ�ע����������idĬ����ȫ�������飬
	  *       2)\ImportSelector��������Ҫ����������ȫ������
	  *       3)\ImportSelector��  �ֶ�ע��bean��������
	  * 4��ʹ��spring�ṩ��FactoryBean������bean��
	  *       1)��Ĭ�ϻ�ȡ�����ǹ���bean����getObject�����Ķ���
	  *       2)��Ҫ��ȡ����Bean����������Ҫ��idǰ���һ��&��ʶ
	  *       
	  *       
	  */
	 @Bean
	 public ColorFacrotyBean colorFactoryBean(){
		return new ColorFacrotyBean();
	 }
	 
	 
}
