package com.hniecs.mainserver.annotation.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @desc      权限控制注解 PermissionRequired.java
 * @author    yijie
 * @date      2020-09-22 02:46
 * @logs[0]   2020-09-22 02:46 yijie 创建了文件PermissionRequired.java
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionRequired {
    // 命名空间
    public String scope() default "";
    // unit permission 元权限
    public long permission() default 0X0000L + 0X00000L;
    // 权限组
    public long rule() default 0X0000L + 0X00000L;
}
