package com.hniecs.mainserver.controller.resource;

import com.hniecs.mainserver.annotation.NotNeedLogin;
import com.hniecs.mainserver.entity.ResourceEntity;
import com.hniecs.mainserver.service.ResourceBaseService;
import com.hniecs.mainserver.tool.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
     *创建资源
     * @param resourceDate
     * @bodyParm kind 资源种类
     * @bodyParm introduce 资源介绍
     * @bodyParm name 资源名
     * @bodyParm url 资源链接
     * @return
     */
    @PostMapping("/resource/base/addResource")
    public CommonResult addMapping(@RequestBody Map<String, String> resourceDate){
        String msg=resource.addResource(
            resourceDate.get("kind"),
            resourceDate.get("introduce"),
            resourceDate.get("name"),
            resourceDate.get("url")
        );
        if(msg.equals("0")){
            return CommonResult.success(null,"资源查找成功");
        }
        return CommonResult.failed(msg);
    }

    /***
     *
     * @param condition 搜索条件
     * @return
     */
    @NotNeedLogin
    @GetMapping("/resource/base/getResourceByFuzzy")
    public CommonResult getByFuzzy(@RequestBody Map<String, String> condition){
        ArrayList<ResourceEntity> resourceList = new ArrayList<>();
        String msg=resource.getByFuzzySearch(condition.get("condition"),resourceList);
        if(msg.equals("0")){
            return CommonResult.success(resourceList,"资源查找成功");
        }
        return CommonResult.failed(msg);
    }

    /**
     *
     * @param kind 资源种类
     * @return
     */
    @NotNeedLogin
    @GetMapping("/resource/base/getResourceByKind")
    public CommonResult getByKind(@RequestBody String kind){
        ArrayList<ResourceEntity> resourceList = new ArrayList<>();
        String msg=resource.getResourceByKind(kind,resourceList);
        if(msg.equals("0")){
            return CommonResult.success(resourceList,"资源查找成功");
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
     * @return
     */
    @PostMapping("/resource/base/updateResource")
    public CommonResult updateMapping(@RequestBody Map<String, String> resourceDate) {
        String msg = resource.updateResource(resourceDate);
        if(msg.equals("0")){
            return CommonResult.success(null, "资源更新成功");
        }
        return CommonResult.failed(msg);
    }

    /**
     * 删除资源
     * @param id 资源id
     * @return
     */
    @GetMapping("/resource/base/deleteResource")
    public CommonResult deleteMapping(@RequestParam long id){
        String msg =resource.deleteResource(id);
        if(msg.equals("0")){
            return CommonResult.success(null, "资源删除成功");
        }
        return CommonResult.success(msg);
    }
}
