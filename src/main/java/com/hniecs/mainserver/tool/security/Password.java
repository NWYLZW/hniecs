package com.hniecs.mainserver.tool.security;

import com.hniecs.mainserver.tool.security.SHA256;

/**
 * @desc    密码有关帮助方法 Password.java
 * @date    2020-09-13 00:00
 * @author  yijie
 * @logs[0] 2020-09-13 00:00 yijie 创建了文件Password.java
 * @logs[1] 2020-09-16 21:30 yijie 抽离了字符串的加密算法
 */
public class Password {
    public static final int SALT_COUNT = 5;
    public static boolean checkPasswordHash(String pwdHash,String pwd){
        // 校验是否为对应加密后的密码
        return SHA256.salt(pwd, SALT_COUNT).equals(pwdHash);
    }
    public static String generatePasswordHash(String pwd){
        return SHA256.salt(pwd, SALT_COUNT);
    }
}
