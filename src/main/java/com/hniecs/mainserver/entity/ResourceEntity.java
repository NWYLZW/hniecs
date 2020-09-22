package com.hniecs.mainserver.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @desc    下载资源 ResourceEntity.java
 * @author  陈桢梁
 * @date    2020-09-14 20:08
 * @logs[0] 2020-09-14 20:08 陈桢梁 创建了ResourceEntity.java文件
 */
public class ResourceEntity {
    /**
     * 资源id
     */
    @Getter
    @Setter
    private long id = -1;

    /**
     * 资源名
     */
    @Getter
    @Setter
    private String name;

    /**
     * 资源种类
     */
    @Getter
    @Setter
    private String kind;

    /**
     * 资源介绍
     */
    @Getter
    @Setter
    private String introduce;

    /**
     * 资源url
     */
    @Getter
    @Setter
    private String url;

    /**
     * 资源创建时间
     */
    @Getter
    @Setter
    private Date mtime;

    /**
     * 资源修改时间
     */
    @Getter
    @Setter
    private Date ctime;

    public ResourceEntity(String name, String url, String introduce, String kind) {
        this.ctime = null;
        this.name = name;
        this.url = url;
        this.kind = kind;
        if (introduce == null) {
            this.introduce = "无";
        } else {
            this.introduce = introduce;
        }
    }
    @Override
    public String toString() {
        return "ResourceEntity{" +
            "id=" + id +
            ",name=" + name +
            ",kind=" + kind +
            ",introduce=" + introduce +
            ",url=" + url +
            "}";
    }
}
