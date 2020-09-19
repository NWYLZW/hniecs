package com.hniecs.mainserver.tool.excel;

import com.alibaba.excel.EasyExcel;
import com.hniecs.mainserver.tool.excel.bill.IBillExcel;

import java.io.InputStream;
import java.util.List;

/**
 * @desc    RaderUtil.java
 * @author  zerohua
 * @date    2020-09-18 22:07
 * @logs[0] 2020-09-18 22:07 zerohua 创建了RaderUtil.java文件
 */
public class RaderUtil<T extends IBillExcel> {
    public List<T> getBillList(InputStream in, Class<T> clazz) {
        BillExcelListener listener = new BillExcelListener();
        EasyExcel.read(in, clazz, listener).sheet().doRead();
        List<T> billExcels = (List<T>) listener.list;
        return billExcels;
    }
}
