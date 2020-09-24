package com.hniecs.mainserver.entity;

import com.hniecs.mainserver.entity.permission.AdminPermissions;
import lombok.Data;

/**
 * @desc    权限组 Rules.java
 * @author  yijie
 * @date    2020-09-25 04:29
 * @logs[0] 2020-09-25 04:29 yijie  创建了 Rules.java 文件
 */
public enum Rules {
    SUPPER_ADMIN(
        "超级管理员",
        AdminPermissions.SEARCH_ALL_USER
    );

    private RuleEntity r;
    Rules(String name, long permissions) {
        r = new RuleEntity(name, permissions);
    }

    /**
     * 代理 r 的can方法
     * @param permissions   权限或权限组
     */
    public boolean can(Long permissions) {
        return r.can(permissions);
    }

    /**
     * 用户规则实体
     */
    @Data
    public class RuleEntity {
        long id;
        String name;
        Long permissions;
        public RuleEntity(String name, Long permissions) {
            this.name = name;
            this.permissions = permissions;
        }

        /**
         * 校验该规则是否有某个权限或权限组
         * @param permissions   权限或权限组
         */
        public boolean can(Long permissions) {
            return (this.permissions | permissions) == permissions;
        }
    }
}
