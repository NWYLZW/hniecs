package com.hniecs.mainserver.model;

import com.hniecs.mainserver.dao.MarketUserDao;
import com.hniecs.mainserver.dao.MarketUserDetailDao;
import com.hniecs.mainserver.entity.market.MarketUserDetailEntity;
import com.hniecs.mainserver.entity.market.MarketUserEntity;
import com.hniecs.mainserver.exception.SQLException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author 陈桢梁
 * @desc MarketUserModel.java
 * @date 2021-04-01 19:32
 * @logs[0] 2021-04-01 19:32 陈桢梁 创建了MarketUserModel.java文件
 */
@Repository
public class MarketUserModel {

    @Resource
    MarketUserDao marketUserDao;

    @Resource
    MarketUserDetailModel marketUserDetailModel;

    public boolean have(String username){
        return marketUserDao.getByName(username) != null;
    }

    public String insert(MarketUserEntity marketUserEntity) throws NoSuchMethodException {
        Long result = marketUserDetailModel.insert();
        try{
            marketUserEntity.setDataId(result);
            marketUserDao.insert(marketUserEntity);
            return "0";
        }catch (Exception e){
            throw new SQLException(500,"插入市场用户数据失败",
                this.getClass().getMethod("deleteWhenError", long.class),this.getClass(),result);
        }
    }

    public String deleteWhenError(long id){
        try{
            marketUserDetailModel.delete(id);
            return "0";
        }catch (Exception e){
            throw new SQLException(500,"删除市场用户信息失败",null,null,null);
        }
    }
}
