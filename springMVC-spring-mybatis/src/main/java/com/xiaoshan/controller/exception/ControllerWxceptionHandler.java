package com.xiaoshan.controller.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author YushanZhao
 * @Date:2018/7/24
 */
@ControllerAdvice
public class ControllerWxceptionHandler extends RuntimeException {

    public String ioException(){
        return "error/500";
    }

}
