package com.facetofront.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


//
public class MyImportSelector implements ImportSelector {
    
	//����ֵ�����ǵ����뵽�����е����ȫ����
	//AnnotationMetadata����ǰ��ע@Importע����������ע����Ϣ
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		// TODO Auto-generated method stub
		//importingClassMetadata
		//������Ҫ����nullֵ��
		
		return new String[]{"com.facetofront.domain.Blue","com.facetofront.domain.Yellow"};
	}
	
	

}
