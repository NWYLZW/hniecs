package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.MarketUserDetailDao;
import com.hniecs.mainserver.entity.market.MarketUserDetailEntity;
import com.hniecs.mainserver.exception.SQLException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 陈桢梁
 * @desc MarketUserDetailModel.java
 * @date 2021-03-13 16:03
 * @logs[0] 2021-03-13 16:03 陈桢梁 创建了MarketUserDetailModel.java文件
 */
@Repository
public class MarketUserDetailModel {
    @Resource
    MarketUserDetailDao marketUserDetailDao;

    public Long insert(){
        MarketUserDetailEntity mid = new MarketUserDetailEntity();
        mid.setCtime(new Date());
        return marketUserDetailDao.insert(mid);
    }

    public Long insert(MarketUserDetailEntity marketUserDetailEntity){
        return marketUserDetailDao.insert(marketUserDetailEntity);
    }

    public String delete(long DetailId){
        try{
            marketUserDetailDao.deleteById(DetailId);
            return "0";
        }catch (Exception e){
            throw new SQLException(500,"删除发生错误",null,null,null);
        }
    }
}
