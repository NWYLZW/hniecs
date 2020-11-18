package com.hniecs.mainserver.controller.user;

import com.hniecs.mainserver.annotation.method.NotNeedLogin;
import com.hniecs.mainserver.exception.CommonExceptions;
import com.hniecs.mainserver.service.user.UserBaseService;
import com.hniecs.mainserver.tool.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

/**
 * @desc    UserBaseController.java
 * @author  yijie
 * @date    2020-09-13 00:00
 * @logs[0] 2020-09-13 00:00 yijie 创建了文件UserBaseController.java
 * @logs[1] 2020-09-13 00:00 yijie 修改了文件名
 * @logs[2] 2020-09-15 20:15 yijie 添加了登陆接口
 * @logs[3] 2020-09-16 01:17 yijie 登陆与注册接口不被拦截器拦截 预留一些TODO
 * @logs[4] 2020-09-16 22:06 yijie 预留数据校验todo，抽离与controller层无关的代码
 * @logs[5] 2020-09-25 04:08 yijie 删除无用的方法
 * @logs[6] 2020-11-18 12:56 yijie 重构代码
 */
@RestController
@Slf4j
public class UserBaseController {
    @Resource
    private UserBaseService userBaseService;

    /**
     * 校验用户名格式是否正确 字母|中文|数字|-|_|.|@ (不能以 数字，特殊字符，中文 开头) 4-12位
     * @param userName  用户名
     * @return  用户名格式是否正确
     */
    private boolean verifyUserName(String userName) {
        int min = 3;
        int max = 12;
        String specialCharPattern = "-,_,.,@";
        String zhPattern = "\\u4E00-\\u9FA5";
        String letterPattern = "a-z,A-Z";
        String pattern = "^[" + letterPattern + ']' + '[' + zhPattern + ',' + letterPattern + ",\\d," + specialCharPattern + "]{" + min + ',' + max + '}';

        return Pattern.matches(pattern, userName);
    }

    /**
     * 校验密码格式是否正确 字母|数字|-|_|.|@ 5-20位
     * @param password  密码
     * @return  密码格式是否正确
     */
    private boolean verifyPassword(String password) {
        int min = 5;
        int max = 20;
        String specialCharPattern = "-,_,.,@";
        String letterPattern = "a-z,A-Z";
        String pattern = "^[" + letterPattern + ",\\d," + specialCharPattern + "]{" + min + "," + max + "}";

        return Pattern.matches(pattern, password);
    }

    /**
     * 用户登陆
     * @param userName    Y   ""    用户名
     * @param password    Y   ""    密码
     */
    @NotNeedLogin
    @PostMapping("/user/base/login")
    public CommonResult login(
        @RequestParam(name = "userName", required = true) String userName,
        @RequestParam(name = "password", required = true) String password
    ) {
        if (userName.equals("") || password.equals("")) {
            throw CommonExceptions.BAD_REQUEST.exception;
        }
        // 密码已加密过一遍，不需要校验
        if (!verifyUserName(userName)) {
            throw CommonExceptions.BAD_REQUEST.exception;
        }
        return CommonResult.success(
            userBaseService.login(userName, password), "登陆成功"
        );
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
     * @param userName          Y   ""  用户名     字母|中文|数字|-|_|.|@ (不能以 数字，特殊字符，中文 开头) 4-12位
     * @param password          Y   ""  密码      字母|数字|-|_|.|@ 5-20位
     * @param realName          Y   ""  真实姓名   中文 2-5位
     * @param schoolNum         Y   ""  学号      20xxxxxxxxxx 12位
     * @param profession        Y   ""  专业名    中文 20位
     * @param classNum          Y   ""  班级      数字 4位
     * @param qqNum             Y   ""  qq号码    数字 6-16位
     * @param telNum            N   ""  电话号码   数字 11位
     * @param invitationCode    Y   ""  邀请码    不含空格 50
     */
    @NotNeedLogin
    @PostMapping("/user/base/registered")
    public CommonResult<Object> register(
        @RequestParam(name = "userName",       required = true) String userName,
        @RequestParam(name = "password",       required = true) String password,
        @RequestParam(name = "realName",       required = true) String realName,
        @RequestParam(name = "schoolNum",      required = true) String schoolNum,
        @RequestParam(name = "profession",     required = true) String profession,
        @RequestParam(name = "classNum",       required = true) String classNum,
        @RequestParam(name = "qqNum",          required = true) String qqNum,
        @RequestParam(name = "telNum",         required = true) String telNum,
        @RequestParam(name = "invitationCode", required = true) String invitationCode
    ) {
        if (
            !verifyUserName(userName)
                || !verifyPassword(password)
                || !Pattern.matches("^[\u4E00-\u9FA5]{2,5}$", realName)
                || !Pattern.matches("^20[0-9]{10}$", schoolNum)
                || !Pattern.matches("^[\u4E00-\u9FA5]{2,20}$", profession)
                || !Pattern.matches("^[0-9]{4}$", classNum)
                || !Pattern.matches("^[0-9]{6,16}$", qqNum)
                || !Pattern.matches("^1[3-9]\\d{9}$", telNum)
                || !Pattern.matches("^\\S{1,50}$", invitationCode)
        ) {
            throw CommonExceptions.BAD_REQUEST.exception;
        }
        userBaseService.registerNewUser(
            userName, password
            , realName, schoolNum
            , profession, classNum
            , qqNum, telNum
            , invitationCode
        );
        return CommonResult.success();
    }

    /**
     * TODO 通过用户的id或用户名获取用户可公开信息
     * @return 与搜索信息相关的用户列表
     */
    @GetMapping("/user/base/searchByIdOrUserName")
    public CommonResult searchByIdOrUserName() {
        throw CommonExceptions.NOT_IMPLEMENTED.exception;
    }

    /**
     * TODO 获取某用户某年度的活跃信息 默认我 本年度信息
     * @return 年度活跃信息
     */
    @GetMapping("/user/base/activeData")
    public CommonResult getActiveData() {
        throw CommonExceptions.NOT_IMPLEMENTED.exception;
    }

    /**
     * TODO 获取某用户的积分信息  默认我的
     * @return 各种积分的一个字典
     */
    @GetMapping("/user/base/integral")
    public CommonResult getIntegral() {
        throw CommonExceptions.NOT_IMPLEMENTED.exception;
    }

    /**
     * TODO 获取我的详细信息
     * @return 我的详情信息字典
     */
    @GetMapping("/user/base/myDetailData")
    public CommonResult getMyDetailData() {
        throw CommonExceptions.NOT_IMPLEMENTED.exception;
    }
}
