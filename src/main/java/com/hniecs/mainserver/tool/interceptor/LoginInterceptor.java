package com.hniecs.mainserver.tool.interceptor;

import com.hniecs.mainserver.annotation.NotNeedLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @desc    登陆拦截器 LoginInterceptor.java
 * @author  yijie
 * @date    2020-09-13 22:14
 * @logs[0] 2020-09-13 22:14 yijie 创建了LoginInterceptor.java文件
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private boolean deal(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler) {
        return true;
    }
    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethodAnnotation(NotNeedLogin.class) != null) {
                return this.deal(request, response, handler);
            }
        }
        return false;
    }
}
