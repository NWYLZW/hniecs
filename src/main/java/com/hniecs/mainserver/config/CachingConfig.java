package com.hniecs.mainserver.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈桢梁
 * @desc CachingConfig.java
 * @date 2020-09-26 19:22
 * @logs[0] 2020-09-26 19:22 陈桢梁 创建了CachingConfig.java文件
 */
@EnableCaching
@Configuration
public class CachingConfig {
    @Bean
    public CacheManager cacheManager(){
        return new ConcurrentMapCacheManager();
    }
}
