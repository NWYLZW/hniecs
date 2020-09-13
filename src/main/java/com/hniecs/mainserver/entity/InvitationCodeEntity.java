package com.hniecs.mainserver.entity;

import lombok.Data;

/**
 * @desc    邀请码实体对象 InvitationCodeEntity.java
 * @author  yijie
 * @date    2020-09-13 18:37
 * @logs[0] 2020-09-13 18:37 yijie 创建了InvitationCodeEntity.java文件
 */
@Data
public class InvitationCodeEntity {
    // 邀请码id
    public long id;
    // 创建邀请码的用户id
    public long createUserId;

    // 邀请码内容 长度在50个以内
    public String invitationCode;
    // 邀请码状态 0 未使用，1 已使用，2 禁止使用
    public int status;
    // 剩余邀请次数 -1 无限邀请
    public int canInviteCount;

    @Override
    public String toString() {
        return "InvitationCodeEntity{" +
            "id=" + id +
            ", invitationCode='" + invitationCode + '\'' +
            ", status=" + status +
            ", canInviteCount=" + canInviteCount +
            ", createUserId=" + createUserId +
            '}';
    }
}
