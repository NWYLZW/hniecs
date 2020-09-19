package com.hniecs.mainserver.tool.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hniecs.mainserver.tool.excel.bill.BillExcel;
import com.hniecs.mainserver.tool.excel.bill.IBillExcel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc    BillExcelListener.java
 * @author  zerohua
 * @date    2020-09-18 21:58
 * @logs[0] 2020-09-18 21:58 zerohua 创建了ModelExcelListener.java文件
 */
@Slf4j
public class BillExcelListener extends AnalysisEventListener<IBillExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    List<IBillExcel> list = new ArrayList<>();

    @Override
    public void invoke(IBillExcel excel, AnalysisContext analysisContext) {
        //得到账单金额str
        String data = excel.verify();
        //正则，筛选脏数据
        if( data != null && data.matches("￥?\\d*\\.\\d*")) {
            list.add(excel);
        }
    }

    /**
     * 所有数据解析完了之后调用
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("解析完毕");
    }
}
