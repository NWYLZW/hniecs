package com.hniecs.mainserver.service.user;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.model.InvitationCodeModel;
import com.hniecs.mainserver.model.UserModel;
import com.hniecs.mainserver.tool.security.SessionTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Hashtable;

/**
 * @desc    UserBaseService.java
 * @author  yijie
 * @date    2020-09-13 00:00
 * @logs[0] 2020-09-13 00:00 yijie 创建了文件UserBaseService.java
 * @logs[1] 2020-09-14 21:35 yijie 添加邀请码校验逻辑
 * @logs[2] 2020-09-16 22:08 yijie 优化登陆逻辑
 * @logs[3] 2020-09-16 22:42 yijie 抽离sessionToken处理，集中管理易于处理加密次数
 */
@Service
public class UserBaseService {
    @Resource
    private UserModel userModel;
    @Resource
    private InvitationCodeModel invitationCodeModel;

    /**
     * 登陆，将信息储存到session中
     * @param userName      用户名
     * @param password      密码
     * @param session       HttpSession字典
     * @param returnData    返回数据字典
     */
    public String login(String userName, String password, HttpSession session, Hashtable returnData) {
        Hashtable getReturnData = new Hashtable();
        String msg = userModel.vertify(userName, password, getReturnData);
        if (msg.equals("0")) {
            UserEntity user = (UserEntity) getReturnData.get("userData");
            returnData.put(
                "sessionToken",
                SessionTool
                    .setUserSessionToken(user)
                    .toString()
            );
            returnData.put("user", user);
        }
        return msg;
    }
    /**
     * 注册新用户
     * @param userName  用户名
     * @param password  密码
     * @param invitationCode  邀请码
     */
    public String registerNewUser(String userName, String password, String invitationCode) {
        InvitationCodeEntity invitationCodeEntity = invitationCodeModel.findAbleUse(invitationCode);
        String msg = "邀请码不存在";
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
