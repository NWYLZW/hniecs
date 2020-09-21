package com.hniecs.mainserver.tool.excel.bill;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @desc    WechatBillExcel.java
 * @author  zerohua
 * @date    2020-09-18 12:18
 * @logs[0] 2020-09-18 12:18 zerohua 创建了WechatBillExcel.java文件
 * @logs[1] 2020-09-18 01:44 yijie 优化代码
 * @logs[2] 2020-09-20 13:18 yijie 基于继承实现
 */
@ExcelIgnoreUnannotated
public class WechatBillExcel extends BillExcel {

    /**
     * 商户单号
     */
    @ExcelProperty(index = 9)
    private String transactionNumber;

    /**
     * 金额(元)
     */
    @ExcelProperty(index = 5)
    private String money;

    /**
     * 收/支
     */
    @ExcelProperty(index = 4)
    private String type;

    public WechatBillExcel() {
        super.tag = "微信";
    }
}

