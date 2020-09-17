package com.hniecs.mainserver.controller.user;

import com.hniecs.mainserver.annotation.NotNeedLogin;
import com.hniecs.mainserver.service.user.UserBaseService;
import com.hniecs.mainserver.tool.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @desc    UserBaseController.java
 * @author  yijie
 * @date    2020-09-13 00:00
 * @logs[0] 2020-09-13 00:00 yijie 创建了文件UserBaseController.java
 * @logs[1] 2020-09-13 00:00 yijie 修改了文件名
 * @logs[2] 2020-09-15 20:15 yijie 添加了登陆接口
 * @logs[3] 2020-09-16 01:17 yijie 登陆与注册接口不被拦截器拦截 预留一些TODO
 * @logs[4] 2020-09-16 22:06 yijie 预留数据校验todo，抽离与controller层无关的代码
 */
@RestController
@Slf4j
public class UserBaseController {
    /**
     * TODO czl 校验用户名格式是否正确 4-12位 字母或数字非数字开头 只能包含 '-', '_', '.', '@' 四种字符，不能在开头
     * @param userName  用户名
     * @return  用户名格式是否正确
     */
    private boolean vertifyUserName (String userName) {
        return true;
    }

    /**
     * TODO czl 校验用户名格式是否正确 6-20位 字母或数字 只能包含 '-', '_', '.', '@' 四种字符，不能在开头
     * @param password  密码
     * @return  密码格式是否正确
     */
    private boolean vertifyPassword (String password) {
        return true;
    }

    @Resource
    private UserBaseService userBaseService;

    /**
     * 用户登陆
     * @param user      用户信息
     * @param session   httpSession，由mvc容器自己管理
     * @bodyParam userName    Y   ""    用户名
     * @bodyParam password    Y   ""    密码
     */
    @NotNeedLogin
    @PostMapping("/user/base/login")
    public CommonResult login(@RequestBody Map<String, String> user, HttpSession session) {
        String
            userName = user.get("userName"),
            password = user.get("password");
        if (!vertifyUserName(userName) || !vertifyPassword(password)) {
            return CommonResult.validateFailed();
        }
        Hashtable data = new Hashtable();
        String msg = userBaseService.login(userName, password, session, data);
        if (msg.equals("0")) {
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
    @NotNeedLogin
    @PostMapping("/user/base/register")
    public CommonResult register(@RequestBody Map<String, String> registerData) {
        String
            userName = registerData.get("userName"),
            password = registerData.get("password"),
            invitationCode = registerData.get("invitationCode");
        if (!vertifyUserName(userName) || !vertifyPassword(password)) {
            return CommonResult.validateFailed();
        }
        String msg = userBaseService.registerNewUser(
            userName, password, invitationCode
        );
        if (msg.equals("0")) {
            return CommonResult.success("注册成功");
        } else {
            return CommonResult.validateFailed(msg);
        }
    }

    /**
     * TODO 通过用户的id或用户名获取用户可公开信息
     * @return 与搜索信息相关的用户列表
     */
    @GetMapping("/user/base/searchByIdOrUserName")
    public CommonResult searchByIdOrUserName() {
        return CommonResult.notFound("接口未完成");
    }

    /**
     * TODO 获取某用户某年度的活跃信息 默认我 本年度信息
     * @return 年度活跃信息
     */
    @GetMapping("/user/base/activeData")
    public CommonResult getActiveData() {
        return CommonResult.notFound("接口未完成");
    }

    /**
     * TODO 获取某用户的积分信息  默认我的
     * @return 各种积分的一个字典
     */
    @GetMapping("/user/base/integral")
    public CommonResult getIntegral() {
        return CommonResult.notFound("接口未完成");
    }

    /**
     * TODO 获取我的详细信息
     * @return 我的详情信息字典
     */
    @GetMapping("/user/base/myDetailData")
    public CommonResult getMyDetailData() {
        return CommonResult.notFound("接口未完成");
    }
}
