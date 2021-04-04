package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.market.MarketUserEntity;
import org.apache.ibatis.annotations.*;

/**
 * @author 陈桢梁
 * @desc MarketUserDao.java
 * @date 2021-03-13 16:07
 * @logs[0] 2021-03-13 16:07 陈桢梁 创建了MarketUserDao.java文件
 */
@Mapper
public interface MarketUserDao {

    @Insert("insert into market_user " +
                "value(null,#{name},#{password},#{dataId},#{hncsId},#{mtime},#{ctime})")
    int insert(MarketUserEntity marketUserEntity);

    @Results(id = "marketDao",value = {
        @Result(property = "dataId",column = "data_id"),
        @Result(property = "hncsId",column = "hncs_id")
    })
    @Select("select * from market_user where id = #{id}")
    MarketUserEntity getById(Long id);

    @ResultMap(value = "markDao")
    @Select("select * from market_user where name = #{name}")
    MarketUserEntity getByName(String name);
}
