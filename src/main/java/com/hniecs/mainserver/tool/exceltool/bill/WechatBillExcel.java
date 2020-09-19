package com.hniecs.mainserver.tool.exceltool.bill;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @desc     BillExcel.java
 * @author  zerohua
 * @date    2020-09-18 12:18
 * @logs[0] 2020-09-18 12:18 zerohua 创建了BillExcel.java文件
 */

@Data
public class WechatBillExcel implements  IBillExcel{

    @ExcelProperty(value = "商户单号", index = 9)
    private String transactionNumber;

    @ExcelProperty(value = "收支类型", index = 4)
    private String type;

    @ExcelProperty(value = "收支金额(元)", index = 5)
    private String money;

    @Override
    public String verify() {
        return money;
    }
}

