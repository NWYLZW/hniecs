package com.hniecs.mainserver.controller;

import com.hniecs.mainserver.entity.User;
import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @RequestMapping(value = "/user/base/login", method = RequestMethod.POST)
    public CommonResult login(@RequestBody User user) {
        if (user.getUserName().equals("admin") && user.vertifyPWD("123")) {
            return CommonResult.success("admin");
        }
        else {
            return CommonResult.validateFailed();
        }
    }
}
