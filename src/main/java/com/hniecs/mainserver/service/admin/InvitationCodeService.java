package com.hniecs.mainserver.service.admin;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.UserEntity;
import com.hniecs.mainserver.model.InvitationCodeModel;
import com.hniecs.mainserver.tool.excel.ExcelRaderUtil;
import com.hniecs.mainserver.tool.excel.bill.AlipayBillExcel;
import com.hniecs.mainserver.tool.excel.bill.WechatBillExcel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @desc    InvitationCodeService.java
 * @author  zerohua
 * @date    2020-09-17 20:18
 * @logs[0] 2020-09-17 20:18 zerohua 创建了InvitationCodeService.java文件
 * @logs[1] 2020-09-20 03:53 yijie 完善了addInvitationCodes方法
 */
@Service
public class InvitationCodeService {
    /**
     * 邀请码门槛金额
     */
    private final int invitationCodeThresholdMoney = 30;
    @Resource
    InvitationCodeModel invitationCodeModel;

    /**
     * 将邀请码列表添加到数据库
     * @param creater           创建用户
     * @param availableCount    可用次数
     * @param tagName           标签
     * @param invitationCodes   邀请码列表
     */
    public String addInvitationCodes(
        UserEntity creater, int availableCount, String tagName, ArrayList<String> invitationCodes,
        Hashtable returnData
    ) {
        // TODO lh 校验 invitationCodes 去除重复数据
        // TODO lh 将新添加上的邀请码以数组的格式返回
        returnData.put("successCount", 0);
        returnData.put("count", 0);
        returnData.put("successAddList", new ArrayList<InvitationCodeEntity>());
        String msg = invitationCodeModel.addInvitationCodes(
            creater,
            availableCount,
            // TODO lh 将 tagName 存入数据库
            invitationCodes
        );
        return msg;
    }

    /**
     * 将邀请码列表添加到数据库
     * @param creater           创建用户
     * @param availableCount    可用次数
     * @param tagName           标签
     * @param excelIS           excel InputStream文件流
     */
    public String addInvitationCodes(
        UserEntity creater, int availableCount, String tagName, InputStream excelIS,
        Hashtable returnData
    ) {
        ArrayList<String> invitationCodes = new ArrayList<>();
        if (tagName == "ali") {
            ArrayList<AlipayBillExcel> alipayBillExcels =
                (ArrayList<AlipayBillExcel>) new ExcelRaderUtil<AlipayBillExcel>()
                    .getBillList(excelIS, AlipayBillExcel.class);
            // TODO lh 将金额等于 invitationCodeThresholdMoney 的加入 invitationCodes数组中
        } else if (tagName == "wechat") {
            ArrayList<WechatBillExcel> wechatBillExcels =
                (ArrayList<WechatBillExcel>) new ExcelRaderUtil<WechatBillExcel>()
                    .getBillList(excelIS, WechatBillExcel.class);
            // TODO lh 将金额等于 invitationCodeThresholdMoney 的加入 invitationCodes数组中
        }
        return this.addInvitationCodes(creater, availableCount, tagName, invitationCodes, returnData);
    }
}
