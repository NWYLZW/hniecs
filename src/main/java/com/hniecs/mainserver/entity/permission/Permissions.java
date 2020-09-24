package com.hniecs.mainserver.entity.permission;

import com.hniecs.mainserver.entity.Rules;
import com.hniecs.mainserver.entity.user.UserEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc      权限组 Permissions.java
 * @author    yijie
 * @date      2020-09-22 05:08
 * @logs[0]   2020-09-22 05:08 yijie 创建了文件Permissions.java
 */
public class Permissions
    implements AdminPermissions {
    // app 权限组 作用域列表
    private static final Map<String, Long> SCOPES = new HashMap() {{
        put("admin",    0X00001L);
        put("resource", 0X00002L);
    }};

    /**
     * 根据权限名域对应的 perssion 域
     * @param scopeName 权限名域
     * @return  SCOPES中记录的 对应 perssion 域
     */
    private static Long getScope (String scopeName) {
        return SCOPES.get(scopeName);
    }

    /**
     * 检测是否有某个权限或权限组
     * @param user          用户实体
     * @param scopeName     作用域
     * @param permissions   某个权限或权限组
     * @return  该用户是否有该权限或权限组
     */
    public static boolean havePermission (
        UserEntity user, String scopeName, Long permissions
    ) {
        Long SCOPE = getScope(scopeName) << 20;
        Long UNIT_PERMISSION = SCOPE + permissions;
        return Rules.SUPPER_ADMIN.can(UNIT_PERMISSION);
    }
}
