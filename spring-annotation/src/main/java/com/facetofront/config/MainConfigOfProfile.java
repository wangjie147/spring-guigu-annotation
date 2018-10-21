package com.facetofront.config;


import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;



@PropertySource("classpath:/dbconfig.properties")
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware{
	
	  @Value("${db.user}")
	  private String user;
	  
	  private StringValueResolver valueResolver;
	  
	  /**
	   * @throws PropertyVetoException 
	   */ 
	   @Bean
	   public DataSource dataSourceTest(@Value("${db.driverClass}")String url) throws PropertyVetoException{
		   ComboPooledDataSource dataSource = new ComboPooledDataSource();
		   dataSource.setUser(user);
		   dataSource.setPassword(password);
		   dataSource.setJdbcUrl("jdbc:mysql://192.168.0.253:3306/test");
		   dataSource.setDriverClass(url);
		   return null;
	   }
	   
	   @Bean
	   public DataSource dataSourceDev(@Value("${db.driverClass}")String url) throws PropertyVetoException{
		   ComboPooledDataSource dataSource = new ComboPooledDataSource();
		   dataSource.setUser(user);
		   dataSource.setPassword(password);
		   dataSource.setJdbcUrl("jdbc:mysql://192.168.0.253:3306/admin");
		   dataSource.setDriverClass(url);
		   return null;
	   }
	   
	   @Bean
	   public DataSource dataSourceProd(@Value("${db.driverClass}")String url) throws PropertyVetoException{
		   ComboPooledDataSource dataSource = new ComboPooledDataSource();
		   dataSource.setUser(user);
		   dataSource.setPassword(password);
		   dataSource.setJdbcUrl("jdbc:mysql://192.168.0.253:3306/taotao");
		   dataSource.setDriverClass(url);
		   return null;
	   }

		public void setEmbeddedValueResolver(StringValueResolver resolver) {
			this.valueResolver=resolver;
		}
	
	
	
}
