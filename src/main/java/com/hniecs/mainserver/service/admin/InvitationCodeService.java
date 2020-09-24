package com.hniecs.mainserver.service.admin;

import com.github.pagehelper.Page;
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
import java.math.BigDecimal;
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
    private BigDecimal targetMoney;

    @Resource
    private InvitationCodeModel invitationCodeModel;

    /**
     * 分页获得
     * 通过三个条件搜索验证码实体，如果传过来的Null，
     * 将之变成万能匹配，就能实现随便查
     * @param tagName
     * @param creatorName
     * @param invitationCode
     * @return 结果集合
     */
    public Page<InvitationCodeEntity> getInvitationCodePage(
        String tagName, String creatorName, String invitationCode) {
                if(tagName == null) {
                    tagName = "%";
                }else {
                    tagName = "%" + tagName + "%";
                }
                if(creatorName == null) {
                    creatorName = "%";
                }else {
                    creatorName = "%" + creatorName + "%";
                }
                if (invitationCode == null) {
                    invitationCode = "%";
                }else {
                    invitationCode = "%" + invitationCode + "%";
                }
        return (Page<InvitationCodeEntity>)
            invitationCodeModel.getInvitationCodeList(tagName,creatorName,invitationCode);
    }

    /**
     * 文以文件方式或者单手动添加形式最终都会调用这个方法
     * @param creator
     * @param availableCount
     * @param invitationCodes
     * @param tagName
     * @param returnData
     * @return
     */
    public String addInvitationCodes(
        UserEntity creator, int availableCount,
        List<String> invitationCodes,String tagName,
        Hashtable returnData) {

        return invitationCodeModel.addInvitationCodes(
            creator,availableCount,
            invitationCodes,
            tagName,
            returnData
        );
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
        Hashtable returnData,
        String targetMoney) {

        this.targetMoney = new BigDecimal(targetMoney);

        List<String> invitationCodeStr = getInvitationCodeStrList(excelIn,tagName);
        return this.addInvitationCodes(creator, availableCount,
            invitationCodeStr, tagName, returnData);
    }

    /**
     * 假删除
     * @param id
     * @return
     */
    public String falseDeleteById(Long id) {
        int result = invitationCodeModel.falseDeleteById(id);
        if(result == 0){
            return "操作失败!";
        }else {
            return "0";
        }
    }

    /**
     * 修改
     * @param invitationCode
     * @return
     */
    public String updateInvitationCode(InvitationCodeEntity invitationCode) {

        int result = invitationCodeModel.updateInvitationCode(invitationCode);
        if(result == 0 ){
            return "操作失败!";
        }else {
            return "0";
        }
    }

    /**
     * @param excelIn 文件输入流
     * @param tagName
     * @return 验证码集合
     */
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
            BigDecimal money = new BigDecimal( billExcel.getMoney());
            // 建议用compareTo进行比较，0 等于 1 大于 -1 小于
            if (  money.compareTo(targetMoney) == 0  ) {
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
