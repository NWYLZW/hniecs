package com.hniecs.mainserver.entity.permission;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc       Permissions.java
 * @author    yijie
 * @date      2020-09-22 05:08
 * @logs[0]   2020-09-22 05:08 yijie 创建了文件Permissions.java
 */
public enum Permissions
    implements AdminPermissions {
    ADMIN(AdminPermissions.NAME);
    Permissions(String name) {
    }
    // app 权限组 作用域列表
    private static final Map<String, Long> SCOPES = new HashMap() {{
        put("admin",    0X00001L);
        put("resource", 0X00002L);
    }};
    public static Long getScope (String scopeName) {
        return SCOPES.get(scopeName);
    }
}
