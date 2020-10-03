package com.hniecs.mainserver.entity.user;

import com.hniecs.mainserver.tool.security.Password;
import com.hniecs.mainserver.tool.security.SHA256;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @desc    用户基类
 * @author  yijie
 * @date    2020-09-12 15:34 yijie
 * @logs[0] 2020-09-12 15:34 yijie 创建了该文件
 * @logs[1] 2020-09-13 01:47 yijie 添加了密码生成与校验
 * @logs[2] 2020-09-13 01:47 yijie 添加了创建时间与修改时间成员
 * @logs[3] 2020-09-16 22:37 yijie 设置全部属性全部可获取
 * @logs[4] 2020-09-17 00:58 yijie 修改toString方法
 * @logs[5] 2020-09-22 17:10 yijie 移动实体至user包下
 */
@NoArgsConstructor
public class UserEntity {
    /**
     * 用户id
     */
    @Getter@Setter
    private long id;
    /**
     * 用户名
     */
    @Getter@Setter
    private String userName;
    /**
     * SHA算法加密后的密码
     */
    private String passwordSHA;
    /**
     * 用户头像url
     */
    @Getter@Setter
    private String avatarUrl;
    /**
     * 创建时间
     */
    @Getter@Setter
    private Date ctime;
    /**
     * 修改时间
     */
    @Getter@Setter
    private Date mtime;

    /**
     * 用户详细信息
     */
    @Getter@Setter
    private UserDetailEntity detail;

    public UserEntity(String userName, String password) {
        this.userName = userName;
        this.setPassword(password);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
            "id=" + id +
            ", userName='" + userName + '\'' +
            ", ctime=" + ctime +
            ", mtime=" + mtime +
            '}';
    }

    /**
     * 获取用户的SessionToken信息
     * @param saltCount 加密次数
     * @return  sessionToken
     */
    public String getUserToken (int saltCount) {
        return SHA256.salt(id + '&' + userName + '&' + passwordSHA, saltCount);
    }

    /**
     * 设置用户密码
     * @param password  新密码
     */
    public void setPassword (String password) {
        this.passwordSHA = Password.generatePasswordHash(password);
    }
    /**
     * 校验密码是否正确
     * @param password 待校验密码
     */
    public boolean vertifyPWD (String password) {
        return Password.checkPasswordHash(this.passwordSHA, password);
    }
}
