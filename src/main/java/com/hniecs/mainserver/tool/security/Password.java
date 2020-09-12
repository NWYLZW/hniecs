package com.hniecs.mainserver.tool.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @desc    密码有关加密方法 Password.java
 * @date    2020-09-13
 * @author  yijie
 * @logs[0] yijie 2020-09-13 创建了文件Password.java
 */
public class Password {
    public static boolean checkPasswordHash(String pwdHash,String pwd){
        // 校验是否为对应加密后的密码
        return getSHA256(pwd).equals(pwdHash);
    }
    public static String generatePasswordHash(String pwd){
        return getSHA256(pwd);
    }

    /**
     * 利用java原生的类实现SHA256加密
     * @param str 明文
     * @return 密文
     */
    private static String getSHA256(String str){
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodestr = byteToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodestr;
    }
    /**
     * 将byte转为16进制
     * @param bytes
     * @return 16进制字符串
     */
    private static String byteToHex(byte[] bytes){
        StringBuilder stringBuffer = new StringBuilder();
        String temp = null;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
