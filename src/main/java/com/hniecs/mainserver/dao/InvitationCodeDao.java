package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc    InvitationCodeDao.java
 * @author  yijie
 * @date    2020-09-13 19:01
 * @logs[0] 2020-09-13 19:01 yijie      创建了InvitationCodeDao.java文件
 * @logs[1] 2020-09-13 19:01 zerohua    插入列名格式为 ${columnName}
 * @logs[2] 2020-09-16 01:00 yijie      重构代码结构，修复bug
 */
@Mapper
public interface InvitationCodeDao {

    enum columnName{
        create_user_id, id, status, invitation_code;
    }

    String ALL_SEARCH = "select c.*, u.* from invitation_code as c, user as u where";

    /**
     * -- 通过 验证码模糊、名字、标签 查
     */
    String SEARCH_BY_ALL =
        "(u.id = c.create_user_id " +
            "c.invitation_code like %#{invitationCode}% " +
            "and u.user_name = #{creatorName} " +
            "and c.tag_name = #{tagName}) ";

    /**
     * -- 通过 标签、名字 查
     */
    String SEARCH_BY_TAG_AND_NAME =
        "(u.id = c.create_user_id " +
            "and c.tag_name = #{tagName} " +
            "and u.user_name = #{creatorName} " +
            "and #{invitationCode} is null) ";

    /**
     * -- 通过 验证码模糊、名字 查
     */
    String SEARCH_BY_INVITATION_AND_NAME =
        "(u.id = c.create_user_id "+
            "and u.user_name = #{creatorName} "+
            "and #{invitationCode} like %#{invitationCode}% "+
            "and #{tagName} is null) ";

    /**
     * -- 通过 验证码模糊、标签 查
     */
    String SEARCH_BY_INVITATION_AND_TAG =
        "(u.id = c.create_user_id " +
            "and c.tag_name = #{tagName} " +
            "and #{invitationCode} like %#{invitationCode}% " +
            "and #{creatorName} is null) ";

    String SEARCH_BY_INVITATION =
        "(u.id = c.create_user_id " +
            "and c.invitation_code like %#{invitationCode}% " +
            "and #{tagName} is null " +
            "and #{creatorName} is null) ";

    /**
     * -- 通过 标签 查
     */
    String SEARCH_BY_TAG = "(u.id = c.create_user_id " +
        "and c.tag_name = #{tagName} " +
        "and #{creatorName} is null " +
        "and #{invitationCode} is null) ";

    /**
     * -- 通过 名字 查
     */
    String SEARCH_BY_NAME = "(u.id = c.create_user_id " +
        "and u.user_name = #{creatorName} " +
        "and #{tagName} is null " +
        "and #{invitationCode} is null) ";

    /**
     * 根据(创建用户id|邀请码id|邀请码状态|邀请码内容) 获取符合条件的邀请码数组
     * @param col       列名
     * @param condition 条件
     */
    @Select(
        "select * " +
            "from invitation_code " +
            "where ${columnName}=#{condition}"
    )
   ArrayList<InvitationCodeEntity> getAll(columnName col, String condition);

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
     * 来呀，查老子啊！！！！
     * @param tagName         标签名
     * @param creatorName     创建者名
     * @param invitationCode  内容
     * @return
     */
    @Select(
        "select * " +
        "from invitation_code " +
        "where " +
        "invitation_code like #{invitationCode} " +
        "and " +
        "(select user_name " +
        "from user " +
        "where user.id = create_user_id) " +
        "like #{creatorName} " +
        "and tag_name = #{tagName};")
    @Result(property = "creator", column = "create_user_id",
            one=@One(select = "com.hniecs.mainserver.dao.UserDao.getUserSimpleById"))
    List<InvitationCodeEntity> getInvitationCodeList(
        @Param("tagName") String tagName,
        @Param("creatorName") String creatorName,
        @Param("invitationCode") String invitationCode);

    /**
     * 新增一条邀请码
     * @param invitationCode InvitationCodeEntity对象
     */
    @Insert(
        "insert into " +
            "invitation_code(create_user_id, invitation_code, tag_name, status,available_invite_count, ctime, mtime)" +
            "values(#{createUserId}, #{invitationCode}, #{tagName}, #{status}, #{availableInviteCount}, #{ctime}, #{mtime})"
    )
    void addNew(InvitationCodeEntity invitationCode);

    /**
     * 通过邀请码id 更新一条邀请码的数据
     * @param invitationCodeEntity  邀请码实体对象
     */
    @Update(
        "update invitation_code " +
            "set invitation_code=#{invitationCode}, status=#{status}, available_invite_count=#{availableInviteCount}, mtime=#{mtime}" +
            "where id=#{id}"
    )
    void update(InvitationCodeEntity invitationCodeEntity);

    /**
     * 通过邀请码id 删除一个邀请码
     * @param id 邀请码id
     */
    @Delete(
        "delete " +
            "from invitation_code " +
            "where id=#{id}"
    )
    void deleteById(long id);

}
