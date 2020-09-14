package com.hniecs.mainserver.tool.api.itf;

/**
 * @desc    HNIECS的API错误码基接口 HErrorCode.java
 * @author  yijie
 * @date    2020-09-12 15:44
 * @logs[0] 2020-09-12 15:44 yijie 创建了HErrorCode.java文件
 */
public interface HErrorCode {
    /**
     * @return 错误码
     */
    long getCode();

    /**
     * @return 错误信息
     */
    String getMessage();
}
