package com.hniecs.mainserver.tool.excel;

import com.alibaba.excel.EasyExcel;
import com.hniecs.mainserver.tool.excel.bill.IBillExcel;

import java.io.InputStream;
import java.util.List;

/**
 * @desc    excel文件读取帮助类 ExcelRaderUtil.java
 * @author  zerohua
 * @date    2020-09-18 22:07
 * @logs[0] 2020-09-18 22:07 zerohua 创建了ExcelRaderUtil.java文件
 * @logs[1] 2020-09-20 03:13 yijie 添加注释
 */
public class ExcelRaderUtil<T extends IBillExcel> {
    /**
     * 获取账单数据列表
     * @param in    账单xlsx格式的excel文件二进制流
     * @param clazz 文件类型 AlipayExcel.class|WechatBillExcel.class
     * @return  账单实体列表
     */
    public List<T> getBillList(InputStream in, Class<T> clazz) {
        BillExcelListener listener = new BillExcelListener();
        EasyExcel.read(in, clazz, listener).sheet().doRead();
        List<T> billExcels = (List<T>) listener.list;
        return billExcels;
    }
}
