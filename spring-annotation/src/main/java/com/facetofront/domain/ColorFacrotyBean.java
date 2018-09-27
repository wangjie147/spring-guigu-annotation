package com.facetofront.domain;

import org.springframework.beans.factory.FactoryBean;


//����һ��spring�����FacrotyBean
public class ColorFacrotyBean implements FactoryBean<Color>{
    
	//����һ��color��������������ӵ�������ȥ
	public Color getObject() throws Exception {
		System.out.println("ColorFacrotyBeand��getObject");
		return new Color();
	}

	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return Color.class;
	}
    
	//�ǵ�����
	//true  ���bean�ǵ�ʵ�����������б���һ��
	//false ��ʵ����ÿ�λ�ȡ���ᴴ��һ���µ�bean
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

	
	
	

}
