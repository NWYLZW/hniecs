package com.hniecs.mainserver.tool.excel.bill;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @desc    AlipayBillExcel.java
 * @author  zerohua
 * @date    2020-09-19 13:34
 * @logs[0] 2020-09-19 13:34 zerohua 创建了AlipayBillExcel.java文件
 * @logs[1] 2020-09-18 01:44 yijie 优化代码
 * @logs[2] 2020-09-20 13:18 yijie 基于继承实现
 */
public class AlipayBillExcel extends BillExcel {
    @ExcelProperty(value = "支付宝交易号", index = 4)
    public String transactionNumber;
    @ExcelProperty(value = "订单金额(元)", index = 7)
    public String money;

    public String type = "收入";
    public String tagName = "支付宝";

    public String moneyMatchesStr = "\\d*\\.\\d*";
}
