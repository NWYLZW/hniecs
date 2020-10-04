package com.hniecs.mainserver;

import com.hniecs.mainserver.model.RuleModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainServerApplication {

    /**
     * 一些启动任务
     * @param context   springBoot上下文
     */
    public static void init (ConfigurableApplicationContext context) {
        context.getBean(RuleModel.class).addAll();
    }

    public static void main (String[] args) {
        SpringApplication app = new SpringApplication(MainServerApplication.class);
        ConfigurableApplicationContext context = app.run(args);

        init(context);
    }

}
