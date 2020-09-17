package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.ResourceDao;
import com.hniecs.mainserver.entity.ResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @desc     ResourceModel.java
 * @author  陈桢梁
 * @date    2020-09-14 21:05
 * @logs[0] 2020-09-14 21:05 陈桢梁 创建了ResourceModel.java文件
 */
@Repository
public class ResourceModel {
    @Resource
    ResourceDao resourceDao;

    /***
     * 模糊搜索资源
     * @param condition 资源可能的名字，介绍，种类
     * @return 所有名字，介绍，种类中带有condition的ResourceEntity
     */
    public ArrayList<ResourceEntity> getResourceFuzzy(String condition){
        ArrayList<ResourceEntity> list=resourceDao.getResourceByFuzzy(condition);
        return list;
    }

    /**
     * 按资源种类返回资源
     * @param kind 资源种类名当为null时返回所有资源
     * @return
     */
    public ArrayList<ResourceEntity> getResourceEntityByKind(String kind){
        ArrayList<ResourceEntity> listOfResource;
        if(kind==null){
            listOfResource=resourceDao.getAllResource();
        }else {
            listOfResource = resourceDao.getResourceByKind(kind);
        }
        return listOfResource;
    }

    /**
     * 更新资源
     * @param resourceEntity
     * @return
     */
    public int updateResource(ResourceEntity resourceEntity){
        try{
            if(resourceDao.getResourceById(resourceEntity.getId())==null){
                return 201;
            }
            resourceDao.update(resourceEntity);
            return 200;
        }catch (Exception e){
            e.printStackTrace();
            return 201;
        }

    }

    /***
     * 根据id返回资源
     * @param id
     * @return
     */
    public ResourceEntity getResourceEntityById(long id){
        ResourceEntity resource=resourceDao.getResourceById(id);
        return resource;
    }

    /**
     * 删除资源
     * @param id
     * @return
     */
    public int deleteResource(long id) {
        try {
            if(resourceDao.getResourceById(id)==null){
                return 201;
            }
            resourceDao.delete(id);
            return 200;
        }catch (Exception e){
            return 201;
        }
    }
}
