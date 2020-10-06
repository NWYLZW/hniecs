package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.FileEntity;
import org.apache.ibatis.annotations.*;

/**
 * @author 陈桢梁
 * @desc $END$ fileDao.java
 * @date 2020-09-26 14:05
 * @logs[0] 2020-09-26 14:05 陈桢梁 创建了fileDao.java文件
 */
@Mapper
public interface FileDao {
    /**
     * 通过id搜索图片对象
     * @param id 图片id
     */
    @Select(
        "select * " +
            "from file " +
            "where id = #{id}"
    )
    public FileEntity getById(Long id);

    /**
     * 通过用户id来查找图片对象
     * @param userId 用户id
     */
    @Select(
        "select * " +
            "from file "+
            "where userId = #{userId}"
    )
    public FileEntity getByUserId(long userId);

    /**
     * 通过路径查找图片实体
     * @param path 图片路径
     */
    @Select(
        "select * "+
            "from file "+
            "where path = #{path}"
    )
    public FileEntity getByPath(String path);
    /**
     * 插入图片
     * @param fileEntity
     */
    @Insert("insert into " +
        "file(path, ctime, mtime)" +
        "value(#{path}, #{ctime}, #{mtime})")
    public void insert(FileEntity fileEntity);
    /**
     * 更新图片
     * @param fileEntity 图片实体
     */
    @Update(
        "<script>" +
            "update file set" +
            "<if test = 'path != null'>path = #{path},</if>" +
            "<if test = 'mtime != null'>mtime = #{mtime},</if>" +
            "id = #{id} " +
            "where id = #{id}" +
            "</script>")
    public void update(FileEntity fileEntity);

    /**
     * 删除图片
     * @param id 图片id
     */
    @Delete(
        "delete from file" +
            "where id = #{id}")
    public void delete(Long id);
}
