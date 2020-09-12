package com.hniecs.mainserver.controller;

import com.hniecs.mainserver.entity.User;
import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @PostMapping(value = "/user/base/login")
    public CommonResult login(@RequestBody User user) {
        if (user.getUserName().equals("admin") && user.vertifyPWD("123")) {
            return CommonResult.success("admin");
        }
        else {
            return CommonResult.validateFailed();
        }
    }
}
