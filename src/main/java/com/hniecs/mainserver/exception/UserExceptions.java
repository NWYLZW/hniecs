package com.hniecs.mainserver.exception;

import com.hniecs.mainserver.annotation.clazz.ExceptionsEnum;

/**
 * @desc    用户相关异常 UserExceptions.java
 * @author  yijie
 * @date    2020-11-17 17:33
 * @logs[0] 2020-11-17 17:33 yijie 创建了UserExceptions.java文件
 */
@ExceptionsEnum
public enum UserExceptions {
    NOT_FOUND_USER_BY_USERNAME(10000, "该用户名用户不存在")
    ,EXIST_USER_BY_USERNAME(10001, "该用户名用户已存在")
    ,PASSWORD_WRONG(10002, "用户密码错误")

    ,NOT_FOUND_INVITATION_CODE(11000, "邀请码不存在")
    ,INVITATION_CODE_COUNT_0(11001, "邀请码可用次数已用尽");

    public RuntimeException exception;

    UserExceptions(Integer code, String message) {
        this.exception = new RuntimeException("[" + code + "] {" + message + "}");
    }
}
