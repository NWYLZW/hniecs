package com.hniecs.mainserver.controller;

import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author  yijie
 * @desc    错误控制 MainErrorController.js
 * @date    2020-09-12 18:26
 * @logs[0] 2020-09-12 18:26 yijie 创建了MainErrorController.js文件
 */
@RestController
public class MainErrorController implements ErrorController {
    @Autowired
    private ErrorAttributes errorAttributes;
    /**
     * 默认错误
     */
    private static final String path_default = "/error";
    @Override
    public String getErrorPath() {
        return path_default;
    }
    /**
     * 错误信息
     */
    @RequestMapping()
    public CommonResult error(HttpServletRequest request) {
        return CommonResult.notFound();
    }
}
