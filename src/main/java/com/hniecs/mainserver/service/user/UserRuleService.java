package com.hniecs.mainserver.service.user;

import com.hniecs.mainserver.entity.AppEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @desc    用户角色相关service UserRuleService.java
 * @author  yijie
 * @date    2020-10-05 21:27
 * @logs[0] 2020-10-05 21:27 yijie 创建了UserRuleService.java文件
 */
@Service
public class UserRuleService {
    /**
     * 获取应用列表
     * @param returnData 返回数据类型
     */
    public String getApps (List<AppEntity> returnData) {
        returnData.addAll(
            new ArrayList<>(Arrays.asList(
                new AppEntity(
                    "后台管理", "管理员后台管理页面", "&#xe76e;",
                    "/admin/manage/user", "[1]", 0
                ),
                new AppEntity(
                    "我的消息", "用户消息中心", "&#xe6a2;",
                    "/message/my/index", "", 0, new ArrayList<>(Arrays.asList(
                    new AppEntity(
                        "公告", "网站公告墙", "&#xe6aa;",
                        "/admin/announcement/index", "", 0
                    ), new AppEntity(
                        "聊天", "网站聊天室", "&#xe6a9;",
                        "/admin/chat/index", "", 0
                    ), new AppEntity(
                        "看板", "网站公告墙", "&#xe6ab;",
                        "/admin/board/index", "", 0
                    )
                ))
                )
            ))
        );
        return "0";
    }
}
