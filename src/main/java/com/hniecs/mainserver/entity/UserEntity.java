package com.hniecs.mainserver.entity;

import com.hniecs.mainserver.tool.security.Password;
import lombok.Getter;
import lombok.Setter;

/**
 * @desc    用户基类
 * @author  yijie
 * @date    2020-09-12 15:34
 * @logs[0] 2020-09-13 01:47 yijie 添加了密码生成与校验
 */
public class UserEntity {
    // 用户id
    @Getter
    private long id;
    // 用户名
    @Getter @Setter
    private String userName;
    // SHA算法加密后的密码
    private String passwordSHA;

    public UserEntity(String userName, String password) {
        this.userName = userName;
        this.setPassword(password);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
            "id=" + id +
            ", userName='" + userName + '\'' +
            '}';
    }

    public void setPassword (String password) {
        this.passwordSHA = Password.generatePasswordHash(password);
    }
    /**
     * 校验密码是否正确
     * @param password 待校验密码
     */
    public boolean vertifyPWD (String password) {
        return Password.checkPasswordHash(this.passwordSHA, password);
    }
}
