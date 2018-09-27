package com.facetofront.domain;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;



@Component
public class Dog {
    
	 public Dog(){
		 System.out.println("dog construct  ======");
	 }
	 //对象创建并赋值之后调用
	 @PostConstruct
	 public void init(){
		 System.out.println("dog init  ======");
	 }
	 
	 //
	 @PreDestroy
	 public void destory(){
		 System.out.println("dog destory  ======");
	 }
	  
}
