package com.facetofront.domain;

import org.springframework.beans.factory.annotation.Value;

public class Girl {
	
	//使用@value赋值
	//1、基本数值
	//2、可以写SpEl    #{}
	//3、可以写${}:  取出配置文件中的值（在运行环境中的值）
	
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
