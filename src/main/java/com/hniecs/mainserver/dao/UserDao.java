package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author  yijie
 * @desc    UserDao.java
 * @date    2020-09-13
 * @logs[0] yijie 2020-09-13 创建了文件UserDao.java
 */
@Mapper
public interface UserDao {
    /**
     * 获得所有用户的简略信息
     */
    @Select("select * from user")
    public List<UserEntity> getSimpleUsers();
    /**
     * 通过id获得某个用户的简略信息
     */
    @Select("select * from  user where id = #{id}")
    public UserEntity getUserSimpleById(Long id);

    /**
     * 通过用户名获得某个用户的简略信息
     */
    @Select("select * from  user where user_name = #{userName}")
    public UserEntity getUserSimpleByUserName(String userName);

    /**
     * 插入一个用户
     * @param user 用户实体
     * @return 操作成功行数
     */
    @Insert(
        "INSERT INTO " +
            "user(user_name, passwordSHA, ctime) " +
            "VALUES(#{userName}, #{passwordSHA}, #{ctime})"
    )
    int insert(UserEntity user);

    /**
     * @param user 用户实体
     * @return 操作成功行数
     */
    @Update(
        "UPDATE user " +
            "SET user_name=#{userName}, passwordSHA=#{passwordSHA}, mtime=#{mtime} " +
            "WHERE id =#{id}"
    )
    int update(UserEntity user);

    /**
     * @param id 用户id
     * @return 操作成功行数
     */
    @Delete("DELETE FROM user WHERE id =#{id}")
    int delete(Long id);
}
