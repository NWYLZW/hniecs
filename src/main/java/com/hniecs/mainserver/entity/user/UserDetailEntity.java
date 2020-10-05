package com.hniecs.mainserver.entity.user;

import com.hniecs.mainserver.entity.Rules;
import lombok.Data;

import java.util.Date;

/**
 * @desc    用户详情信息实体 UserDetailEntity.java
 * @author  yijie
 * @date    2020-09-22 17:12
 * @logs[0] 2020-09-22 17:12 yijie 创建了UserDetailEntity.java文件
 * @logs[1] 2020-09-25 05:36 yijie 属性格式错误
 */
@Data
public class UserDetailEntity {
    // 与user表连接外键
    long userId;
    // 用户真实姓名
    String realName;
    // 专业名称
    String profession;
    // 班级号
    String classNum;
    // 校内学号
    String schoolNum;
    // 联系qq
    String qqNum;
    // 联系电话
    String telNum;
    // 用户注册时使用的邀请码id
    Long invitationCodeId;
    // 连接权限表id
    Long ruleId;
    // 用户权限实体
    Rules.RuleEntity rule;
    // 创建时间
    Date ctime;
    // 修改时间
    Date mtime;

    /**
     * 手动添加了带参数的构造方法，编译器就不会给我们添加默认的无参构造方法。
     * 导致mybatis无法new这个实体类。
     * 无法做映射。
     * 手动添加
     */
    public UserDetailEntity() {
    }

    public UserDetailEntity(String realName, String profession, String classNum, String schoolNum, String qqNum, String telNum, long invitationCodeId, long ruleId, Date ctime) {
        this.realName = realName;
        this.profession = profession;
        this.classNum = classNum;
        this.schoolNum = schoolNum;
        this.qqNum = qqNum;
        this.telNum = telNum;
        this.invitationCodeId = invitationCodeId;
        this.ruleId = ruleId;
        this.ctime = ctime;
    }
}
