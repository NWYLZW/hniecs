package com.hniecs.mainserver.entity.permission;

/**
 * @desc      管理应用下用户权限 AdminPermission.java
 * @author    yijie
 * @date      2020-09-22 01:27
 * @logs[0]   2020-09-22 01:27 yijie 创建了文件AdminPermission.java
 */
public interface AdminPermissions extends IPermissions {
    String NAME = "admin";

    long SEARCH_ALL_USER = 0X001;
}
