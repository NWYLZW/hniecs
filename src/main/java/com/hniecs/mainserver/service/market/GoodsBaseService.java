package com.hniecs.mainserver.service.market;

import com.hniecs.mainserver.entity.market.GoodsEntity;
import com.hniecs.mainserver.model.GoodsModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
