package com.hniecs.mainserver.service.admin;

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
import java.util.List;

/**
 * @desc    InvitationCodeService.java
 * @author  yijie
 * @date    2020-09-20 12:52
 * @logs[0] 2020-09-20 12:52 yijie 创建了InvitationCodeService.java文件
 */
@Slf4j
@Service
public class InvitationCodeService {

    /**
     * 金额有效阈值
     */
    private final int INVITATIONCODE_THRESHOLD_MONEY = 30;

    @Resource
    private InvitationCodeModel invitationCodeModel;

    public String addInvitationCodes(
        UserEntity creator, int availableCount,
        List<String> invitationCodes,String tagName,
        Hashtable returnData) {

        String msg = invitationCodeModel.addInvitationCodes(
            creator,availableCount,
            invitationCodes,
            tagName,
            returnData
        );
        return msg;
    }

    /**
     * @param creator          创建者
     * @param availableCount   可利用次数
     * @param tagName          支付类型
     * @param returnData
     * @return
     */
    public String addInvitationCodes(
        UserEntity creator, int availableCount, String tagName,
        InputStream excelIn,
        Hashtable returnData) {

        List<String> invitationCodeStr = getInvitationCodeStrList(excelIn,tagName);
        return this.addInvitationCodes(creator, availableCount,
            invitationCodeStr, tagName, returnData);

    }

    private List<String> getInvitationCodeStrList(InputStream excelIn,String tagName) {
        List<BillExcel> billExcels = getBillExcel(excelIn, tagName);
        return checkAndTransToList(billExcels);
    }

    /**
     * 除去集合中不满足条件的实体
     * @param billExcels
     * @return 返回验证码集合
     */
    private List<String> checkAndTransToList(List<BillExcel> billExcels) {
        List<String> list = new ArrayList<>();
        for (BillExcel billExcel : billExcels) {
            //字符变成钱
            String moneyStr = billExcel.getMoney();
            Long money = Long.valueOf(moneyStr);
            if (money == INVITATIONCODE_THRESHOLD_MONEY) {
                list.add(billExcel.getTransactionNumber());
            }
        }
        return list;
    }

    /**
     * @param in        表格流
     * @param tagName   支付方式
     * @return          账单集合
     */
    private List<BillExcel> getBillExcel(InputStream in,String tagName) {

        List<BillExcel> billExcels = null;
        ExcelRader<BillExcel> rader = new ExcelRader<>();
        try {
            billExcels = rader.getBillList(in,() -> {
                if(tagName.equals("微信")) {
                    return WechatBillExcel.class;
                }else if(tagName.equals("支付宝")) {
                    return AlipayBillExcel.class;
                }
                return null;
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return billExcels;
    }
}
