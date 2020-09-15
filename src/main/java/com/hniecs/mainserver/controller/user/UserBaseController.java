package com.hniecs.mainserver.controller.user;

import com.hniecs.mainserver.service.UserBaseService;
import com.hniecs.mainserver.tool.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author  yijie
 * @desc    UserBaseController.java
 * @date    2020-09-13 00:00
 * @logs[0] 2020-09-13 00:00 yijie 创建了文件UserBaseController.java
 * @logs[1] 2020-09-13 00:00 yijie 修改了文件名
 * @logs[2] 2020-09-15 20:15 yijie 添加了登陆接口
 */
@RestController
@Slf4j
public class UserBaseController {

    @Resource
    private UserBaseService userBaseService;

    /**
     * 用户登陆
     * @param user      用户信息
     * @param session   httpSession，由mvc容器自己管理
     * @bodyParam userName    Y   ""    用户名
     * @bodyParam password    Y   ""    密码
     */
    @PostMapping("/user/base/login")
    public CommonResult login(@RequestBody Map<String, String> user, HttpSession session) {
        String msg = userBaseService.login(
            user.get("userName"),
            user.get("password")
        );
        if (msg.equals("0")) {
            Object sessionToken = session.getAttribute("sessionToken");
            if (sessionToken == null) {
                sessionToken = "123";
                session.setAttribute("sessionToken", sessionToken);
            }
            Hashtable data = new Hashtable();
            data.put("sessionToken", sessionToken.toString());

            return CommonResult.success(data, "登陆成功");
        } else {
            return CommonResult.validateFailed(msg);
        }
    }

    /**
     * 用户登出
     * @param session   httpSession，由mvc容器自己管理
     */
    @GetMapping("/user/base/logout")
    public CommonResult logout(HttpSession session) {
        Object sessionToken = session.getAttribute("sessionToken");
        if (sessionToken != null) {
            session.removeAttribute("sessionToken");
        }
        return CommonResult.success(null, "退出登陆成功");
    }

    /**
     * 用户注册
     * @param registerData  注册信息字典
     * @bodyParam userName          Y   ""    用户名
     * @bodyParam password          Y   ""    密码
     * @bodyParam invitationCode    Y   ""    邀请码
     */
    @PostMapping("/user/base/register")
    public CommonResult register(@RequestBody Map<String, String> registerData) {
        String msg = userBaseService.registerNewUser(
            registerData.get("userName"),
            registerData.get("password"),
            registerData.get("invitationCode")
        );
        if (msg.equals("0")) {
            return CommonResult.success("注册成功");
        } else {
            return CommonResult.validateFailed(msg);
        }
    }
}
