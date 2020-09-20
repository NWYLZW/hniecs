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
    public String moneyMatchesStr;
    @Override
    public String verify() {
        if( money != null && money.matches(moneyMatchesStr)) {
            return money;
        }
        return null;
    }
}
