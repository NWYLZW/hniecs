package com.hniecs.mainserver.controller;

import com.hniecs.mainserver.tool.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author  yijie
 * @desc    错误控制 MainErrorController.js
 * @date    2020-09-12 18:26
 * @logs[0] 2020-09-12 18:26 yijie 创建了MainErrorController.js文件
 */
@RestController
@Slf4j
public class MainErrorController implements ErrorController {
    /**
     * 默认错误路由
     */
    private static final String PATH  = "/error";

    @Override
    public String getErrorPath() {
        return PATH ;
    }

    /**
     * 处理响应码错误
     */
    @RequestMapping(value = PATH ,  produces = {MediaType.APPLICATION_JSON_VALUE})
    public CommonResult error(HttpServletRequest request) {
        // 获取响应码
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 401) {
            return CommonResult.unauthorized();
        } else if(statusCode == 403) {
            return CommonResult.forbidden();
        } else if(statusCode == 404) {
            return CommonResult.notFound();
        }

        return CommonResult.notFound();
    }
}
