package com.hniecs.mainserver.service.user;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.user.UserDetailEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.exception.UserExceptions;
import com.hniecs.mainserver.model.InvitationCodeModel;
import com.hniecs.mainserver.model.UserModel;
import com.hniecs.mainserver.tool.security.SessionTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @desc    UserBaseService.java
 * @author  yijie
 * @date    2020-09-13 00:00
 * @logs[0] 2020-09-13 00:00 yijie 创建了文件UserBaseService.java
 * @logs[1] 2020-09-14 21:35 yijie 添加邀请码校验逻辑
 * @logs[2] 2020-09-16 22:08 yijie 优化登陆逻辑
 * @logs[3] 2020-09-16 22:42 yijie 抽离sessionToken处理，集中管理易于处理加密次数
 * @logs[4] 2020-11-18 12:56 yijie 重构代码
 */
@Service
@Slf4j
public class UserBaseService {
    @Resource
    private UserModel userModel;
    @Resource
    private InvitationCodeModel invitationCodeModel;

    /**
     * 登陆，将信息储存到session中
     * @param userName      用户名
     * @param password      密码
     */
    public Map<String, Object> login(String userName, String password) {
        UserEntity user = userModel.vertify(userName, password);
        return new HashMap<>(){{
            put(
                "sessionToken", SessionTool.setUserSessionToken(user).toString()
            );
            put("user", user);
        }};
    }

    /**
     * 注册新用户
     * @param userName          用户名
     * @param password          密码
     * @param realName          真实姓名
     * @param schoolNum         学号
     * @param profession        专业名
     * @param classNum          班级号
     * @param qqNum             qq号码
     * @param telNum            电话号码
     * @param invitationCode    邀请码
     */
    @Transactional
    public void registerNewUser(
        String userName, String password
        , String realName, String schoolNum
        , String profession, String classNum
        , String qqNum, String telNum
        , String invitationCode
    ) {
        if (userModel.have(userName)) {
            throw UserExceptions.EXIST_USER_BY_USERNAME.exception;
        }

        InvitationCodeEntity ice = invitationCodeModel.findAbleUse(invitationCode);
        if (ice == null) {
            throw UserExceptions.NOT_FOUND_INVITATION_CODE.exception;
        }

        invitationCodeModel.useInvitationCode(ice);

        UserEntity u = new UserEntity(userName, password);
        u.setCtime(new Date());
        u.setDetail(new UserDetailEntity(
            realName, profession,
            classNum, schoolNum,
            qqNum, telNum,
            ice.getId(), 1, new Date()
        ));
        userModel.addUser(u);
    }
}
