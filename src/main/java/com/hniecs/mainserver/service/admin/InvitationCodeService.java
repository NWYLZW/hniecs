package com.hniecs.mainserver.service.admin;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.UserEntity;
import com.hniecs.mainserver.model.InvitationCodeModel;
import com.hniecs.mainserver.tool.excel.ExcelRader;
import com.hniecs.mainserver.tool.excel.bill.AlipayBillExcel;
import com.hniecs.mainserver.tool.excel.bill.BillExcel;
import com.hniecs.mainserver.tool.excel.bill.WechatBillExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @desc    InvitationCodeService.java
 * @author  yijie
 * @date    2020-09-20 12:52
 * @logs[0] 2020-09-20 12:52 yijie 创建了InvitationCodeService.java文件
 */
@Slf4j
@Service
public class InvitationCodeService {
    private final int INVITATIONCODE_THRESHOLD_MONEY = 30;
    @Resource
    InvitationCodeModel invitationCodeModel;

    public String addInvitationCodes(
        UserEntity creater, int availableCount, String tagName, ArrayList<String> invitationCodes,
        Hashtable returnData
    ) {
        // TODO lh 校验 invitationcodes 去除重复盛据
        // TODO lh 将添加成功的邀请将以数组的格式适回
        returnData.put("successCount",0);
        returnData.put("count",0);
        returnData.put("successAddList",new ArrayList<InvitationCodeEntity>());
        String msg = invitationCodeModel.addInvitationCodes(
            creater,availableCount,
            // TODO lh 将 tagName 存入数据库
            invitationCodes
        );
        return msg;
    }

    public String addInvitationCodes(
        UserEntity creater, int availableCount, String tagName, InputStream ExcelIS,
        Hashtable returnData
    ) {
        ArrayList<String> invitationCodes = new ArrayList<>();
        try {
            ArrayList<BillExcel> billExcels =
                (ArrayList<BillExcel>) new ExcelRader<BillExcel>()
                    .getBillList(ExcelIS, () -> {
                        if (tagName.equals("ali")) {
                            return AlipayBillExcel.class;
                        } else if (tagName.equals("wechat")) {
                            return WechatBillExcel.class;
                        }
                        return null;
                    });
        } catch (NullPointerException | ClassNotFoundException e) {
            log.error(String.valueOf(e));
            return "服务器错误";
        }
        // TODO lh 将金额等于 INVITATIONCODE_THRESHOLD_MONEY 存入 invitationCodes 数组中
        return this.addInvitationCodes(creater, availableCount, tagName, invitationCodes, returnData);
    }
}
