package com.hniecs.mainserver.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author 陈桢梁
 * @desc $END$ fileEntity.java
 * @date 2020-09-25 22:04
 * @logs[0] 2020-09-25 22:04 陈桢梁 创建了fileEntity.java文件
 */
public class FileEntity {
    /**
     * 图片id
     */
    @Getter@Setter
    private Long id;
    /**
     * 图片所属用户id
     */
    @Getter@Setter
    private Long uploaderId;
    /**
     * 文件大小
     */
    @Getter@Setter
    private Long size;
    /**
     * 图片在服务器上保存的路径
     */
    @Getter
    private String path;
    /**
     * 文件类型
     */
    @Getter@Setter
    private String type;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件后缀名
     */
    private String suffix;
    /**
     * 创建时间
     */
    @Getter@Setter
    private Date ctime;

    /**
     * 修改时间
     */
    @Getter@Setter
    private Date mtime;

    private  void setPath(String path){
        this.path = path;
        String[] fileName = path.split(String.valueOf('\\'));
        name = fileName[fileName.length-1];
        suffix = name.split("\\.",2)[1];
    }
    @Override
    public String toString(){
        String charList="{" +
            "id:" +id+
            "userId:"+uploaderId+
            "size:"+size+
            "path:"+path+
            "ctime:"+ctime+
            "mtime:";
        if(mtime == null){
            charList+="null";
        }else {
            charList+=mtime;
        }
        charList+="}";
        return charList;
    }
}
