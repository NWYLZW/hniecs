package com.hniecs.mainserver.service.user;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.user.UserDetailEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.model.InvitationCodeModel;
import com.hniecs.mainserver.model.UserModel;
import com.hniecs.mainserver.tool.security.SessionTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
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
     * @param returnData    返回数据字典
     */
    public String login(String userName, String password, Hashtable returnData) {
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
     * 通过用户名获取用户可公开数据
     * @param userName 用户名
     * @param getUserDetailList 获取用户数据的数组
     */
    public String getByUserNameOrUserId(String userName, ArrayList<UserDetailEntity> getUserDetailList){
        UserDetailEntity userDetailEntity;
        if(!userModel.have(userName)){
            return "用户名不存在";
        }
    }

    /**
     * 通过用户id获取用户可公开数据
     * @param id 用户id
     * @param getUserDetailList 获取用户数据的数组
     */
    public String getByUserNameOrUserId(long id, ArrayList<UserDetailEntity> getUserDetailList){}
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
    public String registerNewUser(
        String userName, String password
        , String realName, String schoolNum
        , String profession, String classNum
        , String qqNum, String telNum
        , String invitationCode
    ) {
        if (userModel.have(userName)) {
            return "该用户名用户已存在";
        }

        InvitationCodeEntity ice = invitationCodeModel.findAbleUse(invitationCode);
        if (ice == null) {
            return "邀请码不存在";
        }

        String msg = invitationCodeModel.useInvitationCode(ice);
        if (!msg.equals("0")) {
            return msg;
        }

        UserEntity u = new UserEntity(userName, password);
        u.setCtime(new Date());
        u.setDetail(new UserDetailEntity(
            realName, profession,
            classNum, schoolNum,
            qqNum, telNum,
            ice.getId(), 1, new Date()
        ));
        msg = userModel.addUser(u);
        return msg;
    }
}
