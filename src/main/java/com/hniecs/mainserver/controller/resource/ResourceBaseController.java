package com.hniecs.mainserver.controller.resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hniecs.mainserver.annotation.method.NotNeedLogin;
import com.hniecs.mainserver.entity.ResourceEntity;
import com.hniecs.mainserver.service.ResourceBaseService;
import com.hniecs.mainserver.tool.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * @desc     ResourceBaseController.java
 * @author  陈桢梁
 * @date    2020-09-16 12:49
 * @logs[0] 2020-09-16 12:49 陈桢梁 创建了ResourceBaseController.java文件
 */
@RestController
@Slf4j
public class ResourceBaseController {
    @Resource
    ResourceBaseService resource;

    /**
     * 创建资源
     * @param resourceDate
     * @bodyParam kind String 资源种类
     * @bodyParam introduce 资源介绍
     * @bodyParam name 资源名
     * @bodyParam url 资源链接
     */
    @PostMapping("/resource/base/addResource")
    public CommonResult addMapping(@RequestBody Map<String, String> resourceDate) {
        Hashtable table = new Hashtable();
        String msg = resource.addResource(
            resourceDate.get("kind"),
            resourceDate.get("introduce"),
            resourceDate.get("name"),
            resourceDate.get("url"),
            table
        );
        if (msg.equals("0")) {
            return CommonResult.success(table, "资源创建成功");
        }
        return CommonResult.failed(msg);
    }
    /**
     * 搜索资源
     * @param condition String  N  ""     搜索条件
     * @param kind      String  N  ""     资源种类
     * @param size      Integer Y  "1"    每页数量
     * @param page      Integer Y  "20"   页数
     */
    @NotNeedLogin
    @GetMapping("/resource/base/get")
    public CommonResult getResourceBaseList(
        @RequestParam(name = "condition", required = false, defaultValue = "") String condition,
        @RequestParam(name = "kind", required = false, defaultValue = "") String kind,
        @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
        @RequestParam(name = "size", required = false, defaultValue = "20") Integer size){
        PageHelper.startPage(page, size);
        ArrayList<ResourceEntity> resourceList = new Page<>();
        String msg = resource.getResource(resourceList, kind, condition);
        if(msg.equals("0")){
            return CommonResult.success(resourceList, "资源查找成功");
        }
        return CommonResult.failed(msg);
    }

    /***
     * 更新资源
     * @param resourceDate
     * @bodyParam name 修改后资源名字
     * @bodyParam kind 修改后资源种类
     * @bodyParam url 修改后资源url
     * @bodyParam introduce 修改后资源介绍
     */
    @PostMapping("/resource/base/updateResource")
    public CommonResult updateMapping(@RequestBody Map<String, String> resourceDate) {
        String msg = resource.updateResource(resourceDate);
        if (msg.equals("0")) {
            return CommonResult.success(null, "资源更新成功");
        }
        return CommonResult.failed(msg);
    }

    /**
     * 删除资源
     * @param id 资源id
     */
    @GetMapping("/resource/base/deleteResource")
    public CommonResult deleteMapping(@RequestParam long id) {
        String msg = resource.deleteResource(id);
        if (msg.equals("0")) {
            return CommonResult.success(null, "资源删除成功");
        }
        return CommonResult.success(msg);
    }
}
