package com.hniecs.mainserver.entity.user;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * @desc    用户详情信息实体 UserDetailEntity.java
 * @author  yijie
 * @date    2020-09-22 17:12
 * @logs[0] 2020-09-22 17:12 yijie 创建了UserDetailEntity.java文件
 */
@Data
public class UserDetailEntity {
    // 与user表连接外键
    long user_id;
    // 用户真实姓名
    String real_name;
    // 专业名称
    String profession;
    // 班级号
    long class_num;
    // 校内学号
    String school_num;
    // 联系qq
    String qq_num;
    // 联系电话
    String tel_num;
    // 用户注册时使用的邀请码id
    long invitation_code_id;
    // 连接权限表id
    long rule_id;
    // 创建时间
    DateTime ctime;
    // 修改时间
    DateTime mtime;
}
