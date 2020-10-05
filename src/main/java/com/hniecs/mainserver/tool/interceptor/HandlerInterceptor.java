package com.hniecs.mainserver.tool.interceptor;

import com.hniecs.mainserver.annotation.method.NotNeedLogin;
import com.hniecs.mainserver.annotation.method.PermissionRequired;
import com.hniecs.mainserver.entity.permission.Permissions;
import com.hniecs.mainserver.tool.api.CommonResult;
import com.hniecs.mainserver.tool.security.SessionTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @desc    请求拦截器 LoginInterceptor.java
 * @author  yijie
 * @date    2020-09-13 22:14
 * @logs[0] 2020-09-13 22:14 yijie 创建了LoginInterceptor.java文件
 * @logs[1] 2020-09-17 00:52 yijie 完成登陆拦截器
 * @logs[2] 2020-09-25 05:12 yijie 完成权限拦截器
 */
@Slf4j
public class HandlerInterceptor extends HandlerInterceptorAdapter {
    /**
     * 权限拦截
     * @param response              HttpServletResponse
     * @param permissionRequired    PermissionRequired
     */
    private boolean havePermission(
        HttpServletResponse response, PermissionRequired permissionRequired
    ) throws IOException {
        boolean havePermission = Permissions.havePermission(
            SessionTool.curUser(),
            permissionRequired.scope(),
            permissionRequired.permission()
        );
        if (!havePermission) {
            response.setStatus(403);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommonResult.forbidden().toJsonString());
        }
        return havePermission;
    }
    /**
     * 是否拦截登陆了
     * @param response  HttpServletResponse
     */
    private boolean isLogin(
        HttpServletResponse response
    ) throws IOException {
        // 校验session是否正确
        boolean isRightSession = SessionTool.verifyUserSessionToken();
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
        SessionTool.setSession(request.getSession());

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 添加了不需要登陆的注解 不再拦截
            if (handlerMethod.getMethodAnnotation(NotNeedLogin.class) != null) {
                return true;
            } else {
                // 如果未登陆 拦截
                boolean isLogin = this.isLogin(response);
                if (!isLogin) {
                    return false;
                }
                // 添加了权限拦截的注解，判断是否有权限
                PermissionRequired pr = handlerMethod.getMethodAnnotation(PermissionRequired.class);
                if (pr != null) {
                    return this.havePermission(response, pr);
                }
            }
        }
        return true;
    }
}
