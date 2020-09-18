package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.ResourceDao;
import com.hniecs.mainserver.entity.ResourceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;

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
            return "查找资源成功";
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
          return "资源查找成功";
      }catch (Exception e){
          log.warn(e.getMessage());
          return "服务器出错";
      }
    }

    /**
     * 更新资源
     * @param resourceEntity
     * @return
     */
    public String updateResource(ResourceEntity resourceEntity){
        try{
            resourceDao.update(resourceEntity);
            return "资源更新成功";
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
            return "资源删除成功";
        }catch (Exception e){
            log.warn(e.getMessage());
            return "服务器出错";
        }
    }

    public String addNew(ResourceEntity resourceEntity) {
        try {
            resourceDao.insert(resourceEntity);
            return "插入资源成功";
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
