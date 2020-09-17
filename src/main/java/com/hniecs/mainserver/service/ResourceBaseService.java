package com.hniecs.mainserver.service;

import com.hniecs.mainserver.dao.ResourceDao;
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
    public ArrayList<ResourceEntity> getResourceByKind(String kind){
        ArrayList<ResourceEntity> resourceList=resourceModel.getResourceEntityByKind(kind);
        return resourceList;

    }

    /**
     * 模糊搜索
     * @param condition 模糊搜索条件
     * @return
     */
    public ArrayList<ResourceEntity> getResourceByFuzzy(String condition){
        ArrayList<ResourceEntity> resourceList=resourceModel.getResourceFuzzy(condition);
        return resourceList;
    }

    /**
     * 根据id返回资源对象
     * @param id 资源id
     * @return
     */
    public ResourceEntity getResourceById(long id){
        ResourceEntity resource=resourceModel.getResourceEntityById(id);
        return resource;
    }

    /***
     * 更新对象
     * @param resourceEntity 资源对象
     * @return
     */
    public int updateResource(ResourceEntity resourceEntity){
        return resourceModel.updateResource(resourceEntity);
    }

    /**
     * 删除资源
     * @param id 资源id
     * @return
     */
    public int deleteResource(long id){
        return resourceModel.deleteResource(id);
    }
}
