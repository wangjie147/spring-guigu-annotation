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
	    	//用来抽取公共的切入点模式
	    	//1、如果本类引用次切入点     @Before("pointCut()")
	    	//2、其他类的引用     这个类就叫切面类       @Before("com.facetofront.aop.LogAspect.pointCut()")
	    };
	
	   //@Before  在目标方法之前切入      切入点的表达式（指定在那个方法）
	   /**
	    * 
	    *  @Before("public int com.facetofront.aop.MathCalculator.*(..)")
	    *  @Before("public int com.facetofront.aop.MathCalculator.div(int, int)")
	    * 
	    */
	   @Before("pointCut()")
	   public void logStart(JoinPoint joinpoint){
		   Object[] args = joinpoint.getArgs();
		   System.out.println("除法运行。。。。参数列表：{"+Arrays.asList(args)+"}"+joinpoint.getSignature().getName());
	   }
	   
	   
	   @After("pointCut()")
	   public void logEnd(JoinPoint joinpoint){
		   System.out.println("除法结束。。。。参数列表：{}"+joinpoint.getSignature().getName());
	   }
	   
	   
	   @AfterReturning(value="pointCut()",returning="result")
	   public void logReturn(JoinPoint joinpoint,Object result){//JoinPoint joinpoint  这个参数必须写在最前面的位置，否则spring是无法识别的
		   System.out.println(joinpoint.getSignature().getName()+"除法正常返回、、、、、参数列表：{"+result+"}");
	   }
	   
	   @AfterThrowing(value="pointCut()",throwing="exp")
	   public void logException(JoinPoint joinpoint,Exception exp){
		   System.out.println(joinpoint.getSignature().getName()+"除法异常。。。。异常信息：{"+exp+"}");
	   }
}
