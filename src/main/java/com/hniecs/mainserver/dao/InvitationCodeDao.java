package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * @desc    InvitationCodeDao.java
 * @author  yijie
 * @date    2020-09-13 19:01
 * @logs[0] 2020-09-13 19:01 yijie 创建了InvitationCodeDao.java文件
 */
@Mapper
public interface InvitationCodeDao {
    // TODO czl 根据(创建用户id|邀请码id|邀请码状态) 查 邀请码

    // TODO czl 增 邀请码
    // TODO czl 根据(创建用户id|邀请码id|邀请码状态) 删 邀请码
    // TODO czl 根据(邀请码id) 改 邀请码 (邀请码状态|邀请码邀请次数|邀请码内容)
    public enum columnName{
        create_user_id,id,status;
    }
    /**
     * 搜索返回list集合数据
     * @param col 列名
     * @param condition 条件
     */
    @Select("select * from invitation_code #{columnName}=#{condition}")
    public ArrayList<InvitationCodeEntity> getAll(@Param("columnName") columnName col,@Param("condition") String condition);

    /**
     *搜索返回单个数据
     * @param col 列名
     * @param condition 条件
     */
    @Select("select * from invitation_code #{columnName} = #{id}")
    public InvitationCodeEntity getOne(@Param("columnName")columnName col,@Param("condition") String condition);

    /**
     * 插入数据
     * @param invitationCode InvitationCodeEntity对象
     */
    @Insert("insert into " +
        "invitation_code(creat_user_id,invitation_code,status,can_invite_count,ctime,mtime)" +
        "value(#{creat_user_id},#{invitation_code},#{status},#{can_invite_count},#{ctime},#{mtime})")
    void insert(InvitationCodeEntity invitationCode);

    @Update("UPDATE invitation_code " +
        "SET create_user_id=#{create_user_id}, invitation_code=#{invitation_code},status=#{status},can_invite_count=#{can_invite_count}, " +
        "ctime=#{ctime},mtime=#{mtime} " +
        "WHERE id =#{id}")
    void update(InvitationCodeEntity invitationCodeEntity);
    /**
     *
     * @param col 列名
     * @param condition 条件
     */
    @Delete("DELETE FROM user WHERE #{columnName} = #{condition}")
    void delete(@Param("columnName")columnName col,@Param("condition") String condition);

}
