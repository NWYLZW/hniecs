package com.hniecs.mainserver.tool;

import com.hniecs.mainserver.entity.UserEntity;
import com.hniecs.mainserver.tool.security.SHA256;
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
    public static final int sessionTokenSaltCount = 10;
    /**
     * 设置覆盖用户sessionToken信息
     * @param session   HttpSession对象
     * @param user      用户实体
     * @return  sessionToken对象
     */
    public static Object setUserSessionToken (HttpSession session, UserEntity user) {
        String sessionToken = SHA256.salt(user.getId() + '&' + user.getUserName(), sessionTokenSaltCount);
        session.setAttribute("sessionToken", sessionToken);
        session.setAttribute("currentUser", user);
        return sessionToken;
    }

    /**
     * 校验用户的sessionToken是否正确
     * @param session   HttpSession对象
     * @return  sessionToken是否正确
     */
    public static boolean vertifyUserSessionToken (HttpSession session) {
        String sessionToken = null;
        try {
            sessionToken = (String) session.getAttribute("sessionToken");
        } catch (Exception e) {
            log.error("无法从 session中获取sessionToken", e);
        }
        try {
            UserEntity user = (UserEntity) session.getAttribute("currentUser");
            if (sessionToken != null && user != null) {
                String vertifiedToken = SHA256.salt(user.getId() + '&' + user.getUserName(), sessionTokenSaltCount);
                if (vertifiedToken.equals(sessionToken)) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("无法从 session中获取 UserEntity格式的 currentUser对象", e);
        }
        return false;
    }
}
