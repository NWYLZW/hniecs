package com.hniecs.mainserver.service;

import com.hniecs.mainserver.entity.UserEntity;
import com.hniecs.mainserver.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @desc      UserBaseService.java
 * @author    yijie
 * @date      2020-09-13
 * @logs[0]   yijie 2020-09-13 创建了文件UserBaseService.java
 */
@Service
public class UserBaseService {
    @Resource
    @Autowired
    private UserModel userModel;

    /**
     * 注册新用户
     * @param userName  用户名
     * @param password  密码
     */
    public String registerNewUser(String userName, String password) {
        UserEntity u = new UserEntity(userName, password);
        String msg = userModel.addUser(u);
        return msg;
    }
}
