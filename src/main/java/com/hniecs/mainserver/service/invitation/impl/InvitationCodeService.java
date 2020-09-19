package com.hniecs.mainserver.service.invitation.impl;

import com.hniecs.mainserver.entity.UserEntity;
import com.hniecs.mainserver.model.InvitationCodeModel;
import com.hniecs.mainserver.service.invitation.IInvitationCodeService;
import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @desc    InvitationCodeService.java
 * @author  zerohua
 * @date    2020-09-17 20:18
 * @logs[0] 2020-09-17 20:18 zerohua 创建了InvitationCodeService.java文件
 */
@Service
public class InvitationCodeService implements IInvitationCodeService {

    @Resource
    private InvitationCodeModel invitationCodeModel;


    @Override
    public CommonResult addInvitationCodes(UserEntity userEntity,
                                               int availableCount,
                                               ArrayList<String> list) {

        String result = invitationCodeModel.addInvitationCodes(userEntity,availableCount,list);
        if(result.equals("0")) {
            return CommonResult.success(null);
        }else {
            return CommonResult.failed();
        }
    }
}
