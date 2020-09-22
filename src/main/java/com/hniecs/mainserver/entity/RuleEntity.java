package com.hniecs.mainserver.entity;

import com.hniecs.mainserver.entity.permission.AdminPermissions;
import lombok.Data;

/**
 * @desc      权限组实体 RuleEntity.java
 * @author    yijie
 * @date      2020-09-21 23:38
 * @logs[0]   2020-09-21 23:38 yijie 创建了文件RuleEntity.java
 */
@Data
public class RuleEntity {
    public enum Rules {
        SUPPER_ADMIN(
            "超级管理员",
            AdminPermissions.SEARCH_ALL_USER
        );

        Rules(String name, long permissions) {
        }
    }

    public static void main(String[] args) {
        Long SCOPE = 0X1001L << 20;
        Long SCOPE_UNIT_PERMISSION = 0X00002L;
        Long UNIT_PERMISSION = SCOPE + SCOPE_UNIT_PERMISSION;
        System.out.println(
            (UNIT_PERMISSION & SCOPE_UNIT_PERMISSION) == 0X00002L
        );
    }
}
