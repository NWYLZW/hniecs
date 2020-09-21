package com.hniecs.mainserver.tool.api.impl;

import com.hniecs.mainserver.tool.api.IBaseErrorCode;
import lombok.Getter;

/**
 * @author  yijie
 * @desc    枚举常用API操作码 ResultCode.java
 * @date    2020-09-12 15:46
 * @logs[0] 2020-09-12 15:46 yijie 创建了ResultCode.java文件
 */
public enum ResultCode implements IBaseErrorCode {
    VALIDATE_FAILED(1, "参数错误"),

    SUCCESS(200, "成功"),

    REDIRECT(302, "接口重定向"),

    UNAUTHORIZED(401, "未登陆"),
    FORBIDDEN(403, "接口无权限"),
    NOT_FOUND(404, "接口不存在"),

    FAILED(500, "失败");

    @Getter
    private long code;
    @Getter
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }
}
