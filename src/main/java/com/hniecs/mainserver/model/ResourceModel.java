package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.ResourceDao;
import com.hniecs.mainserver.entity.ResourceEntity;
import com.hniecs.mainserver.tool.CommonUseStrings;
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

    /**
     * 根据kind和模糊搜索匹配资源
     * @param resourceList 赋值的列表
     * @param kind         资源种类
     * @param condition    模糊搜索条件
     */
    public String getResource(ArrayList<ResourceEntity> resourceList, String kind, String condition) {
        try {
            if(kind == null||kind.equals("")){
                kind = "%";
            }else{
                kind="%"+kind+"%";
            }
            if(condition == null||condition.equals("")){
                condition = "%";
            }else {
                condition ="%"+condition+"%";
            }
            ArrayList<ResourceEntity> temp = resourceDao.getResourceByCondition(kind, condition);
            resourceList.addAll(temp);
            return "0";
        } catch (Exception e) {
            log.error(e.getMessage());
            return CommonUseStrings.SERVER_FAILED.S;
        }
    }



    /**
     * 更新资源
     * @param id        资源id
     * @param name      资源名
     * @param url       资源百度网盘链接
     * @param introduce 资源介绍
     * @param kind      资源种类
     */
    public String updateResourceById(long id, String name, String url, String introduce, String kind) {
        try {
            ResourceEntity resource = new ResourceEntity(name, url, introduce, kind);
            resource.setMtime(new Date());
            resource.setId(id);
            resourceDao.update(resource);
            return "0";
        } catch (Exception e) {
            log.error(e.getMessage());
            return CommonUseStrings.SERVER_FAILED.S;
        }

    }

    /**
     * 根据id返回资源
     * @param id 资源id
     */
    public ResourceEntity getById(long id) {
        try {
            return resourceDao.getResourceById(id);
        } catch (Exception e) {
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
        } catch (Exception e) {
            log.error(e.getMessage());
            return CommonUseStrings.SERVER_FAILED.S;
        }
    }

    /**
     * 添加一个资源
     * @param resourceEntity 资源实体
     */
    public String addNew(ResourceEntity resourceEntity) {
        try {
            resourceDao.insert(resourceEntity);
            return "0";
        } catch (Exception e) {
            log.error(e.getMessage());
            return CommonUseStrings.SERVER_FAILED.S;
        }
    }

    /**
     * 通过id判断资源是否存在
     * @param id 资源id
     * @return 资源是否存在
     */
    public boolean have(long id) {
        return resourceDao.getResourceById(id) == null;
    }

    /**
     * 通过name判断资源是否存在
     * @param name 资源名
     */
    public boolean have(String name){return resourceDao.getResourceByName(name) == null;}
}
