package com.hniecs.mainserver.tool.exceltool;



import com.alibaba.excel.EasyExcel;
import com.hniecs.mainserver.tool.exceltool.bill.AlipayBillExcel;
import com.hniecs.mainserver.tool.exceltool.bill.BillExcel;
import com.hniecs.mainserver.tool.exceltool.bill.IBillExcel;
import com.hniecs.mainserver.tool.exceltool.bill.WechatBillExcel;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @desc     Main.java
 * @author  zerohua
 * @date    2020-09-18 22:07
 * @logs[0] 2020-09-18 22:07 zerohua 创建了Main.java文件
 */
public class Main {

    public static void main(String[] args) {

//        testXlsx();
        testXls();
    }

    public static void testXlsx() {

        String filePath = "C:\\Users\\linghua\\Desktop\\wechatbill.xls";
        ExcelListener listener = new ExcelListener();
        EasyExcel.read(filePath, WechatBillExcel.class, listener).sheet().doRead();
        List<IBillExcel> billExcels = listener.list;
        System.out.println(billExcels);
        for (IBillExcel billExcel : billExcels) {
            System.out.println(billExcel+"fffffffffffff");
        }
    }

    public static void testXls() {

        String filePath = "C:\\Users\\linghua\\Desktop\\dd.xls";
        ExcelListener listener = new ExcelListener();
        EasyExcel.read(filePath, AlipayBillExcel.class, listener).sheet().doRead();
        List<IBillExcel> billExcels = listener.list;
        System.out.println(billExcels+"========");
        for (IBillExcel billExcel : billExcels) {
            System.out.println(billExcel+"fffffffffffff");
        }
    }
}
