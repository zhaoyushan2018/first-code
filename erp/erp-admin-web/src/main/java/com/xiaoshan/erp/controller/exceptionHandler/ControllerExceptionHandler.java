package com.xiaoshan.erp.controller.exceptionHandler;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

/**
 * @author YushanZhao
 * @Date:2018/7/24
 */
//在类上加上@ControllerAdvice(通知)注解用来标记当前类中所有使用了@ExceptionHandler注解的方法为全局的异常处理方法
@ControllerAdvice
public class ControllerExceptionHandler extends RuntimeException {

    @ExceptionHandler(IOException.class)
    public String ioExceptionHandler(){
        return "error/500";
    }

    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(){
        return "error/401";
    }

}
