package com.hniecs.mainserver.annotation.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @desc    给需要登陆的接口添加该注解 NotNeedLogin.java
 * @author  yijie
 * @date    2020-09-13 22:07
 * @logs[0] 2020-09-13 22:07 yijie 创建了NotNeedLogin.java文件
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NotNeedLogin {
}
