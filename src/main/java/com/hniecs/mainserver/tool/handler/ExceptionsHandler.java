package com.hniecs.mainserver.tool.handler;

import com.hniecs.mainserver.tool.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @desc    全局异常拦截 返回json数据响应 ExceptionsHandler.java
 * @author  yijie
 * @date    2020-09-13
 * @logs[0] yijie 2020-09-13 创建了文件ExceptionsHandler.java
 */
@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

    /**
     * 运行时异常
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public CommonResult runtimeExceptionHandler(RuntimeException ex) {
        log.error("运行时异常：{}", ex.getMessage(), ex);
        return CommonResult.failed("服务器异常");
    }
}
