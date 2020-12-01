package com.hniecs.mainserver.service.market;

import com.hniecs.mainserver.entity.market.GoodsEntity;
import com.hniecs.mainserver.exception.CommonExceptions;
import com.hniecs.mainserver.model.GoodsModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author 陈桢梁
 * @desc GoodsBaseService.java
 * @date 2020-11-21 18:00
 * @logs[0] 2020-11-21 18:00 陈桢梁 创建了GoodsBaseService.java文件
 */
@Service
public class GoodsBaseService {
    @Resource
    GoodsModel goodsModel;
    public String upload(GoodsEntity goodsEntity){
        return goodsModel.Insert(goodsEntity);
    }

    /**
     * 根据条件获取entity
     * @param condition 条件
     * @param entities 装载entity的数组
     * @param <T> 条件类型
     * @return
     */
    public <T extends Object> String get(T condition, ArrayList<GoodsEntity> entities){
        if(condition.getClass().getTypeName().equals("java.lang.String")){
            return goodsModel.getByCondition(condition.toString(),entities);
        }
        if(condition.getClass().getTypeName().equals("java.lang.Long")){
            return goodsModel.getById((long)condition,entities);
        }
        throw CommonExceptions.BAD_REQUEST.exception;
    }

    /**
     * 获取全部的goodsEntity
     */
    public String get(ArrayList<GoodsEntity> entities){
        return goodsModel.getAll(entities);
    }

    /**
     * 获取goodEntity通过价格区间
     */
    public String get(int max, int min, ArrayList<GoodsEntity> goodsEntities){
        return goodsModel.getByPriceRange(max,min, goodsEntities);
    }
    /**
     * 透过用户id获取商品对象
     */
    public String getByUserId(long id,ArrayList<GoodsEntity> goodsEntities){
        return goodsModel.getByUserId(id, goodsEntities);
    }
}
