package com.hniecs.mainserver.tool.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hniecs.mainserver.tool.excel.bill.BillExcel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc    BillExcelListener.java
 * @author  zerohua
 * @date    2020-09-18 21:58
 * @logs[0] 2020-09-18 21:58 zerohua 创建了ModelExcelListener.java文件
 * @logs[1] 2020-09-18 01:44 yijie 优化代码
 */
@Slf4j
public class BillExcelListener extends AnalysisEventListener<BillExcel> {

    List<BillExcel> list = new ArrayList<>();

    /**
     * 每行数据的转化与获取
     * @param excel    表格实体
     * @param analysisContext
     */
    @Override
    public void invoke(BillExcel excel, AnalysisContext analysisContext) {
        String data = excel.verify();
        if(data != null) {
            list.add(excel);
        }
    }
    /**
     * 所有数据解析完了之后调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
