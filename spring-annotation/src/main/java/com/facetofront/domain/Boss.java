package com.facetofront.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//Ĭ�ϼ�����ioc�����е��������������������޲ι��������������ٽ��г�ʼ����ֵ�Ȳ���
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
	@Autowired  //��ע�ڷ����ϣ�spring����������ǰ���󣬾ͻ���÷�������ɸ�ֵ����ʹ�ò������Զ������͵�ֵ��ioc�����л�ȡ
	public void setCar(Car car) {
		this.car = car;
	}
	*/
	
  /*spring������ʱ������вι�����     ������Ҫ�õ���������Ǵ������л�ȡ
    public Boss(@AutowiredCar car){
    	this.car = car;
    }*/
}
