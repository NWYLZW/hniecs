package com.hniecs.mainserver.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 陈桢梁
 * @desc $END$ fileEntity.java
 * @date 2020-09-25 22:04
 * @logs[0] 2020-09-25 22:04 陈桢梁 创建了fileEntity.java文件
 */
@Data
public class FileEntity {
    /**
     * 图片id
     */
    private Long id;
    /**
     * 图片所属用户id
     */
    private Long userId;
    /**
     * 图片在服务器上保存的路径
     */
    private String path;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 修改时间
     */
    private Date mtime;

    @Override
    public String toString(){
        String charList="{" +
            "id:" +id+
            "path :"+path+
            "ctime :"+ctime+
            "mtime :";
        if(mtime == null){
            charList+="null";
        }else {
            charList+=mtime;
        }
        charList+="}";
        return charList;
    }
}
