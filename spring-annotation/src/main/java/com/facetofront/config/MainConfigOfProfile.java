package com.facetofront.config;


import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import com.facetofront.domain.Yellow;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * @Profile:ָ��������ĸ�����������²��ܱ�ע�ᵽ�����У���ָ�����κλ����¶���ע��
 * ������
 * 
 *  @Profile("default") 
 *  1�����˻�����ʾ��bean��ֻ����������������ʱ�����ע�ᵽ��������
 *  Ĭ����default����
 *  2��д������������@Profile("test")��ֻ����ָ���Ļ�����ʱ�� ��������������������е����ò��ܿ�ʼ��Ч
 *  3��û�б�ע������ʾ��bean���κλ����¶��Ǽ��ص�
 * @author wangl
 *
 */
//@Profile("test")
@PropertySource("classpath:/dbconfig.properties")
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware{
	
	  @Value("${db.user}")
	  private String user;
	  
	  private StringValueResolver valueResolver;
	  
	  private String resolveStringValue ;
	  
	  @Profile("test")
	  @Bean
	  public Yellow yellow(){
		  return new Yellow();
	  }
	  
	  /**
	   * @throws PropertyVetoException 
	   */ 
	  
	   @Profile("test")
	   @Bean("testData")
	   public DataSource dataSourceTest(@Value("${db.driverClass}")String url) throws PropertyVetoException{
		   ComboPooledDataSource dataSource = new ComboPooledDataSource();
		   dataSource.setUser(user);
		   dataSource.setPassword(resolveStringValue);
		   dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
		   dataSource.setDriverClass(url);
		   return null;
	   }
	   @Profile("dev")
	   @Bean("adminData")
	   public DataSource dataSourceDev(@Value("${db.driverClass}")String url) throws PropertyVetoException{
		   ComboPooledDataSource dataSource = new ComboPooledDataSource();
		   dataSource.setUser(user);
		   dataSource.setPassword(resolveStringValue);
		   dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/admin");
		   dataSource.setDriverClass(url);
		   return null;
	   }
	   @Profile("prod")
	   @Bean("taotaoData")
	   public DataSource dataSourceProd(@Value("${db.driverClass}")String url) throws PropertyVetoException{
		   ComboPooledDataSource dataSource = new ComboPooledDataSource();
		   dataSource.setUser(user);
		   dataSource.setPassword(resolveStringValue);
		   dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/taotao");
		   dataSource.setDriverClass(url);
		   return null;
	   }

		public void setEmbeddedValueResolver(StringValueResolver resolver) {
			this.valueResolver=resolver;
			resolveStringValue= valueResolver.resolveStringValue("${db.password}");
		}
	
	
	
}
