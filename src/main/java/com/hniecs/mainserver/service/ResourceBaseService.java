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
     * @param num       每页个数
     * @param page      资源页数
     * @return
     */
    public String getResource(ArrayList<ResourceEntity> resourceList, String kind, String condition, long num, long page) {
        long point = (page - 1) * num;
        return resourceModel.getResource(resourceList, kind, condition, num, point);
    }

    /**
     * 通过类型返回ResourceEntity数组
     * @param kind 资源种类
     */
    public String getResourceByKind(String kind, ArrayList<ResourceEntity> resourceList) {
        return resourceModel.getByKind(kind, resourceList);
    }

    /**
     * 模糊搜索
     * @param condition 模糊搜索条件
     */
    public String getByFuzzySearch(String condition, ArrayList<ResourceEntity> resourceList) {
        return resourceModel.getFuzzySearch(condition, resourceList);

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
        if (!resourceModel.haveById(id)) {
            return "资源不存在";
        }
        return resourceModel.updateResourceById(id, name, url, introduce, kind);
    }

    /**
     * 删除资源
     * @param id 资源id
     */
    public String deleteResource(long id) {
        if (!resourceModel.haveById(id)) {
            return "资源不存在";
        }
        return resourceModel.deleteResource(id);
    }

    /**
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
