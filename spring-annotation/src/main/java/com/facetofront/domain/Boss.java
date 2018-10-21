package com.facetofront.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//默认加载在ioc容器中的组件，容器启动会调用无参构造器创建对象，再进行初始化赋值等操作
@Component
public class Boss {
	
	
	@Autowired
	private Car car;

	@Override
	public String toString() {
		return "Boss [car=" + car + "]";
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	/*
	@Autowired  //标注在方法上，spring容器创建当前对象，就会调用方法，完成赋值方法使用参数，自定义类型的值从ioc容器中获取
	public void setCar(Car car) {
		this.car = car;
	}
	*/
	
  /*spring启动的时候调用有参构造器     构造器要用的组件，都是从容器中获取
    public Boss(@AutowiredCar car){
    	this.car = car;
    }*/
}
