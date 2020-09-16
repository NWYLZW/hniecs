package com.hniecs.mainserver.tool;

import com.hniecs.mainserver.entity.UserEntity;
import com.hniecs.mainserver.tool.security.SHA256;

import javax.servlet.http.HttpSession;

/**
 * @desc    session帮助类 sessionTool.java
 * @author  yijie
 * @date    2020-09-16 22:24
 * @logs[0] 2020-09-16 22:24 yijie 创建了sessionTool.java文件
 */
public class sessionTool {
    public static Object setUserSessionToken (HttpSession session, UserEntity user) {
        Object sessionToken = session.getAttribute("sessionToken");
        if (sessionToken == null) {
            sessionToken = SHA256.salt(user.getId() + '&' + user.getUserName(), 10);
            session.setAttribute("sessionToken", sessionToken);
            session.setAttribute("currentUser", user);
        }
        return sessionToken;
    }
}
