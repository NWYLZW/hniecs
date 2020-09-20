package com.hniecs.mainserver.tool.excel.bill;

import lombok.Data;

/**
 * @desc    BillExcel.java
 * @author  yijie
 * @date    2020-09-20 13:09
 * @logs[0] 2020-09-20 13:09 yijie 创建了BillExcel.java文件
 */
@Data
public abstract class BillExcel implements IBillExcel {
    public String transactionNumber = null;
    public String money = null;
    public String type = null;
    public String tagName = null;

    @Override
    public String verify() {
        if( money != null && money.matches(IBillExcel.MONEY_MATCHES_STR)) {
            return money;
        }
        return null;
    }
}
