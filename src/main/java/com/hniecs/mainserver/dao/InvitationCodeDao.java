package com.hniecs.mainserver.dao;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
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
     * select c.*, u.* from invitation_code as c, user as u
     * where
     * -- # 通过 验证码 查
     * (c.invitation_code = '#{invitationCode}'
     * and u.id = c.create_user_id)
     * or
     * -- # 通过 标签、名字 查
     * (c.tag_name = '#{tagName}'
     * and u.id = c.create_user_id
     * and u.user_name = '#{userName}')
     * or
     * -- # 通过 名字 查
     * (u.id = c.create_user_id
     * and u.user_name = '#{userName}'
     * and '#{tagName}' is null)
     * or
     * -- # 通过 标签 查
     * (u.id = c.create_user_id
     * and c.tag_name = '#{}'
     * and '#{userName}' is null);
     * @param tagName         标签名
     * @param creatorName     创建者名
     * @param invitationCode  内容
     * @return
     */
    @Select("select c.*, u.* " +
        "from invitation_code as c, user as u " +
        "where " +
        "(c.invitation_code = #{invitationCode} " +
        "and u.id = c.create_user_id) " +
        "or  " +
        "(c.tag_name = #{tagName} " +
        "and u.id = c.create_user_id "  +
        "and u.user_name = #{creatorName}) " +
        "or " +
        "(u.id = c.create_user_id " +
        "and u.user_name = #{creatorName} " +
        "and #{tagName} is null) " +
        "or " +
        "(u.id = c.create_user_id " +
        "and c.tag_name = #{tagName} " +
        "and #{creatorName} is null);")
    @ResultMap("未封装")
    Map<UserEntity,InvitationCodeEntity> getInvitationCodeList(
        String tagName, String creatorName, String invitationCode);

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
