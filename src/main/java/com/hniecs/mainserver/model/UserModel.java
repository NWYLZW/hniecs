package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.UserDao;
import com.hniecs.mainserver.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Hashtable;

/**
 * @desc    UserModel.java
 * @author  yijie
 * @date    2020-09-13 00:00
 * @logs[0] 2020-09-13 00:00 yijie 创建了文件UserModel.java
 * @logs[1] 2020-09-16 22:39 yijie 添加 vertify 方法的多态，给userBaseService处理
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
     * 检查是否能够登录
     * @param userName      用户名
     * @param password      密码
     * @param returnData    多余的返回值
     */
    public String vertify(String userName, String password, Hashtable returnData) {
        UserEntity u = get(userName);
        if (u == null) return "该用户名用户不存在";
        if (u.vertifyPWD(password)) {
            returnData.put("userData", u);
            return "0";
        } else {
            return "密码错误";
        }
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
