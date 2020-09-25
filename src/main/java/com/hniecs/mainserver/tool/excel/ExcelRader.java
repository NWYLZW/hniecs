package com.hniecs.mainserver.tool.excel;

import com.alibaba.excel.EasyExcel;
import com.hniecs.mainserver.tool.excel.bill.BillExcel;

import java.io.InputStream;
import java.util.List;
import java.util.function.Supplier;

/**
 * @desc    excel文件读取帮助类 ExcelRader.java
 * @author  zerohua
 * @date    2020-09-18 22:07
 * @logs[0] 2020-09-18 22:07 zerohua 创建了ExcelRader.java文件
 * @logs[1] 2020-09-20 03:13 yijie 添加注释
 * @logs[2] 2020-09-20 13:46 yijie 使用lambda进行控制
 */
public class ExcelRader<T> {
    /**
     * 获取账单数据列表
     * @param in            账单xlsx格式的excel文件二进制流
     * @param classSupplier 无参构造，返回一个一个继承了BillExcel的类
     * @return  账单实体列表
     */
    public List<T> getBillList(InputStream in, Supplier<Class> classSupplier)
        throws ClassNotFoundException, NullPointerException {
        BillExcelListener listener = new BillExcelListener();
        Class billExcelClass = classSupplier.get();
        if (billExcelClass == null) {
            throw new NullPointerException("classSupplier 返回值为空");
        }
        if (
            !BillExcel.class
                .isAssignableFrom(billExcelClass)
        ) {
            throw new ClassNotFoundException("classSupplier 返回值未继承 BillExcel.class抽象类");
        }
        EasyExcel.read(in, billExcelClass, listener).sheet().doRead();
        List<T> billExcels = (List<T>) listener.list;
        return billExcels;
    }
}
