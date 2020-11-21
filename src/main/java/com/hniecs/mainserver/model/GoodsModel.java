package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.GoodsDao;
import com.hniecs.mainserver.entity.market.GoodsEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
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
}
