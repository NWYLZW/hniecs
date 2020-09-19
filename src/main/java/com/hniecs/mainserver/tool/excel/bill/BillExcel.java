package com.hniecs.mainserver.tool.excel.bill;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @desc     BillExcel.java
 * @author  zerohua
 * @date    2020-09-19 14:07
 * @logs[0] 2020-09-19 14:07 zerohua 创建了BillExcel.java文件
 */
@Data
public class BillExcel{
    private String transactionNumber;

    private String TYPE;

    private String TAG;

    private String money;

}
