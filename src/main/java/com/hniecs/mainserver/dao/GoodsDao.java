package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.market.GoodsEntity;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * @author 陈桢梁
 * @desc $END$ GoodsDao.java
 * @date 2020-11-20 17:27
 * @logs[0] 2020-11-20 17:27 陈桢梁 创建了GoodsDao.java文件
 */
@Mapper
public interface GoodsDao {
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "fileUrl", column = "file_url"),
        @Result(property = "priceRange", column = "price_range")
    })
    @Select("select * from goods " +
                "where title like '%${condition}%' " +
                "or introduce like '%${condition}%'")
    ArrayList<GoodsEntity> getGoodEntitiesByCondition(String condition);

    @Select("select * from goods" +
                " where userId = #{userId}")
    ArrayList<GoodsEntity> getGoodsEntitiesByUserId(long userId);

    @Insert("insert into goods(user_id,price,title,introduce,file_url,ctime,mtime)" +
        "value(#{userId},#{price},#{title},#{introduce},#{fileUrl},#{ctime},#{mtime})")
    void inert(GoodsEntity goodsEntity);

    @Update("<script>" +
                "update goods set " +
                    "<if text = 'title != null'> title = #{title},</if>" +
                    "<if text = 'price != null'> price = #{price},</if>" +
                    "<if text = 'userId !=null'> user_id = #{userId},</if>" +
                    "<if text = 'ctime != null'> ctime = #{ctime},</if>" +
                    "<if text = 'fileUrl != null'>file_url=#{fileUrl},</if>" +
                    "<if text  = 'introduce != null'>introduce = #{introduce}</if>" +
                    "mtime = #{mtime}" +
                    " where id = #{id}" +
            "</script>")
    void update(GoodsEntity goodsEntity);

    @Delete("delete from goods where id = #{id}")
    void delete(long id);
}
