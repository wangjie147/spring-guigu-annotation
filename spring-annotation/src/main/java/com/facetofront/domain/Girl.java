package com.facetofront.domain;

import org.springframework.beans.factory.annotation.Value;

public class Girl {
	
	//ʹ��@value��ֵ
	//1��������ֵ
	//2������дSpEl    #{}
	//3������д${}:  ȡ�������ļ��е�ֵ�������л����е�ֵ��
	
	@Value("1111")
	private String name;
	
	@Value("#{20-2}")
	private Integer age;
	
	@Value("${person.sex}")
	private String sex;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
    
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Girl [name=" + name + ", age=" + age +  ",sex=" +sex+"]";
	}

	public Girl(String name, Integer age,String sex) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public Girl() {
		super();
	}
	
	

}
