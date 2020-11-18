package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.UserDao;
import com.hniecs.mainserver.entity.user.UserDetailEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.exception.CommonExceptions;
import com.hniecs.mainserver.tool.CommonUseStrings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * @desc    UserModel.java
 * @author  yijie
 * @date    2020-09-13 00:00
 * @logs[0] 2020-09-13 00:00 yijie 创建了文件UserModel.java
 * @logs[1] 2020-09-16 22:39 yijie 添加 vertify 方法的多态，给userBaseService处理
 * @logs[2] 2020-11-18 12:56 yijie 重构代码
 */
@Slf4j
@Repository
public class UserModel {

    @Resource
    private UserDao userDao;

    @Resource
    private FileModel fileModel;
    /**
     * 获取某个用户名对应用户
     * @param userName 用户名
     */
    private UserEntity get(String userName) {
        if (userName.equals("")) {
            return null;
        }
        try {
            return userDao.getSimpleByUserName(userName);
        } catch (Exception e) {
            log.error("获取用户简略信息时出现了错误", e);
            return null;
        }
    }
    public boolean injectDetailData (UserEntity u) {
        if (u == null) {
            return false;
        }
        try {
            UserDetailEntity userDetailEntity = userDao.getDetailById(u.getId());
            u.setDetail(
                userDetailEntity
            );
            return true;
        } catch (Exception e) {
            log.error("注入用户详细信息时出现了错误", e);
            return false;
        }
    }

    /**
     * 检查是否有某个用户
     * @param userName 用户名
     */
    public boolean have(String userName) {
        UserEntity u = get(userName);
        return u != null;
    }

    /**
     * 检查是否能够登录
     * @param userName      用户名
     * @param password      密码
     */
    public UserEntity vertify(String userName, String password) {
        UserEntity u = get(userName);
        if (u == null) {
            throw new RuntimeException("[1001] {该用户名用户不存在}");
        }
        if (u.vertifyPWD(password)) {
            return u;
        } else {
            throw new RuntimeException("[1002] {密码错误}");
        }
    }

    /**
     * 初始化用户文件夹
     * @param userEntity 用户实体
     */
    private boolean createfileDir(File newFile,UserEntity userEntity) {
        String basePath = System.getProperty("user.dir");
        basePath += "/workplace/public/"+userEntity.getId()+"/image";
        try {
            newFile = new File(basePath);
            newFile.mkdirs();
            return true;
        }catch (Exception e){
            newFile.getParentFile().delete();
            return false;
        }
    }
    /**
     * 添加用户
     * @param user  用户entity实体结构
     */
    public void addUser(UserEntity user) {
        if (user.getDetail() == null) {
            log.error("传给userModel.addUser方法未设置detail属性信息");
            throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
        }
        try {
            if (userDao.addNew(user) == 0) {
                throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
            }
            // 获取添加后的用户id 加到userDetail中
            UserEntity newU = userDao.getSimpleByUserName(user.getUserName());
            user.getDetail().setUserId(newU.getId());
            userDao.addNewDetail(user.getDetail());
            createfileDir(new File(""),user);
        } catch (Exception e) {
            e.printStackTrace();
            throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
        }
    }
}
