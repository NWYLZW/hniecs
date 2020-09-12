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
    @Getter
    private int id;
    @Getter
    @Setter
    private String userName;
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
    public boolean vertifyPWD (String password) {
        return Password.checkPasswordHash(this.passwordSHA, password);
    }
}
