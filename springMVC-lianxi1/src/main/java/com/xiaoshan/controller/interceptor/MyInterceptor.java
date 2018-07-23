package com.xiaoshan.controller.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author YushanZhao
 * @Date:2018/7/23
 */
public class MyInterceptor extends HandlerInterceptorAdapter {

    //preHandle 请求拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        System.out.println("请求路径uri: " + uri);
        System.out.println("请求拦截...");
        return true;
    }

    //postHandle 响应拦截
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("响应拦截...");
    }

    //afterCompletion 视图渲染完成之后回调
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("视图渲染完成之后回调...");
    }
}
