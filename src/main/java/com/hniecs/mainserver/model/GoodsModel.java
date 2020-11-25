package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.GoodsDao;
import com.hniecs.mainserver.entity.market.GoodsEntity;
import com.hniecs.mainserver.exception.CommonExceptions;
import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author 陈桢梁
 * @desc GoodsModel.java
 * @date 2020-11-21 18:03
 * @logs[0] 2020-11-21 18:03 陈桢梁 创建了GoodsModel.java文件
 */
@Repository
public class GoodsModel {
    @Resource
    GoodsDao goodsDao;

    public String Insert(GoodsEntity goodsEntity){
        try {
            goodsEntity.setCtime(new Date());
            goodsDao.inert(goodsEntity);
            return "0";
        }catch (Exception e){
            throw new RuntimeException("0");
        }
    }

    public String getById(long id, ArrayList<GoodsEntity> goodsEntities) {
        try {
            goodsEntities.add(goodsDao.getById(id));
            return "0";
        }catch (Exception e){
            throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
        }
    }
    public String getByUserId(long id, ArrayList<GoodsEntity> goodsEntities){
        try{
            goodsEntities.addAll(goodsDao.getGoodsEntitiesByUserId(id));
            return "0";
        }catch (Exception e){
            throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
        }
    }
    public String getByCondition(String condition, ArrayList<GoodsEntity> goodsEntities) {
        try {
            goodsEntities.addAll(goodsDao.getGoodEntitiesByCondition(condition));
            return "0";
        }catch (Exception e){
            throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
        }
    }

    public String getByPriceRange(int max, int min, ArrayList<GoodsEntity> goodsEntities){
        try{
            goodsEntities.addAll(goodsDao.getGoodsEntitiesByPriceRange(max, min));
            return "0";
        }catch (Exception e){
            throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
        }
    }
    public String getAll(ArrayList<GoodsEntity> entities){
        try{
            entities.addAll(goodsDao.getAll());
            return "0";
        }catch (Exception e){
            throw new RuntimeException("[500]{服务器出错}");
        }
    }

    public boolean have(long id){
        return goodsDao.getById(id) !=null;
    }

}
