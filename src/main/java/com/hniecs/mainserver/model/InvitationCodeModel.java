package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.InvitationCodeDao;
import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

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
     * 查询此邀请码是否能用
     * @param invitationCode    邀请码
     * @logs[] 2020-09-14 21:56 zerohua
     */
    public InvitationCodeEntity findAbleUse(String invitationCode) {

        InvitationCodeEntity entity = invitationCodeDao
            .getOne(InvitationCodeDao.columnName.invitation_code,invitationCode);

        if( entity != null && entity.getStatus() != 2 && entity.getAvailableInviteCount() > 0) {
            return entity;
        }else {
            return null;
        }
    }

    /**
     * 一个用户对应一个邀请码，每个用户他的邀请码有效次数有限
     * @param user              用户实体
     * @param availableInviteCount  能邀请的用户个数
     * @param invitationCodes   邀请码数组
     * @logs[] 2020-09-14 21:48 zerohua
     */
    public String addInvitationCodes(
        UserEntity user,
        int availableInviteCount,
        ArrayList<String> invitationCodes
    ) {
        for (String invitationCode : invitationCodes) {
            InvitationCodeEntity entity = new InvitationCodeEntity();
            entity.setMtime(new Date());
            entity.setCtime(new Date());
            entity.setCreateUserId(user.getId());
            entity.setInvitationCode(invitationCode);
            entity.setAvailableInviteCount(availableInviteCount);
            entity.setStatus(0);
            try {
                invitationCodeDao.insert(entity);
            } catch (Exception e) {
                e.printStackTrace();
                return "插入失败,创建者:"+user.getUserName()+"执行时间:"+new Date();
            }
        }
        return "0";
    }

    /**
     * @param invitationCode   邀请码实体
     * @logs[] 2020-09-14 21:48 zerohua
     */
    public String useInvitationCode(InvitationCodeEntity invitationCode) {

        InvitationCodeEntity entity = invitationCodeDao
            .getOne(InvitationCodeDao.columnName.invitation_code,invitationCode.getInvitationCode());
        int count = invitationCode.getAvailableInviteCount();
        if(count > 0) {
            invitationCode.setAvailableInviteCount(count - 1);
            return "使用成功";
        }else {
            return "可用次数已用尽,使用失败!";
        }
    }
}
