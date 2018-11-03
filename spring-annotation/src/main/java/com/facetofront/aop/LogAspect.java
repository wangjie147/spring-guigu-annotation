package com.facetofront.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class LogAspect {
	    
	    @Pointcut("execution(public int com.facetofront.aop.MathCalculator.*(..))")
	    public void pointCut(){
	    	//������ȡ�����������ģʽ
	    	//1������������ô������     @Before("pointCut()")
	    	//2�������������     �����ͽ�������       @Before("com.facetofront.aop.LogAspect.pointCut()")
	    };
	
	   //@Before  ��Ŀ�귽��֮ǰ����      �����ı��ʽ��ָ�����Ǹ�������
	   /**
	    * 
	    *  @Before("public int com.facetofront.aop.MathCalculator.*(..)")
	    *  @Before("public int com.facetofront.aop.MathCalculator.div(int, int)")
	    * 
	    */
	   @Before("pointCut()")
	   public void logStart(JoinPoint joinpoint){
		   Object[] args = joinpoint.getArgs();
		   System.out.println("�������С������������б�{"+Arrays.asList(args)+"}"+joinpoint.getSignature().getName());
	   }
	   
	   
	   @After("pointCut()")
	   public void logEnd(JoinPoint joinpoint){
		   System.out.println("���������������������б�{}"+joinpoint.getSignature().getName());
	   }
	   
	   
	   @AfterReturning(value="pointCut()",returning="result")
	   public void logReturn(JoinPoint joinpoint,Object result){//JoinPoint joinpoint  �����������д����ǰ���λ�ã�����spring���޷�ʶ���
		   System.out.println(joinpoint.getSignature().getName()+"�����������ء��������������б�{"+result+"}");
	   }
	   
	   @AfterThrowing(value="pointCut()",throwing="exp")
	   public void logException(JoinPoint joinpoint,Exception exp){
		   System.out.println(joinpoint.getSignature().getName()+"�����쳣���������쳣��Ϣ��{"+exp+"}");
	   }
}
