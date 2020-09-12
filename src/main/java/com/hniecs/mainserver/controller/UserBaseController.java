package com.hniecs.mainserver.controller;

import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author  yijie
 * @desc    UserBaseController.java
 * @date    2020-09-13
 * @logs[0] yijie 2020-09-13 创建了文件UserBaseController.java
 * @logs[1] yijie 2020-09-13 修改了文件名
 */
@RestController
public class UserBaseController {
    @PostMapping("/user/base/login")
    public CommonResult login(@RequestBody Map<String,Object> user) {
        if (user.get("userName").equals("admin")) {
            return CommonResult.success("admin");
        } else {
            return CommonResult.validateFailed();
        }
    }
    @PostMapping("/user/base/register")
    public CommonResult register(@RequestBody Map<String,Object> registerData) {
        if (registerData.get("userName").equals("admin")) {
            return CommonResult.success("admin");
        } else {
            return CommonResult.validateFailed();
        }
    }
}
