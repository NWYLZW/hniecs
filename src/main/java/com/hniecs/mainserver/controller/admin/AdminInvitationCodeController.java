package com.hniecs.mainserver.controller.admin;

import cn.hutool.http.server.HttpServerRequest;
import com.hniecs.mainserver.entity.UserEntity;
import com.hniecs.mainserver.service.invitation.IInvitationCodeService;
import com.hniecs.mainserver.tool.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

/**
 * @desc      管理员邀请码操作 AdminInvitationCodeController.java
 * @author    yijie
 * @date      2020-09-17 01:26
 * @logs[0]   2020-09-17 01:26 yijie 创建了文件AdminInvitationCodeController.java
 */
@RestController
@Slf4j
public class AdminInvitationCodeController {

    @Resource
    private IInvitationCodeService invitationCodeService;

    /**
     * 添加单个邀请码
     * @return
     */
    @PostMapping("/admin/invitationCode/add")
    public CommonResult addInvitationCode(@RequestBody Map<String, Object> invitationCodeMap,
                                          HttpServletRequest request) {
        //此处用List,适应后台,一个多个都能搞
        ArrayList<String> invitationCodeStr = new ArrayList<String>();
        Integer availableCount = null;
        UserEntity userEntity = null;
        try {
            invitationCodeStr.add((String) invitationCodeMap.get("invitationCode"));
            availableCount = (Integer) invitationCodeMap.get("availableCount");
            userEntity = (UserEntity) request.getSession().getAttribute("currentUser");
        } catch (Exception e) {
            log.warn("参数错误");
            return CommonResult.validateFailed();
        }
        return invitationCodeService.addInvitationCodes(userEntity,availableCount,invitationCodeStr);
    }


    /**
     * 通过支付宝或微信导出账单文件 导入邀请码到数据库
     * @return
     */
    @GetMapping("")
    public CommonResult addInvitationCodes() {

        return null;
    }


    /**
     * 获取邀请码列表 根据多种筛选条件筛选 例如状态，内容，等等
     * @return
     */
    @GetMapping("/")
    public CommonResult getInvitationCodesByCondition() {
        return null;
    }


    /**
     * 通过邀请码id 修改邀请码内容 禁用邀请码 添加标签 修改使用次数
     * @return
     */
    @PutMapping("")
    public CommonResult updateInvitationCode() {
        return null;
    }

}
