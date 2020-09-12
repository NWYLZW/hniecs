package com.hniecs.mainserver.service;

import com.hniecs.mainserver.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @desc      UserBaseService.java
 * @author    yijie
 * @date      2020-09-13
 * @logs[0]   yijie 2020-09-13 创建了文件UserBaseService.java
 */
public class UserBaseService {
    @Autowired
    @Resource
    private UserDao userDao;
}
