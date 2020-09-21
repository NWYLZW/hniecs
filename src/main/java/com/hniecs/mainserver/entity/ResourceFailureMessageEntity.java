package com.hniecs.mainserver.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @desc     ResourceFailureMessageEntity.java
 * @author  陈桢梁
 * @date    2020-09-20 15:31
 * @logs[0] 2020-09-20 15:31 陈桢梁 创建了ResourceFailureMessageEntity.java文件
 */
public class ResourceFailureMessageEntity {
    public enum Status{
        unsolved,solved;
    }
    //信息id
    @Getter
    long id;

    //资源id
    @Setter
    @Getter
    long resourceId;

    //信息状态
    @Setter
    @Getter
    Status status;

    //创建时间
    @Getter
    Date mtime;

    //设置时间
    @Getter
    @Setter
    Date ctime;

    public ResourceFailureMessageEntity(long resourceId){
        if(mtime==null){
            mtime=new Date();
        }
        this.resourceId=resourceId;
        ctime=null;
        status=Status.unsolved;
    }
}
