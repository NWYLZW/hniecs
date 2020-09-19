package com.hniecs.mainserver.tool.excel.bill;
/**
 * @desc    IBillExcel.java
 * @author  zerohua
 * @date    2020-09-19 14:52
 * @logs[0] 2020-09-19 14:52 zerohua 创建了IBillExcel.java文件
 * @logs[1] 2020-09-18 01:44 yijie 优化代码
 */
public interface IBillExcel {
    String transactionNumber = null;
    String money = null;
    String type = null;
    String tagName = null;

    /**
     * 校验金额类型
     * @return
     */
    String verify();
}
