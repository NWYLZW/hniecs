package com.hniecs.mainserver.controller.user;

import com.hniecs.mainserver.annotation.method.PermissionRequired;
import com.hniecs.mainserver.entity.permission.AdminPermissions;
import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;

/**
 * @desc    用户权限controller层 UserRuleController.java
 * @author  yijie
 * @date    2020-09-15 20:19
 * @logs[0] 2020-09-15 20:19 yijie 创建了UserRuleController.java文件
 * @logs[1] 2020-09-16 01:30 yijie 预留权限接口待完成
 */
@RestController
public class UserRuleController {
    /**------------------超级管理员------------------**/
    /**
     * TODO 获取已有的权限列表
     */
    @PermissionRequired(
        scope = AdminPermissions.NAME,
        permission = AdminPermissions.SEARCH_ALL_USER
    )
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
        return CommonResult.success(new ArrayList<>(
            Arrays.asList(
                new HashMap<String, Object>() {{
                    put("name", "后台管理");
                    put("iconUTF8", "&#xe76e;");
                    put("url", "/admin/manage/user");
                }},
                new HashMap<String, Object>() {{
                    put("name", "我的消息");
                    put("iconUTF8", "&#xe6a2;");
                    put("url", "/message/my/index");
                    put("menus", new ArrayList<>(
                        Arrays.asList(
                            new HashMap<String, Object>() {{
                                put("name", "公告");
                                put("iconUTF8", "&#xe6aa;");
                                put("url", "/admin/announcement/index");
                            }},
                            new HashMap<String, Object>() {{
                                put("name", "聊天");
                                put("iconUTF8", "&#xe6a9;");
                                put("url", "/admin/chat/index");
                            }},
                            new HashMap<String, Object>() {{
                                put("name", "看板");
                                put("iconUTF8", "&#xe6ab;");
                                put("url", "/admin/board/index");
                            }}
                        )
                    ));
                }}
            )
        ));
    }
}
