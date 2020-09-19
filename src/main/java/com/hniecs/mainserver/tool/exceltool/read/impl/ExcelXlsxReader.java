package com.hniecs.mainserver.tool.exceltool.read.impl;

import com.hniecs.mainserver.tool.exceltool.read.BaseReader;
import com.hniecs.mainserver.tool.exceltool.read.IExcelReader;
import org.apache.poi.ss.formula.functions.T;

import java.io.InputStream;
import java.util.List;

/**
 * @desc     用来读取.xlsx后缀的的表格
 * @author  zerohua
 * @date    2020-09-18 21:54
 * @logs[0] 2020-09-18 21:54 zerohua 创建了ExcelXlsxReader.java文件
 */
public class ExcelXlsxReader extends BaseReader implements IExcelReader {


    public ExcelXlsxReader(String filePath, int sheetIndex, int[] indexList) {
        super(filePath, sheetIndex, indexList);
    }

    public ExcelXlsxReader(String filePath) {
        super(filePath);
    }

    public ExcelXlsxReader(InputStream in, int sheetIndex, int[] indexList) {
        super(in, sheetIndex, indexList);
    }

    public ExcelXlsxReader(InputStream in) {
        super(in);
    }

    @Override
    public List<List<String>> getAllData() {
        return null;
    }

    @Override
    public List<T> getRowAt(int rowIndex) {
        return null;
    }

    @Override
    public List<T> getColAt(int colIndex) {
        return null;
    }
}
