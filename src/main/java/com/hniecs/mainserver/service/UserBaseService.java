package com.hniecs.mainserver.service;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.UserEntity;
import com.hniecs.mainserver.model.InvitationCodeModel;
import com.hniecs.mainserver.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @desc      UserBaseService.java
 * @author    yijie
 * @date      2020-09-13
 * @logs[0]   yijie 2020-09-13 创建了文件UserBaseService.java
 * @logs[1]   2020-09-14 21:35 yijie 添加邀请码校验逻辑
 */
@Service
public class UserBaseService {

    @Resource
    private UserModel userModel;
    @Resource
    private InvitationCodeModel invitationCodeModel;

    public String login(String userName, String password) {
        return userModel.vertify(userName, password);
    }
    /**
     * 注册新用户
     * @param userName  用户名
     * @param password  密码
     * @param invitationCode  邀请码
     */
    public String registerNewUser(String userName, String password, String invitationCode) {
        InvitationCodeEntity invitationCodeEntity = invitationCodeModel.findAbleUse(invitationCode);
        String msg = "验证码不存在";
        if (invitationCodeEntity == null) {
            return msg;
        }
        msg = invitationCodeModel.useInvitationCode(invitationCodeEntity);
        if (!msg.equals("0")) {
            return msg;
        }

        UserEntity u = new UserEntity(userName, password);
        msg = userModel.addUser(u);
        return msg;
    }
}
