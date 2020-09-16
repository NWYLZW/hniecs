package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.ResourceDao;
import com.hniecs.mainserver.entity.ResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @desc     ResourceModel.java
 * @author  陈桢梁
 * @date    2020-09-14 21:05
 * @logs[0] 2020-09-14 21:05 陈桢梁 创建了ResourceModel.java文件
 */
public class ResourceModel {
    @Autowired
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
}
