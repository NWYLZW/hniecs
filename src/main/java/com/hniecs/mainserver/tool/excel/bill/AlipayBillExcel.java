package com.hniecs.mainserver.tool.excel.bill;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @desc    AlipayBillExcel.java
 * @author  zerohua
 * @date    2020-09-19 13:34
 * @logs[0] 2020-09-19 13:34 zerohua 创建了AlipayBillExcel.java文件
 * @logs[1] 2020-09-18 01:44 yijie 优化代码
 * @logs[2] 2020-09-20 13:18 yijie 基于继承实现
 */
@ExcelIgnoreUnannotated
public class AlipayBillExcel extends BillExcel {

    /**
     * 支付宝交易号
     */
    @ExcelProperty(index = 4)
    private String transactionNumber;

    /**
     * 订单金额(元)
     */
    @ExcelProperty(index = 6)
    private String money;

    /**
     * 收/支
     */
    private String type;

    public AlipayBillExcel() {
        super.tag = "支付宝";
    }
}
