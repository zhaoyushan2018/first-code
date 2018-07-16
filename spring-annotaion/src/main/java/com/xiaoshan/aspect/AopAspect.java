package com.xiaoshan.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author YushanZhao
 * @Date:2018/7/16
 */
@Component("AopAspect")
@Aspect
public class AopAspect {

    @Pointcut("execution(* com.xiaoshan.service..*.*(..))")
    public void pointCut(){}

    //@Before("execution(* com.xiaoshan.service..*.*(..))")
    @Before("pointCut()")
    public void beforeAdvice(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("前置通知...-->methodNamne:" + methodName);
    }

    //传两个参数时,考虑能不能传第两个参数,位置在哪
    @AfterReturning(value = "pointCut()", returning = "result")
    public void afterAdvice(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("后置通知...-->methodNamne:" + methodName + ",result:" + result);
    }
   /* @AfterReturning("pointCut()")
    public void afterAdvice(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("后置通知...-->methodNamne:" + methodName);
    }无参数时*/

    @AfterThrowing("pointCut()")
    public void exceptionAdvice(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("异常通知...-->methodNamne:" + methodName);
    }

    @After("pointCut()")
    public void finallyAdvice(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("最终通知...-->methodNamne:" + methodName);
    }

  /* @Around("pointCut()")
   public Object aroundAdvice(ProceedingJoinPoint joinPoint){
       String methodName = joinPoint.getSignature().getName();

       Object object = null;
       try {
           System.out.println("前置通知");
           //目标对象方法的执行
           object = joinPoint.proceed();
           System.out.println("后置通知");
       } catch (Throwable throwable) {
           System.out.println("异常通知");
       } finally {
           System.out.println("最终通知-->methodName:" + methodName);
       }
        return object;
   }*/

}
