package com.hniecs.mainserver.tool.api;

import com.hniecs.mainserver.tool.api.itf.HErrorCode;

/**
 * @author  yijie
 * @desc    枚举常用API操作码 ResultCode.java
 * @date    2020-09-12 15:46
 * @logs[0] 2020-09-12 15:46 yijie 创建了ResultCode.java文件
 */
public enum ResultCode implements HErrorCode {
    SUCCESS(200, "成功"),

    REDIRECT(302, "接口重定向"),

    UNAUTHORIZED(401, "未登陆"),
    FORBIDDEN(403, "接口无权限"),
    VALIDATE_FAILED(404, "接口不存在"),

    FAILED(500, "失败");

    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }
    public long getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
