package com.hniecs.mainserver.controller;

import com.hniecs.mainserver.service.UserBaseService;
import com.hniecs.mainserver.tool.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author  yijie
 * @desc    UserBaseController.java
 * @date    2020-09-13
 * @logs[0] yijie 2020-09-13 创建了文件UserBaseController.java
 * @logs[1] yijie 2020-09-13 修改了文件名
 */
@RestController
@Slf4j
public class UserBaseController {
    @Resource
    @Autowired
    private UserBaseService userBaseService;

    @PostMapping("/user/base/login")
    public CommonResult login(@RequestBody Map<String, String> user) {
        String msg = userBaseService.login(
            user.get("userName"),
            user.get("password")
        );
        if (msg.equals("0")) {
            return CommonResult.success("登录成功");
        } else {
            return CommonResult.validateFailed(msg);
        }
    }
    @PostMapping("/user/base/register")
    public CommonResult register(@RequestBody Map<String, String> registerData) {
        String msg = userBaseService.registerNewUser(
            registerData.get("userName"),
            registerData.get("password")
        );
        if (msg.equals("0")) {
            return CommonResult.success("注册成功");
        } else {
            return CommonResult.validateFailed(msg);
        }
    }
}
