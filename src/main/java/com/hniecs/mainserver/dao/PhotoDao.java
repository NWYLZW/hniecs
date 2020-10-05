package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.PhotoEntity;
import org.apache.ibatis.annotations.*;

/**
 * @author 陈桢梁
 * @desc $END$ PhotoDao.java
 * @date 2020-09-26 14:05
 * @logs[0] 2020-09-26 14:05 陈桢梁 创建了PhotoDao.java文件
 */
@Mapper
public interface PhotoDao {
    /**
     * 通过id搜索图片对象
     * @param id 图片id
     */
    @Select(
        "select * " +
            "from photo " +
            "where id = #{id}")
    public PhotoEntity getById(Long id);

    /**
     * 插入图片
     * @param photoEntity
     */
    @Insert("insert into " +
        "photo(path, ctime, mtime)" +
        "value(#{path}, #{ctime}, #{mtime})")
    public void insert(PhotoEntity photoEntity);
    /**
     * 更新图片
     * @param photoEntity 图片实体
     */
    @Update(
        "<script>" +
            "update photo set" +
            "<if test = 'path != null'>path = #{path},</if>" +
            "<if test = 'mtime != null'>mtime = #{mtime},</if>" +
            "id = #{id}" +
            "where id = #{id}" +
            "</script>")
    public void update(PhotoEntity photoEntity);

    /**
     * 删除图片
     * @param id 图片id
     */
    @Delete(
        "delete from photo" +
            "where id = #{id}")
    public void delete(Long id);
}
