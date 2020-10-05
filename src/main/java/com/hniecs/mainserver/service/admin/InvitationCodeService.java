package com.hniecs.mainserver.service.admin;

import com.hniecs.mainserver.entity.InvitationCodeEntity;
import com.hniecs.mainserver.entity.user.UserEntity;
import com.hniecs.mainserver.model.InvitationCodeModel;
import com.hniecs.mainserver.model.UserModel;
import com.hniecs.mainserver.tool.CommonUseStrings;
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
import java.util.HashMap;
import java.util.List;

/**
 * @desc    InvitationCodeService.java
 * @author  yijie
 * @date    2020-09-20 12:52
 * @logs[0] 2020-09-20 12:52 yijie 创建了InvitationCodeService.java文件
 * @logs[1] 2020-09-23 03:00 yijie 重构大部分代码
 */
@Slf4j
@Service
public class InvitationCodeService {
    @Resource
    private InvitationCodeModel invitationCodeModel;
    @Resource
    private UserModel userModel;

    /**
     * 删选列表出满足条件实体列表
     * @param billExcels            账单excel表格元数据列表
     * @param effectiveThreshold    金额有效阈值
     * @return 返回邀请码实体列表
     */
    private List<String> filterBillExcelData(List<BillExcel> billExcels, BigDecimal effectiveThreshold) {
        List<String> list = new ArrayList<>();
        for (BillExcel billExcel : billExcels) {
            // 字符变成钱
            BigDecimal money = new BigDecimal(billExcel.getMoney());
            // 建议用compareTo进行比较，0 等于 1 大于 -1 小于
            if (money.compareTo(effectiveThreshold) == 0) {
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
    private List<BillExcel> getBillExcels(InputStream in, String tagName) throws ClassNotFoundException, NullPointerException {
        List<BillExcel> billExcels = new ArrayList<>();
        billExcels = new ExcelRader<BillExcel>()
            .getBillList(in, () -> {
                // TODO 做成常量维护
                if(tagName.equals("微信")) {
                    return WechatBillExcel.class;
                }else if(tagName.equals("支付宝")) {
                    return AlipayBillExcel.class;
                }
                return null;
            });
        return billExcels;
    }

    /**
     * 分页获得
     * 通过三个条件搜索邀请码实体，如果传过来的Null，
     * 将之变成万能匹配，就能实现随便查
     * @param tagName           标签名
     * @param creatorName       创建者用户名
     * @param invitationCode    邀请码内容
     * @param returnData        返回数据
     * @return 邀请码实体列表
     */
    public String getInvitationCodePage(
        String invitationCode, String creatorName, String tagName,
        List<InvitationCodeEntity> returnData
    ) {
        // TODO 优化查找效率
        //  如果三个关键词串都为空串时调用获取全部的接口
        //  判断标签名是否存在
        //  判断创建者用户名是否存在
        return invitationCodeModel
            .getInvitationCodeList(
                creatorName, tagName, invitationCode
                , returnData
            );
    }

    /**
     * 通过邀请码字符串列表将邀请码添加至服务器
     * @param creator           创建者实体
     * @param availableCount    可用次数
     * @param invitationCodes   邀请码字符串列表
     * @param tagName           标签名
     * @param returnData        返回数据
     */
    public String addInvitationCodes(
        UserEntity creator, int availableCount, String tagName,
        List<String> invitationCodes,
        HashMap returnData
    ) {
        if (!userModel.have(creator.getUserName())) {
            return "用户不存在";
        }
        List<String> newInvitationCodes = new ArrayList<>();
        for (String invitationCode : invitationCodes) {
            if (!invitationCode.equals("")) {
                newInvitationCodes.add(invitationCode);
            }
        }
        return invitationCodeModel.addInvitationCodes(
            creator, availableCount, tagName,
            newInvitationCodes,
            returnData
        );
    }

    /**
     * 通过excel文件流添加数据到数据库
     * @param creator           创建者实体信息
     * @param availableCount    可利用次数
     * @param tagName           支付类型名
     * @param thresholdMoney       计入数据库信息阈值金钱数
     * @param returnData        返回数据
     */
    public String addInvitationCodes(
        UserEntity creator, int availableCount, String tagName, String thresholdMoney,
        InputStream excelIn,
        HashMap returnData
    ) {

        try {
            List<String> invitationCodeStrs = filterBillExcelData(
                getBillExcels(excelIn, tagName), new BigDecimal(thresholdMoney)
            );

            if (invitationCodeStrs.isEmpty()) {
                returnData.put("successCount", 0);
                returnData.put("failureCount", 0);
                return "0";
            }
            return this.addInvitationCodes(
                creator, availableCount, tagName, invitationCodeStrs
                , returnData
            );
        } catch (NullPointerException e) {
            log.error("不能解析该标签数据", e);
            return CommonUseStrings.SERVER_FAILED.S;
        } catch (ClassNotFoundException e) {
            log.error("编码错误，getBillExcels{ExcelRader.getBillList 返回值未继承BillExcel类}", e);
            return CommonUseStrings.SERVER_FAILED.S;
        }
    }

    /**
     * 删除一个邀请码
     * @param id    邀请码id
     */
    public String deleteById(Long id) {
        if(!invitationCodeModel.have(id)){
            return "邀请码不存在";
        }
        return invitationCodeModel.deleteById(id);
    }

    /**
     * 修改邀请码信息
     * @param invitationCode    邀请码实体
     */
    public String updateInvitationCode(InvitationCodeEntity invitationCode) {
        Long id = invitationCode.getId();
        if (id == null || !invitationCodeModel.have(id)){
            return "邀请码不存在";
        }
        return invitationCodeModel.updateInvitationCode(invitationCode);
    }

    /**
     * 获取invitationCode表中所有的tagName 并加上全部
     * @param tagNameList tagName数组
     */
    public String geTagNameList(List<String> tagNameList){
        tagNameList.add("全部");
        return invitationCodeModel.getTagName(tagNameList);
    }
}
