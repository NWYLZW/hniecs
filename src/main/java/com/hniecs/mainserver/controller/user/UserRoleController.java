package com.hniecs.mainserver.controller.user;

import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc    用户权限controller层 UserRoleController.java
 * @author  yijie
 * @date    2020-09-15 20:19
 * @logs[0] 2020-09-15 20:19 yijie 创建了UserRoleController.java文件
 * @logs[1] 2020-09-16 01:30 yijie 预留权限接口待完成
 */
@RestController
public class UserRoleController {
    /**------------------超级管理员------------------**/
    /**
     * TODO 获取已有的权限列表
     */
    @GetMapping("/user/Role/getRoles")
    public CommonResult getRoles() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 获取已有的权限组列表
     */
    @GetMapping("/user/Role/getRolers")
    public CommonResult getRolers() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 新增权限组
     */
    @GetMapping("/user/Role/addRoler")
    public CommonResult addRoler() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 重命名某个权限组
     */
    @GetMapping("/user/Role/renameRoler")
    public CommonResult renameRoler() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 将某个权限添加到某个权限组
     */
    @GetMapping("/user/Role/addRoleToRoler")
    public CommonResult addRoleToRoler() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 设置某个用户为某个权限组
     */
    @GetMapping("/user/Role/setRoler")
    public CommonResult setRoler() {
        return CommonResult.notFound("接口未完成");
    }
    /**------------------超级管理员------------------**/
}
