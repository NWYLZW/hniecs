package com.hniecs.mainserver.controller.admin;

import com.github.pagehelper.PageHelper;
import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.service.admin.InvitationCodeService;
import com.hniecs.mainserver.tool.CommonUseStrings;
import com.hniecs.mainserver.tool.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @desc    管理员邀请码操作 AdminInvitationCodeController.java
 * @author  yijie
 * @date    2020-09-17 01:26
 * @logs[0] 2020-09-17 01:26 yijie 创建了文件AdminInvitationCodeController.java
 * @logs[1] 2020-09-20 03:53 yijie 完善了addInvitationCodes接口, 添加了importInvitationCodes接口
 */
@RestController
@Slf4j
public class AdminInvitationCodeController {
    @Resource
    private InvitationCodeService invitationCodeService;

    // TODO 下面俩个添加接口进行入参合法性校验
    /**
     * 手动添加邀请码
     */
    @PostMapping("/admin/invitationCode/addList")
    public CommonResult addInvitationCode(
        @RequestBody Map<String, Object> invitationCodeMap,
        HttpServletRequest request) {

        List<String> invitationCodes = null;
        String tagName = null;
        Integer availableCount = null;
        try {
            invitationCodes = (ArrayList<String>) invitationCodeMap.get("invitationCodes");
            tagName = (String) invitationCodeMap.get("tagName");
            availableCount = (Integer) invitationCodeMap.get("availableCount");
        } catch (NullPointerException e) {
            log.error("属性值不存在!",e);
            return CommonResult.validateFailed();
        }

        UserEntity currentUser = (UserEntity) request.getSession().getAttribute("currentUser");

        Hashtable data = new Hashtable();

        String msg = invitationCodeService
            .addInvitationCodes(
                currentUser, availableCount, tagName, invitationCodes
                , data);
        if (msg.equals("0")) {
            return CommonResult.success(data);
        } else {
            return CommonResult.failed(msg);
        }
    }

    /**
     * 通过支付宝或微信导出账单文件 导入邀请码到数据库
     */
    @PostMapping("/admin/invitationCode/importFromExcel")
    public CommonResult importInvitationCodes(
        @RequestParam(value = "excelFile",required = true) MultipartFile excel,
        @RequestBody Map<String, Object> invitationCodeMap,
        HttpServletRequest request
    ){
        String tagName = null;
        Integer availableCount = null;
        String targetMoney = null;
        try {
            tagName = (String) invitationCodeMap.get("tagName");
            availableCount = (Integer) invitationCodeMap.get("availableCount");
            targetMoney = (String) invitationCodeMap.get("targetMoney");
        } catch (NullPointerException e) {
            log.error("属性值不存在!",e);
            return CommonResult.validateFailed();
        }
        // TODO 校验tagName是否为 支付宝或者微信

        UserEntity currentUser = (UserEntity) request.getSession().getAttribute("currentUser");
        InputStream excelIS = null;

        try {
            excelIS = excel.getInputStream();
        } catch (IOException e) {
            log.error("获取文件IS流出现了问题", e);
            return CommonResult.failed(CommonUseStrings.SERVER_FAILED.S);
        }
        Hashtable data = new Hashtable();
        String msg = invitationCodeService
            .addInvitationCodes(currentUser, availableCount, tagName, targetMoney, excelIS, data);
        if (msg.equals("0")) {
            return CommonResult.success(data);
        } else {
            return CommonResult.failed(msg);
        }
    }

    /**
     * 删除一个邀请码
     * @param id
     */
    @DeleteMapping("/admin/invitationCode/{id}")
    public CommonResult falseDeleteById(@RequestParam("id") Long id) {
        String message = invitationCodeService.falseDeleteById(id);
        if(message.equals("0")) {
            return CommonResult.success(message);
        }else {
            return CommonResult.failed(message);
        }
    }

    /**
     * 修改
     * 修改邀请码内容 禁用邀请码 添加标签 修改使用次数
     * @return
     */
    @PutMapping("/admin/invitationCode")
    public CommonResult updateInvitationCode(InvitationCodeEntity entity) {
        String message = invitationCodeService.updateInvitationCode(entity);
        if(message.equals("0")) {
            return CommonResult.success(message);
        }else {
            return CommonResult.failed(message);
        }
    }

    /**
     * 获取邀请码列表 根据多种筛选条件筛选 例如状态，内容，等等
     */
    @GetMapping("/admin/invitationCode")
    public CommonResult getInvitationCodesByCondition(
        @RequestBody Map<String, Object> dataMap) {

        String tagName = (String) dataMap.get("tagName");
        String invitationCode = (String) dataMap.get("InvitationCode");
        String creatorName = (String) dataMap.get("creatorName");
        Integer page = null;
        Integer size = null;

        try {
            page = (Integer) dataMap.get("page");
            size = (Integer) dataMap.get("size");
        } catch (NullPointerException e) {
            log.error("属性值不存在!");
            return CommonResult.failed();
        }
        //设置分页规则
        PageHelper.startPage(page,size);

        List<InvitationCodeEntity> data = invitationCodeService
            .getInvitationCodePage(tagName,creatorName,invitationCode);
        for (InvitationCodeEntity datum : data) {
            System.out.println(datum);
        }
        return CommonResult.success(data);
    }

}
