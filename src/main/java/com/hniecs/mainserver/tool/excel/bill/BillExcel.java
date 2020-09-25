package com.hniecs.mainserver.tool.excel.bill;

import lombok.Data;

/**
 * @desc    BillExcel.java
 * @author  yijie
 * @date    2020-09-20 13:09
 * @logs[0] 2020-09-20 13:09 yijie 创建了BillExcel.java文件
 */
@Data
public class BillExcel{

    private String transactionNumber;

    private String money;

    private String type = "收入";

    public String tag;

    private static final String MONEY_MATCHES_STR = "\\d*\\.?\\d*";

    public String verify() {
        if( money != null && money.matches(MONEY_MATCHES_STR)) {
            return money;
        }
        return null;
    }
}
