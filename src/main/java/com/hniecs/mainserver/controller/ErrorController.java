package com.hniecs.mainserver.controller;

import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author  yijie
 * @desc    错误控制 ErrorControler.js
 * @date    2020-09-12 18:26
 * @logs[0] 2020-09-12 18:26 yijie 创建了ErrorControler.js文件
 */
@RestController
public class ErrorController {
    @PostMapping(value = "/error/notFound")
    public CommonResult notFound() {
        return CommonResult.notFound();
    }
}
