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
 * @logs[1] 2020-09-15 15:51 yijie 完善一些内容
 */
@Repository
@Slf4j
public class InvitationCodeModel {

    @Resource
    private InvitationCodeDao invitationCodeDao;

    /**
     * 查询此邀请码是否能用
     * @param invitationCode    邀请码
     */
    public InvitationCodeEntity findAbleUse(String invitationCode) {
        InvitationCodeEntity ic = invitationCodeDao
            .getOne(
                InvitationCodeDao.columnName.invitation_code,
                invitationCode
            );

        if( ic != null && ic.getStatus() != 2 && ic.getAvailableInviteCount() > 0) {
            return ic;
        }else {
            return null;
        }
    }

    /**
     * 一个用户对应一个邀请码，每个用户他的邀请码有效次数有限
     * @param user                  用户实体
     * @param availableInviteCount  能邀请的用户个数
     * @param invitationCodes       邀请码数组
     */
    public String addInvitationCodes(
        UserEntity user,
        int availableInviteCount,
        ArrayList<String> invitationCodes
    ) {
        InvitationCodeEntity ic = new InvitationCodeEntity();
        ic.setMtime(new Date());
        ic.setCtime(new Date());
        ic.setCreateUserId(user.getId());
        ic.setAvailableInviteCount(availableInviteCount);
        ic.setStatus(0);
        for (String invitationCode : invitationCodes) {
            ic.setInvitationCode(invitationCode);
            try {
                invitationCodeDao.insert(ic);
            } catch (Exception e) {
                e.printStackTrace();
                return "插入失败, 创建者:" + user.getUserName() + "创建时间:" + new Date();
            }
        }
        return "0";
    }

    /**
     * @param invitationCode    邀请码实体
     */
    public String useInvitationCode(InvitationCodeEntity invitationCode) {
        int count = invitationCode.getAvailableInviteCount();
        if(count > 0) {
            invitationCode.setAvailableInviteCount(count - 1);
            return "0";
        }else {
            return "邀请码可用次数已用尽, 使用失败!";
        }
    }
}
