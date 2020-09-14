package com.hniecs.mainserver.config;

import com.hniecs.mainserver.tool.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @desc    HNIECS主配置文件 MainWebConfigOfHNIECS.java
 * @author  yijie
 * @date    2020-09-14 13:52
 * @logs[0] 2020-09-14 13:52 yijie 创建了MainWebConfigOfHNIECS.java文件
 */
@Configuration
public class MainWebConfigOfHNIECS implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer configurer = new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry
                    .addInterceptor( new LoginInterceptor() )
                    .addPathPatterns("/**");
            }
        };
        return configurer;
    }
}
