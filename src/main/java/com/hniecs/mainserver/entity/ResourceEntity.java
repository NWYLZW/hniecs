package com.hniecs.mainserver.entity;

import lombok.Data;

import java.util.Date;

/**
 * @desc    下载资源 ResourceEntity.java
 * @author  陈桢梁
 * @date    2020-09-14 20:08
 * @logs[0] 2020-09-14 20:08 陈桢梁 创建了ResourceEntity.java文件
 */
@Data
public class ResourceEntity {
    /**
     * 资源id
     */
    private long id;

    /**
     * 资源名
     */
    private String name;

    /**
     * 资源种类
     */
    private String kind;

    /**
     * 资源介绍
     */
    private String introduce;

    /**
     * 资源url
     */
    private String url;

    /**
     * 资源创建时间
     */
    private Date mtime;

    /**
     * 资源修改时间
     */
    private Date ctime;

    public ResourceEntity(String name, String url, String introduce, String kind) {
        if (mtime == null) {
            mtime = new Date();
        }
        this.ctime = null;
        this.name = name;
        this.url = url;
        this.kind = kind;
        if (introduce == null) {
            introduce = "无";
        }
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "ResourceEntity{" +
            "id=" + id +
            "name=" + name +
            "kind=" + kind +
            "introduce=" + introduce +
            "url=" + url +
            "}";
    }
}
