package com.hniecs.mainserver.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @desc        用户基类
 * @author      yijie
 * @date        2020-09-12 15:34
 */
public class User {
    @Getter
    private int id;
    @Getter
    @Setter
    private String userName;
    private String passwordSHA;

    public void password(String password) {
        this.passwordSHA = password;
    }
    public boolean vertifyPWD (String password) {
        return this.passwordSHA.equals(password);
    }
}
