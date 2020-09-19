package com.hniecs.mainserver.tool.excel.bill;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * @desc     AlipayBillExcel.java
 * @author  zerohua
 * @date    2020-09-19 13:34
 * @logs[0] 2020-09-19 13:34 zerohua 创建了AlipayBillExcel.java文件
 */
@Data
public class AlipayBillExcel extends BillExcel implements IBillExcel {

    @ExcelProperty(value = "支付宝交易号", index = 4)
    private String transactionNumber;

    private final String TYPE = "收入";

    private final String TAG = "支付宝";

    @ExcelProperty(value = "订单金额(元)", index = 7)
    private String money;

    @Override
    public String verify() {
        return money;
    }
}
