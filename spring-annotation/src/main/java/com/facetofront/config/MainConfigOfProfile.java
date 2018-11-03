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
 * @Profile:指定组件在哪个环境的情况下才能被注册到容器中，不指定：任何环境下都能注册
 * 这个组件
 * 
 *  @Profile("default") 
 *  1、加了环境表示的bean，只有这个环境被激活的时候才能注册到容器里面
 *  默认是default环境
 *  2、写在配置类上面@Profile("test")，只有是指定的环境的时候 ，整个配置类里面的所有的配置才能开始生效
 *  3、没有标注环境表示的bean在任何环境下都是加载的
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
