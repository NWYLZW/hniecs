package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.ResourceDao;
import com.hniecs.mainserver.entity.ResourceEntity;
import com.hniecs.mainserver.tool.ObjectTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
            resourceList.addAll(resourceDao.getResourceByFuzzy(condition));
            return "0";
        }catch (Exception e){
            log.error(e.getMessage());
            return "服务器出错";
        }
    }

    /**
     * 按资源种类返回资源
     * @param kind 资源种类名当为null时返回所有资源
     */
    public String getByKind(String kind, ArrayList<ResourceEntity> resourceList){
      try{
          resourceList.addAll(resourceDao.getResourceByKind(kind));
          return "0";
      }catch (Exception e){
          log.error(e.getMessage());
          return "服务器错误";
      }
    }

    /**
     * 更新资源
     * @param id 资源id
     * @param name 资源名
     * @param url 资源百度网盘链接
     * @param introduce 资源介绍
     * @param kind 资源种类
     */
    public String updateResourceById(long id, String name, String url, String introduce, String kind){
        try{
            ResourceEntity resource = new ResourceEntity(name,url,introduce,kind);
            ResourceEntity resourceEntity = resourceDao.getResourceById(id);
            resource.setMtime(new Date());
            resource.setCtime(resourceEntity.getCtime());
            ResourceEntity temp = ObjectTool.combineEntity(resourceEntity,resource);
            resourceDao.update(temp);
            return "0";
        }catch (Exception e){
            log.error(e.getMessage());
            return "服务器错误";
        }

    }

    /***
     * 根据id返回资源
     * @param id 资源id
     */
    public ResourceEntity getById(long id) {
        try {
            return resourceDao.getResourceById(id);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
    /**
     * 删除资源
     * @param id 资源id
     */
    public String deleteResource(long id) {
        try {
            resourceDao.delete(id);
            return "0";
        }catch (Exception e){
            log.error(e.getMessage());
            return "服务器错误";
        }
    }

    /**
     *
     * @param resourceEntity 资源实体
     */
    public String addNew(ResourceEntity resourceEntity) {
        try {
            resourceDao.insert(resourceEntity);
            return "0";
        }catch (Exception e){
            log.error(e.getMessage());
            return "服务器错误";
        }
    }

    /**
     * 通过id判断资源是否存在
     * @param id 资源id
     * @return 资源是否存在
     */
    public boolean haveById(long id){
        return resourceDao.getResourceById(id) != null;
    }

}
