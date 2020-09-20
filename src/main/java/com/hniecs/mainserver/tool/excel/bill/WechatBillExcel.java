package com.hniecs.mainserver.tool.excel.bill;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @desc    WechatBillExcel.java
 * @author  zerohua
 * @date    2020-09-18 12:18
 * @logs[0] 2020-09-18 12:18 zerohua 创建了WechatBillExcel.java文件
 * @logs[1] 2020-09-18 01:44 yijie 优化代码
 * @logs[2] 2020-09-20 13:18 yijie 基于继承实现
 */
public class WechatBillExcel extends BillExcel {

    @ExcelProperty(value = "商户单号", index = 9)
    public String transactionNumber;

    @ExcelProperty(value = "收支金额(元)", index = 5)
    public String money;

    @ExcelProperty(value = "收支类型", index = 4)
    public String type;

    public final String tagName = "微信";

}

