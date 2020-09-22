package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.user.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author  yijie
 * @desc    UserDao.java
 * @date    2020-09-13 00:00
 * @logs[0] 2020-09-13 00:00 yijie 创建了文件UserDao.java
 * @logs[1] 2020-09-16 01:10 yijie 修改格式错误
 */
@Mapper
public interface UserDao {
    /**
     * 获得所有用户的简略信息
     * @return  用户实体列表
     */
    @Select("select * from user")
    public List<UserEntity> getSimpleUsers();
    /**
     * 获得某个用户的简略信息
     * @param   id 用户id
     * @return  用户实体
     */
    @Select("select * from  user where id=#{id}")
    public UserEntity getUserSimpleById(Long id);
    /**
     * 获得某个用户的简略信息
     * @param   userName  用户名
     * @return  用户实体
     */
    @Select("select * from  user where user_name=#{userName}")
    public UserEntity getUserSimpleByUserName(String userName);

    /**
     * 新增一个用户
     * @param   user 用户实体
     * @return 操作成功行数
     */
    @Insert(
        "insert into " +
            "user(user_name, passwordSHA, ctime) " +
            "values(#{userName}, #{passwordSHA}, #{ctime})"
    )
    int addNew(UserEntity user);

    /**
     * 通过用户id更新用户
     * @param   user 用户实体
     * @return 操作成功行数
     */
    @Update(
        "update user " +
            "set user_name=#{userName}, passwordSHA=#{passwordSHA}, mtime=#{mtime} " +
            "where id=#{id}"
    )
    int updateById(UserEntity user);

    /**
     * 通过用户id删除用户
     * @param   id 用户id
     * @return 操作成功行数
     */
    @Delete("delete from user where id=#{id}")
    int delete(Long id);
}
