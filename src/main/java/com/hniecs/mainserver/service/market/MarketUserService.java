package com.hniecs.mainserver.service.market;

import com.hniecs.mainserver.entity.market.MarketUserEntity;
import com.hniecs.mainserver.model.MarketUserDetailModel;
import com.hniecs.mainserver.model.MarketUserModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 陈桢梁
 * @desc MarketUserService.java
 * @date 2021-04-04 17:17
 * @logs[0] 2021-04-04 17:17 陈桢梁 创建了MarketUserService.java文件
 */
@Service
public class MarketUserService {

    @Resource
    MarketUserDetailModel marketUserDetailModel;
    @Resource
    MarketUserModel marketUserModel;

    public String register(MarketUserEntity marketUserEntity) throws NoSuchMethodException {
        if(marketUserModel.have(marketUserEntity.getName())){
            return "用户名重复";
        }
        return marketUserModel.insert(marketUserEntity);
    }
}
