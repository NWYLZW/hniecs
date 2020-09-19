package com.hniecs.mainserver.tool.exceltool.read;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.io.InputStream;

/**
 * @desc    BaseReader.java
 * @author  zerohua
 * @date    2020-09-19 11:34
 * @logs[0] 2020-09-19 11:34 zerohua 创建了BaseReader.java文件
 */
public class BaseReader {

    /**
     * 表格文件位置
     */
    protected String filePath;

    /**
     * 文件输入流
     */
    protected InputStream in;

    /**
     * 所需表格索引，默认为0
     */
    protected int sheetIndex;

    /**
     * 所需要的的列的索引
     */
    protected int[] indexList;

    public BaseReader(String filePath, int sheetIndex, int[] indexList) {
        this.filePath = filePath;
        this.sheetIndex = sheetIndex;
        this.indexList = indexList;
    }

    public BaseReader(String filePath) {
        this.filePath = filePath;
    }

    public BaseReader(InputStream in, int sheetIndex, int[] indexList) {
        this.in = in;
        this.sheetIndex = sheetIndex;
        this.indexList = indexList;
    }

    public BaseReader(InputStream in) {
        this.in = in;
    }
}
