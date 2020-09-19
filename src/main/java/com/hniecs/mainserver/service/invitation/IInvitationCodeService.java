package com.hniecs.mainserver.service.invitation;

import com.hniecs.mainserver.entity.UserEntity;
import com.hniecs.mainserver.tool.api.CommonResult;

import java.util.ArrayList;


/**
 * @program: main-server
 * @description: 负责验证码业务逻辑的Service接口
 * @author: zerohua
 * @create: 2020-09-17 19:26
 **/
public interface IInvitationCodeService {

    /**
     * 添加验证码进入数据库，可以一条也可以是多条
     * @param list
     * @param availableCount
     * @param userEntity
     * @return 执行结果
     */
    CommonResult addInvitationCodes(UserEntity userEntity,
                                    int availableCount,
                                    ArrayList<String> list);
}
