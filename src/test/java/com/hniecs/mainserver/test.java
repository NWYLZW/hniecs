package com.hniecs.mainserver;
/**
 * @author 陈桢梁
 * @desc test.java
 * @date 2020-10-12 18:50
 * @logs[0] 2020-10-12 18:50 陈桢梁 创建了test.java文件
 */

public class test {
    public <T> void Test(T condition){
        System.out.println(condition.getClass().getTypeName());
    }

    public static void main(String[] args) {
        new test().Test(new Long(12));
    }
}