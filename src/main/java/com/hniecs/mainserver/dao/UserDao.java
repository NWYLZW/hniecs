package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author  yijie
 * @desc    UserDao.java
 * @date    2020-09-13
 * @logs[0] yijie 2020-09-13 创建了文件UserDao.java
 */
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
    public UserEntity getUserSimpleById(UserEntity param);

    /**
     * 插入一个用户
     */
    @Insert("INSERT INTO user(user_name, passwordSHA) VALUES(#{userName}, #{passwordSHA})")
    void insert(UserEntity user);
    /**
     * 修改一个用户
     */
    @Update("UPDATE user SET user_name=#{userName},passwordSHA=#{passwordSHA} WHERE id =#{id}")
    void update(UserEntity user);
    /**
     * 删除一个用户
     */
    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(Long id);
}
