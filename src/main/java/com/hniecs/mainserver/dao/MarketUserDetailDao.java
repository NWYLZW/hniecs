package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.market.MarketUserDetailEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 陈桢梁
 * @desc MarketUserDetailDao.java
 * @date 2021-03-15 16:38
 * @logs[0] 2021-03-15 16:38 陈桢梁 创建了MarketUserDetailDao.java文件
 */
@Mapper
public interface MarketUserDetailDao {
    @Insert("insert into market_user_detail " +
        "value(null,#{headPortrait},#{biography},#{wechat},#{number},#{qq},#{mtime},#{ctime})")
    Long insert(MarketUserDetailEntity marketUserDetailDao);

    @Delete("delete market_user_detail where id = #{id}")
    void deleteById(long id);
}
