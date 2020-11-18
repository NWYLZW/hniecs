package com.hniecs.mainserver.controller.admin;

import com.github.pagehelper.PageHelper;
import com.hniecs.mainserver.annotation.method.PermissionRequired;
import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.permission.AdminPermissions;
import com.hniecs.mainserver.exception.CommonExceptions;
import com.hniecs.mainserver.service.admin.InvitationCodeService;
import com.hniecs.mainserver.tool.api.CommonResult;
import com.hniecs.mainserver.tool.security.SessionTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
 * @logs[3] 2020-11-18 12:56 yijie 重构代码
 */
@RestController
@Slf4j
public class AdminInvitationCodeController {
    @Resource
    private InvitationCodeService invitationCodeService;

    /**
     * 根据json格式请求信息添加邀请码
     * @param invitationCodes   Array      Y   []  待添加的邀请码列表
     * @param tagName           String     Y   ""  标签名
     * @param availableCount    Integer    N   0   邀请码可用次数
     */
    @PermissionRequired(
        scope = AdminPermissions.NAME,
        permission = AdminPermissions.OPERATE_INVITATION_CODES
    )
    @PostMapping("/admin/invitationCode/list")
    public CommonResult<Object> addInvitationCode(
        @RequestParam(name = "tagName"        ) String tagName,
        @RequestParam(name = "availableCount" ) Integer availableCount,
        @RequestParam(name = "invitationCodes") List<String> invitationCodes
    ) {
        if (invitationCodes.isEmpty()) {
            return CommonResult.success(new HashMap<>(){{
                put("successCount", 0);
                put("failureCount", 0);
            }});
        }

        return CommonResult.success(invitationCodeService
            .addInvitationCodes(
                Objects.requireNonNull(SessionTool.curUser()), availableCount, tagName, invitationCodes
            )
        );
    }

    /**
     * 通过支付宝或微信导出账单文件 导入邀请码到数据库
     * @param excelFile         File       Y       表格二进制流文件
     * @param tagName           String     Y   ""  标签名
     * @param availableCount    Integer    N   0   邀请码可用次数
     * @param thresholdMoney    String     N   0   邀请码录入数据库阈值
     */
    @PermissionRequired(
        scope = AdminPermissions.NAME,
        permission = AdminPermissions.OPERATE_INVITATION_CODES
    )
    @PostMapping("/admin/invitationCode/importFromExcel")
    public CommonResult<Object> importInvitationCodes(
        @RequestParam(value = "excelFile",      required = true) MultipartFile excelFile,
        @RequestParam(value = "tagName",        required = true) String tagName,
        @RequestParam(value = "availableCount", required = false, defaultValue = "0") Integer availableCount,
        @RequestParam(value = "thresholdMoney", required = false, defaultValue = "30") String thresholdMoney
    ){
        if (Arrays.binarySearch((new String[]{"微信", "支付宝"}), tagName) == -1) {
            throw CommonExceptions.BAD_REQUEST.exception;
        }

        InputStream excelIS;
        try {
            excelIS = excelFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
        }

        return CommonResult.success(invitationCodeService
            .addInvitationCodes(
                SessionTool.curUser(), availableCount, tagName, thresholdMoney, excelIS
            )
        );
    }

    /**
     * 删除一个邀请码
     * @param id    Long    Y   0    邀请码id
     */
    @DeleteMapping("/admin/invitationCode/one")
    @PermissionRequired(
        scope = AdminPermissions.NAME,
        permission = AdminPermissions.OPERATE_INVITATION_CODES
    )
    public CommonResult<Object> falseDeleteById(
        @RequestParam(value = "id", required = true) Long id
    ) {
        if (id < 0) {
            throw CommonExceptions.BAD_REQUEST.exception;
        }
        invitationCodeService.deleteById(id);
        return CommonResult.success();
    }

    /**
     * 修改
     * 修改邀请码内容 禁用邀请码 添加标签 修改使用次数
     * @param id                    Long        Y   ""  邀请码id
     * @param invitationCode        String      N   ""  邀请码内容
     * @param tagName               String      N   ""  邀请码标签
     * @param availableInviteCount  Integer     N   0   邀请码可用次数
     * @param status                Integer     N   0   邀请码状态
     */
    @PermissionRequired(
        scope = AdminPermissions.NAME,
        permission = AdminPermissions.OPERATE_INVITATION_CODES
    )
    @PutMapping("/admin/invitationCode/one")
    public CommonResult<Object> updateInvitationCode(
        @RequestParam(name = "id"                  , required = true ) Long id,
        @RequestParam(name = "invitationCode"      , required = false) String invitationCode,
        @RequestParam(name = "tagName"             , required = false) String tagName,
        @RequestParam(name = "availableInviteCount", required = false) Integer availableInviteCount,
        @RequestParam(name = "status"              , required = false) Integer status
    ) {
        if (
            id < 0 || invitationCode.length() > 50 || availableInviteCount < 0 || status == -1
        ) {
            throw CommonExceptions.BAD_REQUEST.exception;
        }
        invitationCodeService.updateInvitationCode(new InvitationCodeEntity(){{
            this.setId(id);
            this.setInvitationCode(invitationCode);
            this.setTagName(tagName);
            this.setAvailableInviteCount(availableInviteCount);
            this.setStatus(status);
        }});
        return CommonResult.success();
    }

    /**
     * 获取邀请码列表
     * @bodyParam invitationCode    String  N   ""  邀请码内容
     * @bodyParam creatorName       String  N   ""  创建者姓名
     * @bodyParam tagName           String  N   ""  标签名
     * @bodyParam page              int     N   1   页码
     * @bodyParam size              int     N   20  每页个数
     */
    @PermissionRequired(
        scope = AdminPermissions.NAME,
        permission = AdminPermissions.SEARCH_INVITATION_CODES
    )
    @GetMapping("/admin/invitationCode/search")
    public CommonResult getInvitationCodesByCondition(
        @RequestParam(name = "invitationCode"   , required = false, defaultValue = "") String invitationCode,
        @RequestParam(name = "creatorName"      , required = false, defaultValue = "") String creatorName,
        @RequestParam(name = "tagName"          , required = false, defaultValue = "") String tagName,
        @RequestParam(name = "page"             , required = false, defaultValue = "1") Integer page,
        @RequestParam(name = "size"             , required = false, defaultValue = "20") Integer size
    ) {
        if (page <= 0 || size < 0) {
            throw CommonExceptions.BAD_REQUEST.exception;
        }
        if (size == 0) {
            return CommonResult.success(new ArrayList<>());
        }
        // 设置分页规则
        PageHelper.startPage(page, size);

        return CommonResult.success(
            invitationCodeService.getInvitationCodePage(
                invitationCode, creatorName, tagName
            )
        );
    }

    /**
     * 返回所有不为空不重复的tagName
     */
    @PermissionRequired(
        scope = AdminPermissions.NAME,
        permission = AdminPermissions.SEARCH_INVITATION_CODES
    )
    @GetMapping("/admin/invitationCode/tagNames")
    public CommonResult<Object> getTagName(){
        return CommonResult.success(
            invitationCodeService.geTagNameList()
        );
    }
}
