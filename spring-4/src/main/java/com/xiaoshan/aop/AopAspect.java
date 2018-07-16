package com.xiaoshan.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author YushanZhao
 * @Date:2018/7/16
 */
public class AopAspect {

    public void beforeAdvice() {
        System.out.println("前置通知");
    }
    public void afterAdvice(Object result) {
        System.out.println("后置通知-->result:" + result);
    }
    public void exceptionAdvice(Exception ex) {
        System.out.println("异常通知-->Exception:" + ex.getMessage());
    }
    public void finallyAdvice() {
        System.out.println("最终通知");
    }

   public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){

       Object object = null;
       try {
           System.out.println("前置通知...");
           //目标方法的执行
           object = proceedingJoinPoint.proceed();
           System.out.println("后置通知...");
       } catch (Throwable throwable) {
           System.out.println("异常通知...");
       } finally{
           System.out.println("最终通知...");
       }
        return object;
   }

}
