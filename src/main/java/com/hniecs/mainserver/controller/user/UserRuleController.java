package com.hniecs.mainserver.controller.user;

import com.hniecs.mainserver.annotation.method.PermissionRequired;
import com.hniecs.mainserver.entity.AppEntity;
import com.hniecs.mainserver.entity.permission.AdminPermissions;
import com.hniecs.mainserver.service.user.UserRuleService;
import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @desc    用户权限controller层 UserRuleController.java
 * @author  yijie
 * @date    2020-09-15 20:19
 * @logs[0] 2020-09-15 20:19 yijie 创建了UserRuleController.java文件
 * @logs[1] 2020-09-16 01:30 yijie 预留权限接口待完成
 */
@RestController
public class UserRuleController {
    @Resource
    UserRuleService userRuleService;
    /**------------------超级管理员------------------**/
    /**
     * TODO 获取已有的权限列表
     */
    @GetMapping("/user/rule/getRules")
    public CommonResult getRules() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 获取已有的权限组列表
     */
    @GetMapping("/user/rule/getRulers")
    public CommonResult getRulers() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 新增权限组
     */
    @GetMapping("/user/rule/addRuler")
    public CommonResult addRuler() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 重命名某个权限组
     */
    @GetMapping("/user/rule/renameRuler")
    public CommonResult renameRuler() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 将某个权限添加到某个权限组
     */
    @GetMapping("/user/rule/addRuleToRuler")
    public CommonResult addRuleToRuler() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 设置某个用户为某个权限组
     */
    @GetMapping("/user/rule/setRuler")
    public CommonResult setRuler() {
        return CommonResult.notFound("接口未完成");
    }

    /**
     * TODO 获取某个的权限组下的应用
     */
    @GetMapping("/user/rule/getAppsByRuler")
    public CommonResult getAppsByRuler() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 给某个权限组添加应用
     */
    @GetMapping("/user/rule/addAppToRuler")
    public CommonResult addAppToRuler() {
        return CommonResult.notFound("接口未完成");
    }
    /**------------------超级管理员------------------**/

    /**
     * TODO 获取自己的权限组下的应用
     */
    @GetMapping("/user/rule/getApps")
    public CommonResult getApps() {
        ArrayList<AppEntity> apps = new ArrayList<>();
        String message = userRuleService.getApps(apps);
        if (message.equals("0")) {
            return CommonResult.success(apps);
        } else {
            return CommonResult.failed(message);
        }
    }
}
