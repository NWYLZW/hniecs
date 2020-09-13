package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.InvitationCodeDao;
import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * @desc    InvitationCodeModel.java
 * @author  yijie
 * @date    2020-09-13 18:57
 * @logs[0] 2020-09-13 18:57 yijie 创建了InvitationCodeModel.java文件
 */
@Slf4j
@Repository
public class InvitationCodeModel {
    @Resource
    @Autowired
    private InvitationCodeDao invitationCodeDao;

    /**
     * TODO lh 添加验证码组
     * @param user              用户实体
     * @param canInviteCount    能邀请的用户个数
     * @param invitationCodes   验证码数组
     */
    public String addInvitationCodes(
        UserEntity user,
        int canInviteCount,
        ArrayList<String> invitationCodes
    ) {
        return "";
    }

    /**
     * TODO lh 验证码数组 使用一个验证码
     * @param invitationCode   验证码实体
     */
    public String useInvitationCode(
        InvitationCodeEntity invitationCode
    ) {
        return "";
    }
}
