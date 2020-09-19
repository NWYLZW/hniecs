package com.hniecs.mainserver.tool.excel;


import com.alibaba.excel.EasyExcel;
import com.hniecs.mainserver.tool.excel.bill.IBillExcel;
import java.io.InputStream;
import java.util.List;

/**
 * @desc     Main.java
 * @author  zerohua
 * @date    2020-09-18 22:07
 * @logs[0] 2020-09-18 22:07 zerohua 创建了Main.java文件
 */
public class RaderUtil {

    public static List<IBillExcel> getBillList(InputStream in, Class clazz) {
        BillExcelListener listener = new BillExcelListener();
        EasyExcel.read(in, clazz, listener).sheet().doRead();
        List<IBillExcel> billExcels = listener.list;
        return billExcels;
    }

}
