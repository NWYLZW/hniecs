package com.hniecs.mainserver.tool.exceltool.read.impl;


import com.hniecs.mainserver.tool.exceltool.read.BaseReader;
import com.hniecs.mainserver.tool.exceltool.read.IExcelReader;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc     ExcelUtil.java
 * @author  zerohua
 * @date    2020-09-18 21:24
 * @logs[0] 2020-09-18 21:24 zerohua 创建了ExcelUtil.java文件
 */
public class ExcelXlsReader extends BaseReader implements IExcelReader {


    public ExcelXlsReader(String filePath, int sheetIndex, int[] indexList) {
        super(filePath, sheetIndex, indexList);
    }

    public ExcelXlsReader(String filePath) {
        super(filePath);
    }

    public ExcelXlsReader(InputStream in, int sheetIndex, int[] indexList) {
        super(in, sheetIndex, indexList);
    }

    public ExcelXlsReader(InputStream in) {
        super(in);
    }

    @Override
    public List<List<String>> getAllData() {
        // 获取文件路径和文件
        FileInputStream fis = null;
        // 将输入流转换为工作簿对象
        HSSFWorkbook workbook = null;

        List<List<String>> lists = new ArrayList<>();

        try {
            fis = new FileInputStream(filePath);
            workbook = new HSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 使用索引获取工作表
        HSSFSheet sheet = workbook.getSheetAt(0);

        for (Row cells : sheet) {
            List<String> list = new ArrayList<>();
            for (int index : indexList) {
                list.add(cells.getCell(index).getStringCellValue());
            }
            lists.add(list);
            list.clear();
        }
        return lists;
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
