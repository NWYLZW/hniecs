package com.hniecs.mainserver.handler;

import com.hniecs.mainserver.tool.CommonUseStrings;
import com.hniecs.mainserver.tool.EnvController;
import com.hniecs.mainserver.tool.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @desc    全局异常拦截 返回json数据响应 ExceptionsHandler.java
 * @author  yijie
 * @date    2020-09-13
 * @logs[0] yijie 2020-09-13 创建了文件ExceptionsHandler.java
 */
@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {
    @Autowired
    private EnvController<RuntimeException, List> envController;

    /**
     * 运行时异常
     * @param exception 抛出的异常信息
     */
    @ExceptionHandler(RuntimeException.class)
    public CommonResult<Object> runtimeExceptionHandler(RuntimeException exception) {
        String code;
        String message;
        exception.printStackTrace();

        Matcher m = Pattern
            .compile("\\[(\\d*)\\]\\s*\\{([\\s|\\S]*)\\}")
            .matcher(exception.getMessage());

        if (m.find()) {
            code = m.group(1);
            message = m.group(2);
        } else {
            code = "500";
            message = CommonUseStrings.SERVER_FAILED.S;
        }

        log.error("运行时异常：{}", message);
        return new CommonResult<>(Integer.parseInt(code), message, envController.run(new HashMap<>() {{
                put("dev", exception -> Arrays.asList(
                    exception.getStackTrace()
                ));
                put("pro", exception -> null);
            }}, exception)
        );
    }
}
