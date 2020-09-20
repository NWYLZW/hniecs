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
 * @logs[2] 2020-09-16 01:00 yijie      重构代码结构，修复bug
 */
@Mapper
public interface InvitationCodeDao {
    public enum columnName{
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
    public ArrayList<InvitationCodeEntity> getAll(columnName col, String condition);

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
    public InvitationCodeEntity getOne(columnName col, String condition);

    /**
     * 新增一条邀请码
     * @param invitationCode InvitationCodeEntity对象
     */
    @Insert(
        "insert into " +
            "invitation_code(create_user_id, invitation_code, tag, status,can_invite_count, ctime, mtime)" +
            "value(#{createUserId}, #{invitationCode}, #{tag}, #{status}, #{canInviteCount}, #{ctime}, #{mtime})"
    )
    void addNew(InvitationCodeEntity invitationCode);

    /**
     * 通过邀请码id 更新一条邀请码的数据
     * @param invitationCodeEntity  邀请码实体对象
     */
    @Update(
        "update invitation_code " +
            "set invitation_code=#{invitationCode}, status=#{status}, available_invite_count=#{canInviteCount}, mtime=#{mtime}" +
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
