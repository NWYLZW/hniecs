package com.hniecs.mainserver.tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Function;

/**
 * @desc    环境控制 EnvController.java
 * @author  yijie
 * @date    2020-11-11 11:16
 * @logs[0] 2020-11-11 11:16 yijie 创建了EnvController.java文件
 * @param <P>   参数类型
 * @param <R>   返回值类型
 */
@Configuration
public class EnvController<P, R> {
    @Value("${spring.profiles.active:#{null}}")
    private String active;

    public R run(Map<String, Function<P, R>> envsRun, P arg) {
        return envsRun.get(active == null?"dev":active).apply(arg);
    }
}
