package com.facetofront.domain;

import org.springframework.beans.factory.FactoryBean;


//创建一个spring定义的FacrotyBean
public class ColorFacrotyBean implements FactoryBean<Color>{
    
	//返回一个color对象，这个对象会添加到容器中去
	public Color getObject() throws Exception {
		System.out.println("ColorFacrotyBeand的getObject");
		return new Color();
	}

	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return Color.class;
	}
    
	//是单例？
	//true  这个bean是单实例，在容器中爆粗一份
	//false 多实例，每次获取都会创建一个新的bean
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

	
	
	

}
