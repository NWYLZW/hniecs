package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc    InvitationCodeDao.java
 * @author  yijie
 * @date    2020-09-13 19:01
 * @logs[0] 2020-09-13 19:01 yijie      创建了InvitationCodeDao.java文件
 * @logs[1] 2020-09-13 19:01 zerohua    插入列名格式为 ${columnName}
 * @logs[2] 2020-09-16 01:00 yijie      重构代码结构，修复bug
 * @logs[3] 2020-09-25 01:18 yijie      重构代码
 */
@Mapper
public interface InvitationCodeDao {
    enum columnName{
        create_user_id, id, status, invitation_code;
    }

    /**
     * 获取全部未被删除邀请码列表
     */
    @Select(
        "select * " +
            "from invitation_code " +
            "where status!=-1"
    )
    ArrayList<InvitationCodeEntity> getAll();

    /**
     * 根据(创建用户id|邀请码id|邀请码状态|邀请码内容) 获取符合条件的某个邀请码
     * @param col       列名
     * @param condition 条件
     */
    @Select(
        "select * " +
            "from invitation_code " +
            "where ${columnName}=#{condition}"
    )
    InvitationCodeEntity getOne(columnName col, String condition);


    /**
     * 进行详细的邀请码查询
     * @param tagName         标签名
     * @param creatorName     创建者用户名名
     * @param invitationCode  邀请码内容
     */
    @Select(
        "select * " +
        "from invitation_code " +
            "where " +
            "invitation_code like #{invitationCode} " +
            "and (" +
                "select user_name " +
                "from user " +
                    "where user.id=create_user_id" +
                ") like #{creatorName} " +
            "and tag_name like #{tagName}  or tag_name is null " +
            "and status!=-1 "
    )
    @Result(
        property="creator", column="create_user_id",
        one=@One(
            select="com.hniecs.mainserver.dao.UserDao.getSimpleById"
        )
    )
    List<InvitationCodeEntity> getInvitationCodes(
        String creatorName, String tagName, String invitationCode
    );

    /**
     * 新增一条邀请码
     * @param invitationCode InvitationCodeEntity对象
     */
    @Insert(
        "insert into " +
            "invitation_code(create_user_id, invitation_code, tag_name, status, available_invite_count, ctime, mtime)" +
            "values(#{createUserId}, #{invitationCode}, #{tagName}, #{status}, #{availableInviteCount}, #{ctime}, #{mtime})"
    )
    void addNew(InvitationCodeEntity invitationCode);

    /**
     * 通过邀请码id 更新一条邀请码
     * @param invitationCodeEntity  邀请码实体对象
     */
    @Update(
        "<script> \n" +
            "update invitation_code set " +
                "<if test='invitationCode!=null'>invitation_code=#{invitationCode}, </if>" +
                "<if test='status!=null'>status=#{status}, </if>" +
                "<if test='available_invite_count!=null'>available_invite_count=#{available_invite_count}, </if>" +
                "<if test='mtime!=null'>mtime=#{mtime}, </if>" +
            "where id=#{id}" +
        "</script>"
    )
    int update(InvitationCodeEntity invitationCodeEntity);
}
