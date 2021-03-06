package com.hniecs.mainserver.service;

import com.hniecs.mainserver.entity.ResourceEntity;
import com.hniecs.mainserver.model.ResourceModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * @desc     ResourceBaseService.java
 * @author  陈桢梁
 * @date    2020-09-16 17:08
 * @logs[0] 2020-09-16 17:08 陈桢梁 创建了ResourceBaseService.java文件
 */
@Service
public class ResourceBaseService {
    @Resource
    private ResourceModel resourceModel;

    /**
     * 根据kind和模糊搜索匹配资源
     * @param kind      资源种类
     * @param condition 资源条件
     */
    public String getResource(ArrayList<ResourceEntity> resourceList, String kind, String condition) {
        return resourceModel.getResource(resourceList, kind, condition);
    }

    /**
     * 根据id返回资源对象
     * @param id 资源id
     */
    public ResourceEntity getResourceById(long id) {
        ResourceEntity resource = resourceModel.getById(id);
        return resource;
    }

    /***
     * 更新对象
     * @param resourceDate 资源对象
     */
    public String updateResource(Map<String, String> resourceDate) {
        long id = Long.parseLong(resourceDate.get("id"));
        String kind = resourceDate.get("kind");
        String introduce = resourceDate.get("introduce");
        String name = resourceDate.get("name");
        String url = resourceDate.get("url");
        if (resourceModel.have(id)) {
            return "资源不存在";
        }
        return resourceModel.updateResourceById(id, name, url, introduce, kind);
    }

    /**
     * 删除资源
     * @param id 资源id
     */
    public String deleteResource(long id) {
        if (resourceModel.have(id)) {
            return "资源不存在";
        }
        return resourceModel.deleteResource(id);
    }

    /**
     * 添加资源
     * @param kind      资源种类
     * @param introduce 资源介绍
     * @param name      资源名
     * @param url       资源链接
     * @param table     获取返回值的table
     */
    public String addResource(String kind, String introduce, String name, String url, Hashtable table) {
        ResourceEntity resourceEntity = new ResourceEntity(name, url, introduce, kind);
        resourceEntity.setCtime(new Date());
        table.put("资源实体", resourceEntity);
        return resourceModel.addNew(resourceEntity);
    }
}
