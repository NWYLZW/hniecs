package com.hniecs.mainserver.tool.interceptor;

import com.hniecs.mainserver.annotation.NotNeedLogin;
import com.hniecs.mainserver.tool.api.CommonResult;
import com.hniecs.mainserver.tool.security.session.SessionTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @desc    登陆拦截器 LoginInterceptor.java
 * @author  yijie
 * @date    2020-09-13 22:14
 * @logs[0] 2020-09-13 22:14 yijie 创建了LoginInterceptor.java文件
 * @logs[1] 2020-09-17 00:52 yijie 完成登陆拦截器
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private boolean deal(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler) throws Exception {
        // 校验session是否正确
        HttpSession session = request.getSession();
        boolean isRightSession = SessionTool
            .vertifyUserSessionToken(session);
        if (!isRightSession) {
            response.setStatus(401);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommonResult.unauthorized().toJsonString());
        }
        return isRightSession;
    }
    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethodAnnotation(NotNeedLogin.class) != null) {
                return true;
            }
        }
        return this.deal(request, response, handler);
    }
}
