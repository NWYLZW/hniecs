package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.InvitationCodeDao;
import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @desc    InvitationCodeModel.java
 * @author  yijie
 * @date    2020-09-13 18:57
 * @logs[0] 2020-09-13 18:57 yijie 创建了InvitationCodeModel.java文件
 */
@Repository
@Slf4j
public class InvitationCodeModel {

    @Resource
    private InvitationCodeDao invitationCodeDao;

    /**
     * TODO lh 根据邀请码搜索数据库中能狗被使用的邀请码实体，没搜索到返回null
     * @param invitationCode    邀请码
     */
    public InvitationCodeEntity findAbleUse(String invitationCode) {
        return null;
    }

    /**
     * TODO lh 添加邀请码组
     * 一个用户对应一个邀请码，每个用户他的邀请码有效次数有限
     * @param user              用户实体
     * @param canInviteCount    能邀请的用户个数
     * @param invitationCodes   邀请码数组
     */
    public String addInvitationCodes(
        UserEntity user,
        int canInviteCount,
        ArrayList<String> invitationCodes
    ) {
        for (String invitationCode : invitationCodes) {

        }
        return "";
    }

    /**
     * TODO lh 邀请码数组 使用一个邀请码
     * @param invitationCode   邀请码实体
     */
    public String useInvitationCode(InvitationCodeEntity invitationCode) {
        int count = invitationCode.getCanInviteCount();
        if(count > 0) {
            invitationCode.setCanInviteCount(count - 1);
            return "使用成功";
        }else {
            return "可用次数已用尽,使用失败!";
        }
    }
}
