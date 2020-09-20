package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.ResourceDao;
import com.hniecs.mainserver.entity.ResourceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.hniecs.mainserver.tool.ObjectTool;
/**
 * @desc     ResourceModel.java
 * @author  陈桢梁
 * @date    2020-09-14 21:05
 * @logs[0] 2020-09-14 21:05 陈桢梁 创建了ResourceModel.java文件
 */
@Repository
@Slf4j
public class ResourceModel {
    @Resource
    ResourceDao resourceDao;

    /***
     * 模糊搜索资源
     * @param condition 资源可能的名字，介绍，种类
     * @return 所有名字，介绍，种类中带有condition的ResourceEntity
     */
    public String getFuzzySearch(String condition, ArrayList<ResourceEntity> resourceList) {
        try {
            for (ResourceEntity x : resourceDao.getResourceByFuzzy(condition)) {
                resourceList.add(x);
            }
            return "0";
        }catch (Exception e){
            log.warn(e.getMessage());
            return "服务器出错";
        }
    }

    /**
     * 按资源种类返回资源
     * @param kind 资源种类名当为null时返回所有资源
     * @return
     */
    public String getByKind(String kind, ArrayList<ResourceEntity> resourceList){
      try{
          for (ResourceEntity x:resourceDao.getResourceByKind(kind)) {
              resourceList.add(x);
          }
          return "0";
      }catch (Exception e){
          log.warn(e.getMessage());
          return "服务器出错";
      }
    }

    /**
     * 更新资源
     * @param resourceMap resource数据
     * @return
     */
    public String updateResource(Map<String, String> resourceMap,long id){
        String name=resourceMap.get("name");
        String url=resourceMap.get("url");
        String kind=resourceMap.get("kind");
        String introduce=resourceMap.get("introduce");
        try{
            ResourceEntity resource = new ResourceEntity(name,url,introduce,kind);
            ResourceEntity resourceEntity = resourceDao.getResourceById(id);
            resource.setMtime(new Date());
            resource.setCtime(resourceEntity.getCtime());
            ResourceEntity temp = ObjectTool.combineEntity(resourceEntity,resource);
            resourceDao.update(temp);
            return "0";
        }catch (Exception e){
            log.warn(e.getMessage());
            return "服务器出错";
        }

    }

    /***
     * 根据id返回资源
     * @param id
     * @return
     */
    public ResourceEntity getById(long id){
        ResourceEntity resource=resourceDao.getResourceById(id);
        return resource;
    }

    /**
     * 删除资源
     * @param id
     * @return
     */
    public String deleteResource(long id) {
        try {
            resourceDao.delete(id);
            return "0";
        }catch (Exception e){
            log.warn(e.getMessage());
            return "服务器出错";
        }
    }

    public String addNew(ResourceEntity resourceEntity) {
        try {
            resourceDao.insert(resourceEntity);
            return "0";
        }catch (Exception e){
            return "服务器出错";
        }
    }

    /**
     * 通过id判断资源是否存在
     * @param id 资源id
     * @return 资源是否存在
     */
    public boolean haveById(long id){
        if(resourceDao.getResourceById(id)==null)
        {
            return false;
        }
        return true;
    }

}
