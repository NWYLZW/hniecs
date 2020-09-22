package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.ResourceEntity;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * @author 陈桢梁
 * @desc ResourceDao.java
 * @date 2020-09-14 20:14
 * @logs[0] 2020-09-14 20:14 陈桢梁 创建了ResourceDao.java文件
 */
@Mapper
public interface ResourceDao {
    /**
     * 根据kind和模糊搜索匹配资源
     * @param kind      资源种类
     * @param condition 资源条件
     * @param num       每页个数
     * @param point     资源起始
     * @return
     */
    @Select("<script>" +
        "select * " +
        "from resource " +
        "where <if test='kind != null'>kind = #{kind} and</if>" +
        "<if test = 'condition != null'> name like \"%${condition}%\" or introduce like \"%${condition}%\"</if>" +
        "limit #{num} offset #{point}" +
        "</script>")
    public ArrayList<ResourceEntity> getResourceByCondition(@Param("kind") String kind, @Param("condition") String condition,
                                                            @Param("num") long num, @Param("point") long point);

    /**
     * 通过资源id查找ResourceEntity
     * @param id 资源id
     * @return
     */
    @Select("select * " +
        "from resource " +
        "where id = #{id}")
    public ResourceEntity getResourceById(long id);

    /**
     * 获取所有ResourceEntity
     * @return
     */
    @Select("select * " +
        "from resource order by id " +
        "limit #{limit} offset #{point}")
    public ArrayList<ResourceEntity> getAllResource(long limit, long point);

    /***
     * 通过资源名字查找资源ResourceEntity
     * @param name 资源名字
     * @return
     */
    @Select("select * " +
        "from resource " +
        "where name=#{name}")
    public ResourceEntity getResourceByName(String name);

    /***
     * 通过资源种类搜索ResourceEntity
     * @param kind 资源种类
     * @return
     */
    @Select("select * " +
        "from resource " +
        "where kind = #{kind}")
    public ArrayList<ResourceEntity> getResourceByKind(String kind);

    /***
     * 模糊搜索
     * @param condition 搜索条件
     * @return
     */
    @Select("select distinct * " +
        "from resource " +
        "where kind like \"%${condition}%\" or name like \"%${condition}%\" or introduce like \"%${condition}%\"")
    public ArrayList<ResourceEntity> getResourceByFuzzy(String condition);

    /***
     * 插入资源对象
     * @param resourceEntity 插入的对象
     */
    @Insert("insert into " +
        "resource(name,kind,introduce,url,mtime,ctime) " +
        "value(#{name},#{kind},#{introduce},#{url},#{mtime},#{ctime})")
    public void insert(ResourceEntity resourceEntity);

    /***
     * 通过id更新ResourceEntity
     * @param resourceEntity 要更新完的对象
     */
    @Update("<script>" +
        "update resource set" +
        "<if test = 'name != null'>name=#{name},</if>" +
        "<if test = 'kind != null'>kind=#{kind},</if>" +
        "<if test = 'url != null'>url=#{url},</if>" +
        "<if test = 'introduce = null'>introduce=#{introduce},</if>" +
        "<if test = 'mtime != null'>mtime=#{mtime},</if>" +
        "<if test = 'ctime != null'>ctime=#{ctime},</if>" +
        "id = #{id} where id =#{id}" +
        "</script>")
    public void update(ResourceEntity resourceEntity);

    /***
     * 通过用户id删除ResourceEntity
     * @param id 资源id
     */
    @Delete("delete " +
        "from resource " +
        "where id=#{id}")
    public void delete(long id);
}
