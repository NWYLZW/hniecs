package com.hniecs.mainserver.tool.api;

/**
 * @desc    HNIECS的API错误码基接口 IBaseErrorCode.java
 * @author  yijie
 * @date    2020-09-12 15:44
 * @logs[0] 2020-09-12 15:44 yijie 创建了IBaseErrorCode.java文件
 */
public interface IBaseErrorCode {
    /**
     * @return 错误码
     */
    long getCode();

    /**
     * @return 错误信息
     */
    String getMessage();
}
