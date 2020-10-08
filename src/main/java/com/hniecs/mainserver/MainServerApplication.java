package com.hniecs.mainserver;

import com.hniecs.mainserver.model.RuleModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
@EnableCaching
@SpringBootApplication
@Slf4j
public class MainServerApplication {

    /**
     * SpringBoot 应用上下文
     */
    public static ConfigurableApplicationContext context;

    /**
     * 一些启动任务
     */
    public static void init () {
        try {
            context.getBean(RuleModel.class).addAll();
        } catch (Exception e) {
            log.error("插入权限组时出现了错误，如有修改权限配置信息，请重启服务");
        }
    }

    public static void main (String[] args) {
        context = new SpringApplication(
            MainServerApplication.class
        ).run(args);
        init();
    }

}
