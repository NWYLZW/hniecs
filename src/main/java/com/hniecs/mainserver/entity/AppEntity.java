package com.hniecs.mainserver.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @desc    应用实体 AppEntity.java
 * @author  yijie
 * @date    2020-10-05 21:31
 * @logs[0] 2020-10-05 21:31 yijie 创建了AppEntity.java文件
 */
@Data
@NoArgsConstructor
public class AppEntity {
    /** app 主键索引 */
    Long id;
    /** app 父应用id 用于菜单 */
    Long pid;
    /** 名字 */
    String name;
    /** 介绍 */
    String desc;
    /** 图标颜色 */
    String color;
    /** 图标UTF8编码值 */
    String iconUTF8;
    /** 跳转到的页面 */
    String url;
    /**
     * 应用所属的角色组id列表
     * 英文逗号分隔，每个id用中括号包起来
     * 为空字符串时任意用户均可使用
     */
    String ruleIds;

    /** 状态 -1 删除，0 存在，1 建设，2 禁用 */
    Integer status;

    /** 子应用列表 */
    List<AppEntity> menuChildren;

    /** 创建时间 */
    Date ctime;
    /** 修改时间 */
    Date mtime;

    public AppEntity(String name, String desc, String iconUTF8, String url, String ruleIds, Integer status) {
        this.name = name;
        this.desc = desc;
        this.iconUTF8 = iconUTF8;
        this.url = url;
        this.ruleIds = ruleIds;
        this.status = status;
    }
    public AppEntity(String name, String desc, String iconUTF8, String url, String ruleIds, Integer status, List<AppEntity> menuChildren) {
        this.name = name;
        this.desc = desc;
        this.iconUTF8 = iconUTF8;
        this.url = url;
        this.ruleIds = ruleIds;
        this.status = status;
        this.menuChildren = menuChildren;
    }
}
