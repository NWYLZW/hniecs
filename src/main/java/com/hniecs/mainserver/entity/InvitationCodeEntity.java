package com.hniecs.mainserver.entity;

import com.hniecs.mainserver.entity.user.UserEntity;
import lombok.Data;

import java.util.Date;

/**
 * @desc    邀请码实体对象 InvitationCodeEntity.java
 * @author  yijie
 * @date    2020-09-13 18:37
 * @logs[0] 2020-09-13 18:37 yijie 创建了InvitationCodeEntity.java文件
 * @logs[1] 2020-09-21 18:27 yijie 支持假删除
 */
@Data
public class InvitationCodeEntity {

    /**
     * 邀请码id
     */
    private long id;

    /**
     * 创建邀请码的用户id
     */
    private long createUserId;

    /**
     * 邀请码内容 长度在50个以内
     */
    private String invitationCode;

    /**
     * 标签
     */
    private String tagName;

    /**
     * 邀请码状态 -1 已删除，0 未使用，1 已使用，2 禁止使用
     */
    private int status;

    /**
     * 剩余邀请次数 -1 无限邀请
     */
    private int availableInviteCount;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 修改时间
     */
    private Date mtime;

    /**
     * 创建者实体
     */
    private UserEntity creator;

}
