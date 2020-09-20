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
    public CommonResult addResource(@RequestBody Map<String, String> resourceDate){
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
     * 设置一个resourceEntity
     * @param name 资源名
     * @param url 资源地址
     * @param kind 资源种类
     * @param introduce 资源介绍
     */
    private void setResourceEntity(String name, String url, String kind, String introduce, ResourceEntity resourceEntity){
        if(kind!=null){
            resourceEntity.setKind(kind);
        }
        if(name!=null){
            resourceEntity.setName(name);
        }
        if(url!=null){
            resourceEntity.setUrl(url);
        }
        if(introduce!=null){
            resourceEntity.setIntroduce(introduce);
        }
    }
    /***
     *
     * @param condition 搜索条件
     * @return
     */
    @NotNeedLogin
    @GetMapping("/resource/base/getResourceByFuzzy")
    public CommonResult getResourceByFuzzy(@RequestBody Map<String, String> condition){
        ArrayList<ResourceEntity> resourceList = new ArrayList<>();
        String msg=resource.getByFuzzySearch(condition.get("condition"),resourceList);
        if(msg.equals("0")){
            return CommonResult.success(resourceList,"资源查找成功");
        }
        return CommonResult.failed(msg);
    }
    @NotNeedLogin
    @GetMapping("/resource/base/getResourceByKind")
    public CommonResult getResourceByKind(@RequestBody String kind){
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
     * @badyParam name 修改后资源名字
     * @bodyParam kind 修改后资源种类
     * @bodyParam url 修改后资源url
     * @bodyParam introduce 修改后资源介绍
     * @return
     */
    @PostMapping("/resource/base/updateResource")
    public CommonResult updateResource(@RequestBody Map<String, String> resourceDate) {
        long id = Integer.parseInt(resourceDate.get("id"));
        ResourceEntity resourceEntity = resource.getResourceById(id);
        resourceEntity.setCtime(new Date());
        setResourceEntity(resourceDate.get("name"), resourceDate.get("url")
            , resourceDate.get("kind"), resourceDate.get("introduce"), resourceEntity);
        String msg = resource.updateResource(resourceEntity);
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
    public CommonResult deleteResource(@RequestParam long id){
        String result=resource.deleteResource(id);
        if(result.equals("0")){
            return CommonResult.success(null, "资源删除成功");
        }
        return CommonResult.success(result);
    }
}
