package com.hniecs.mainserver.tool.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @desc    SHA256加密算法 SHA256.java
 * @author  yijie
 * @date    2020-09-16 21:22
 * @logs[0] 2020-09-16 21:22 yijie 创建了SHA256.java文件
 */
public class SHA256 {
    /**
     * 利用java原生的类实现SHA256加密
     * @param str 明文
     * @return 密文
     */
    public static String salt(String str){
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
     * 利用java原生的类实现SHA256加密
     * @param str   明文
     * @param count 加密次数
     * @return 密文
     */
    public static String salt(String str, int count){
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodestr = byteToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (count <= 0) {
            return encodestr;
        } else {
            return salt(encodestr, count-1);
        }
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
