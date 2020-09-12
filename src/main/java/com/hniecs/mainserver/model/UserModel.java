package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.UserDao;
import com.hniecs.mainserver.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author  yijie
 * @desc    UserModel.java
 * @date    2020-09-13
 * @logs[0] yijie 2020-09-13 创建了文件UserModel.java
 */
@Slf4j
@Repository
public class UserModel {
    @Resource
    @Autowired
    private UserDao userDao;

    /**
     * 添加用户
     * @param user  用户entity实体结构
     */
    public String addUser (UserEntity user) {
        UserEntity u = userDao.getUserSimpleByUserName(user.getUserName());
        if (u != null) return "该用户名用户已存在";
        try {
            userDao.insert(user);
            return "0";
        } catch (Exception e) {
            log.error("插入用户出现了错误：{}", e.getMessage(), e);
            return "服务器错误";
        }
    }
}
