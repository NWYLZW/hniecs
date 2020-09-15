package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * @desc    InvitationCodeDao.java
 * @author  yijie
 * @date    2020-09-13 19:01
 * @logs[0] 2020-09-13 19:01 yijie      创建了InvitationCodeDao.java文件
 * @logs[1] 2020-09-13 19:01 zerohua    插入列名格式为 ${columnName}
 */
@Mapper
public interface InvitationCodeDao {

    public enum columnName{
        create_user_id,id,status,invitation_code;
    }
    /**
     * 搜索返回list集合数据
     * @param col 列名
     * @param condition 条件
     */
    @Select("select * from invitation_code where ${columnName}=#{condition}")
    public ArrayList<InvitationCodeEntity> getAll(@Param("columnName") columnName col,@Param("condition") String condition);

    /**
     *搜索返回单个数据
     * @param col 列名
     * @param condition 条件
     */
    @Select("select * from invitation_code where ${columnName}=#{condition}")
    public InvitationCodeEntity getOne(@Param("columnName") columnName col,int condition);
    /**
     *搜索返回单个数据
     * @param col 列名
     * @param condition 条件
     */
    @Select("select * from invitation_code where ${columnName}=#{condition}")
    public InvitationCodeEntity getOne(@Param("columnName") columnName col,String condition);

    /**
     * 插入数据
     * @param invitationCode InvitationCodeEntity对象
     */
    @Insert("insert into " +
        "invitation_code(create_user_id,invitation_code,status,can_invite_count,ctime,mtime)" +
        "values(#{createUserId},#{invitationCode},#{status},#{canInviteCount},#{ctime},#{mtime})")
    void insert(InvitationCodeEntity invitationCode);

    @Update("UPDATE invitation_code " +
        "SET create_user_id=#{createUserId}, invitation_code=#{invitationCode},status=#{status},can_invite_count=#{canInviteCount}, " +
        "ctime=#{ctime},mtime=#{mtime} " +
        "WHERE id =#{id}")
    void update(InvitationCodeEntity invitationCodeEntity);
    /**
     *
     * @param col 列名
     * @param condition 条件
     */
    @Delete("DELETE FROM user WHERE ${columnName} = #{condition}")
    void delete(@Param("columnName")columnName col,@Param("condition") String condition);

}
