package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.UserDao;
import com.hniecs.mainserver.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @desc    UserModel.java
 * @author  yijie
 * @date    2020-09-13
 * @logs[0] 2020-09-13 yijie 创建了文件UserModel.java
 */
@Slf4j
@Repository
public class UserModel {

    @Resource
    private UserDao userDao;

    /**
     * 获取某个用户名对应用户
     * @param userName 用户名
     */
    private UserEntity get(String userName) {
        return userDao.getUserSimpleByUserName(userName);
    }

    /**
     * 检查是否有某个用户
     * @param userName 用户名
     */
    private boolean have(String userName) {
        UserEntity u = userDao.getUserSimpleByUserName(userName);
        return u != null;
    }

    /**
     * 检查是否能够登录
     * @param userName  用户名
     * @param password  密码
     */
    public String vertify(String userName, String password) {
        UserEntity u = get(userName);
        if (u == null) return "该用户名用户不存在";
        return u.vertifyPWD(password)?"0":"密码错误";
    }

    /**
     * 添加用户
     * @param user  用户entity实体结构
     */
    public String addUser (UserEntity user) {
        if (have(user.getUserName())) return "该用户名用户已存在";
        try {
            userDao.addNew(user);
            return "0";
        } catch (Exception e) {
            log.error("插入用户出现了错误：{}", e.getMessage(), e);
            return "服务器错误";
        }
    }
}
