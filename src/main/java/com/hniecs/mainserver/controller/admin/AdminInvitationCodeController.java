package com.hniecs.mainserver.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.service.admin.InvitationCodeService;
import com.hniecs.mainserver.tool.CommonUseStrings;
import com.hniecs.mainserver.tool.api.CommonResult;
import com.hniecs.mainserver.tool.security.SessionTool;
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
 * @logs[2] 2020-09-23 02:59 yijie 预留TODO，修改格式
 */
@RestController
@Slf4j
public class AdminInvitationCodeController {
    @Resource
    private InvitationCodeService invitationCodeService;

    // TODO 下面俩个添加接口进行入参合法性校验
    /**
     * 根据json格式请求信息添加邀请码
     * @param requestData 邀请码信息请求字典
     * @bodyParam invitationCodes   Array      Y   []  待添加的邀请码列表
     * @bodyParam tagName           String     Y   ""  标签名
     * @bodyParam availableCount    Integer    N   0   邀请码可用次数
     */
    @PostMapping("/admin/invitationCode/list")
    public CommonResult addInvitationCode(
        @RequestBody Map<String, Object> requestData
    ) {
        List<String> invitationCodes;
        String tagName;
        Integer availableCount;

        try {
            invitationCodes = (ArrayList<String>) requestData.get("invitationCodes");
            tagName = (String) requestData.get("tagName");
            availableCount = (Integer) requestData.get("availableCount");
        } catch (ClassCastException | NullPointerException e) {
            return CommonResult.validateFailed();
        } catch (Exception e) {
            log.error("发生了未知错误", e);
            return CommonResult.failed();
        }
        HashMap<String, Object> data = new HashMap<>();
        if (invitationCodes.isEmpty()) {
            data.put("successCount", 0);
            data.put("failureCount", 0);
            return CommonResult.success(data);
        }
        String msg = invitationCodeService
            .addInvitationCodes(
                SessionTool.curUser(), availableCount, tagName, invitationCodes
                , data
            );
        if (msg.equals("0")) {
            return CommonResult.success(data);
        } else {
            return CommonResult.failed(msg);
        }
    }

    /**
     * 通过支付宝或微信导出账单文件 导入邀请码到数据库
     * @param excel             表格二进制流文件
     * @param requestData       请求数据
     * @bodyParam invitationCodes   Array      Y   []  待添加的邀请码列表
     * @bodyParam tagName           String     Y   ""  标签名
     * @bodyParam availableCount    Integer    N   0   邀请码可用次数
     */
    @PostMapping("/admin/invitationCode/importFromExcel")
    public CommonResult importInvitationCodes(
        @RequestParam(value = "excelFile", required = true) MultipartFile excel,
        @RequestBody Map<String, Object> requestData
    ){
        String tagName = null;
        Integer availableCount = null;
        String targetMoney = null;
        try {
            tagName = (String) requestData.get("tagName");
            availableCount = (Integer) requestData.get("availableCount");
            targetMoney = (String) requestData.get("targetMoney");
        } catch (NullPointerException e) {
            log.error("属性值不存在!",e);
            return CommonResult.validateFailed();
        }
        // TODO 校验tagName是否为 支付宝或者微信

        InputStream excelIS = null;
        try {
            excelIS = excel.getInputStream();
        } catch (IOException e) {
            log.error("获取文件IS流出现了问题", e);
            return CommonResult.failed(CommonUseStrings.SERVER_FAILED.S);
        }

        HashMap<String, Object> data = new HashMap<>();
        String msg = invitationCodeService
            .addInvitationCodes(
                SessionTool.curUser(), availableCount, tagName, targetMoney, excelIS
                , data
            );
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
    @DeleteMapping("/admin/invitationCode/one")
    public CommonResult falseDeleteById(Long id) {
        String message = invitationCodeService.deleteById(id);
        if(message.equals("0")) {
            return CommonResult.success(message);
        }else {
            return CommonResult.failed(message);
        }
    }

    /**
     * 修改
     * 修改邀请码内容 禁用邀请码 添加标签 修改使用次数
     */
    @PutMapping("/admin/invitationCode/one")
    public CommonResult updateInvitationCode(
        InvitationCodeEntity entity
    ) {
        String message = invitationCodeService.updateInvitationCode(entity);
        if(message.equals("0")) {
            return CommonResult.success();
        }else {
            return CommonResult.failed(message);
        }
    }

    /**
     * 获取邀请码列表
     * @bodyParam invitationCode    String  N   ""  邀请码内容
     * @bodyParam creatorName       String  N   ""  创建者姓名
     * @bodyParam tagName           String  N   ""  标签名
     * @bodyParam page              int     N   1   页码
     * @bodyParam size              int     N   20  每页个数
     */
    @GetMapping("/admin/invitationCode/search")
    public CommonResult getInvitationCodesByCondition(
        @RequestParam(name = "invitationCode"   , required = false, defaultValue = "") String invitationCode,
        @RequestParam(name = "creatorName"      , required = false, defaultValue = "") String creatorName,
        @RequestParam(name = "tagName"          , required = false, defaultValue = "") String tagName,
        @RequestParam(name = "page"             , required = false, defaultValue = "1") Integer page,
        @RequestParam(name = "size"             , required = false, defaultValue = "20") Integer size
    ) {
        // 设置分页规则
        PageHelper.startPage(page, size);

        List<InvitationCodeEntity> data = new Page<InvitationCodeEntity>();
        String message = invitationCodeService
            .getInvitationCodePage(
                invitationCode, creatorName, tagName
                , data
            );
        if(message.equals("0")) {
            return CommonResult.success(data);
        }else {
            return CommonResult.failed(message);
        }
    }

    /**
     * 返回所有不为空不重复的tagName
     */
    @GetMapping("/admin/invitationCode/getTagName")
    public CommonResult getTagName(){
        ArrayList<String> tagNameList = new ArrayList<>();
        String msg = invitationCodeService.geTagNameList(tagNameList);
        if(msg.equals("0")){
            return CommonResult.success(tagNameList,"获取tagName成功");
        }
        return CommonResult.failed(msg);
    }
}
