package com.hniecs.mainserver.controller.user;

import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.function.Predicate;

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

    /**
     * TODO 获取某个的权限组下的应用
     */
    @GetMapping("/user/Role/getAppsByRoler")
    public CommonResult getAppsByRoler() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 给某个权限组添加应用
     */
    @GetMapping("/user/Role/addAppToRoler")
    public CommonResult addAppToRoler() {
        return CommonResult.notFound("接口未完成");
    }
    /**------------------超级管理员------------------**/

    /**
     * TODO 获取自己的权限组下的应用
     */
    @GetMapping("/user/Role/getApps")
    public CommonResult getApps() {
//        ArrayList apps = new ArrayList();
//        Predicate<HashMap> p = (String name, String iconUTF8) -> {
//            HashMap<String, String> h = new HashMap<>();
//            h.put("name", name);
//            h.put("iconUTF8", iconUTF8);
//            return h;
//        };
        return CommonResult.notFound("接口未完成");
    }
}
