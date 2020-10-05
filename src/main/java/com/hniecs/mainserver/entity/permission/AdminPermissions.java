package com.hniecs.mainserver.entity.permission;

/**
 * @desc      管理应用下用户权限 AdminPermission.java
 * @author    yijie
 * @date      2020-09-22 01:27
 * @logs[0]   2020-09-22 01:27 yijie 创建了文件AdminPermission.java
 */
public interface AdminPermissions extends IPermissions {
    String NAME = "admin";

    // 操作邀请码权限
    long OPERATE_INVITATION_CODES = 0X001;
    // 搜索邀请码列表权限
    long SEARCH_INVITATION_CODES = 0X002;
}
