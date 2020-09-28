package com.hniecs.mainserver.tool.security;

import com.hniecs.mainserver.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;

/**
 * @desc    session帮助类 sessionTool.java
 * @author  yijie
 * @date    2020-09-16 22:24
 * @logs[0] 2020-09-16 22:24 yijie 创建了sessionTool.java文件
 * @logs[1] 2020-09-17 00:06 yijie 添加sessionToken帮助方法
 */
@Slf4j
public class SessionTool {
    /**
     * sessionToken的加密次数
     */
    public static final int SESSION_TOKEN_SALT_COUNT = 10;

    /**
     * 当前祈求session
     */
    @Getter@Setter
    public static HttpSession session;

    /**
     * 获取当前用户 不存在返回null
     */
    public static UserEntity curUser () {
        try {
            return (UserEntity) getSession().getAttribute("currentUser");
        } catch (Exception e) {
            log.error("无法从 session中获取 UserEntity格式的 currentUser对象", e);
        }
        return null;
    }

    /**
     * 设置覆盖用户sessionToken信息
     * @param user      用户实体
     * @return  sessionToken对象
     */
    public static Object setUserSessionToken (UserEntity user) {
        String sessionToken = user.getUserToken(SESSION_TOKEN_SALT_COUNT);
        getSession().setAttribute("sessionToken", sessionToken);
        getSession().setAttribute("currentUser", user);
        return sessionToken;
    }

    /**
     * 校验用户的sessionToken是否正确
     * @return  sessionToken是否正确
     */
    public static boolean verifyUserSessionToken () {
        String sessionToken = null;
        try {
            sessionToken = (String) getSession().getAttribute("sessionToken");
        } catch (Exception e) {
            log.error("无法从 session中获取sessionToken", e);
        }
        UserEntity user = curUser();
        if (sessionToken != null && user != null) {
            return user.getUserToken(SESSION_TOKEN_SALT_COUNT)
                .equals(sessionToken);
        }
        return false;
    }
}
