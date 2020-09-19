package com.hniecs.mainserver.tool.exceltool.read;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @desc     IExcelReader.java
 * @author  zerohua
 * @date    2020-09-19 11:19
 * @logs[0] 2020-09-19 11:19 zerohua 创建了IExcelReader.java文件
 */
public interface IExcelReader {

    /**
     * 获取表格文件中的所有数据
     * @return 实体集合
     */
    List<List<String>> getAllData();

    /**
     * 获取某一行所有数据
     * @param rowIndex 行索引
     * @return 数据集合
     */
    List<T> getRowAt(int rowIndex);


    /**
     * 获取某一列所有数据
     * @param colIndex 列索引
     * @return 数据集合
     */
    List<T> getColAt(int colIndex);
}
