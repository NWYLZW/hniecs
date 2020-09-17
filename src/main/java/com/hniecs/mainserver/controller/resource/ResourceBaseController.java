package com.hniecs.mainserver.controller.resource;

import com.hniecs.mainserver.annotation.NotNeedLogin;
import com.hniecs.mainserver.dao.ResourceDao;
import com.hniecs.mainserver.entity.ResourceEntity;
import com.hniecs.mainserver.service.ResourceBaseService;
import com.hniecs.mainserver.tool.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.naming.Name;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
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

    /***
     * 设置一个resourceEntity
     * @param name 资源名
     * @param url 资源地址
     * @param kind 资源种类
     * @param introduce 资源介绍
     */
    private void setResourceEntity(String name,String url,String kind,String introduce,ResourceEntity resourceEntity){
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
     * @param session
     * @return
     */
    @NotNeedLogin
    @GetMapping("/resource/base/getResourceByFuzzy")
    public CommonResult getResourceByFuzzy(@RequestBody Map<String,String> condition, HttpSession session){
        ArrayList<ResourceEntity> resourceList = resource.getResourceByFuzzy(condition.get("condition"));
        if(resourceList==null&&resourceList.size()==0){
            return CommonResult.success(null,"未找到资源");
        }
        return CommonResult.success(resourceList,"成功找到资源");
    }
    @NotNeedLogin
    @GetMapping("/resource/base/getResourceByKind")
    public CommonResult getResourceByKind(@RequestBody String kind){
        ArrayList<ResourceEntity> resourceList = resource.getResourceByKind(kind);
        if(resourceList==null&&resourceList.size()==0){
            return CommonResult.success(null,"未找到资源");
        }
        return CommonResult.success(resourceList,"成功找到资源");
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
    public CommonResult updateResource(@RequestBody Map<String,String> resourceDate) {
        long id = Integer.parseInt(resourceDate.get("id"));
        ResourceEntity resourceEntity = resource.getResourceById(id);
        resourceEntity.setCtime(new Date());
        setResourceEntity(resourceDate.get("name"), resourceDate.get("url")
            , resourceDate.get("kind"), resourceDate.get("introduce"), resourceEntity);
        int msg = resource.updateResource(resourceEntity);
        if(msg==200){
            return CommonResult.success(null,"资源更新成功");
        }else{
            return CommonResult.failed("资源更新失败");
        }
    }
    @GetMapping("/resource/base/deleteResource")
    public CommonResult deleteResource(@RequestParam long id){
        int result=resource.deleteResource(id);
        if(result==200){
            return CommonResult.success(null,"资源删除成功");
        }else {
            return CommonResult.failed("资源删除失败");
        }
    }
}
