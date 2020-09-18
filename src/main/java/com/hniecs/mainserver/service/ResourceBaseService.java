package com.hniecs.mainserver.service;

import com.hniecs.mainserver.entity.ResourceEntity;
import com.hniecs.mainserver.model.ResourceModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

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
     * 通过类型返回ResourceEntity数组
     * @param kind
     * @return
     */
    public String getResourceByKind(String kind,ArrayList<ResourceEntity> resourceList){
        return resourceModel.getByKind(kind,resourceList);
    }

    /**
     * 模糊搜索
     * @param condition 模糊搜索条件
     * @return
     */
    public String getByFuzzySearch(String condition, ArrayList<ResourceEntity> resourceList){
        return resourceModel.getFuzzySearch(condition,resourceList);

    }

    /**
     * 根据id返回资源对象
     * @param id 资源id
     * @return
     */
    public ResourceEntity getResourceById(long id){
        ResourceEntity resource=resourceModel.getById(id);
        return resource;
    }

    /***
     * 更新对象
     * @param resourceEntity 资源对象
     * @return
     */
    public String updateResource(ResourceEntity resourceEntity){
        if(resourceModel.haveById(resourceEntity.getId())){
            return "资源不存在";
        }
        return resourceModel.updateResource(resourceEntity);
    }

    /**
     * 删除资源
     * @param id 资源id
     * @return
     */
    public String deleteResource(long id){
        if(resourceModel.haveById(id)){
            return "资源不存在";
        }
        return resourceModel.deleteResource(id);
    }

    /**
     *
     */
    public String addResource(String kind, String introduce, String name, String url){
        ResourceEntity resourceEntity=new ResourceEntity(name, url, introduce, kind);
        return resourceModel.addNew(resourceEntity);
    }
}
