package com.hniecs.mainserver.tool.exceltool;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hniecs.mainserver.tool.exceltool.bill.BillExcel;
import com.hniecs.mainserver.tool.exceltool.bill.IBillExcel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc     ModelExcelListener.java
 * @author  zerohua
 * @date    2020-09-18 21:58
 * @logs[0] 2020-09-18 21:58 zerohua 创建了ModelExcelListener.java文件
 */
@Slf4j
public class ExcelListener extends AnalysisEventListener<IBillExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<IBillExcel> list = new ArrayList<>();

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(IBillExcel excel, AnalysisContext analysisContext) {
//        log.info("解析到一条数据{}",excel.verify());
        String data = excel.verify();
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
