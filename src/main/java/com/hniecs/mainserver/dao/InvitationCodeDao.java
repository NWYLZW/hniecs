package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @desc    InvitationCodeDao.java
 * @author  yijie
 * @date    2020-09-13 19:01
 * @logs[0] 2020-09-13 19:01 yijie 创建了InvitationCodeDao.java文件
 */
@Mapper
public interface InvitationCodeDao {
    // TODO czl 根据(创建用户id|邀请码id|邀请码状态) 查 邀请码
    @Select("select * from ")
    InvitationCodeEntity getInvitationCodeByUserId(Long uId);

    // TODO czl 增 邀请码
    // TODO czl 根据(创建用户id|邀请码id|邀请码状态) 删 邀请码
    // TODO czl 根据(邀请码id) 改 邀请码 (邀请码状态|邀请码邀请次数|邀请码内容)
}
