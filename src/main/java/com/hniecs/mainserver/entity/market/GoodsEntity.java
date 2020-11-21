package com.hniecs.mainserver.entity.market;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

/**
 * @desc     GoodsEntity.java
 * @author  陈桢梁
 * @date    2020-11-20 16:57
 * @logs[0] 2020-11-20 16:57 陈桢梁 创建了GoodsEntity.java文件
 */
@Data
public class GoodsEntity {
    /**
     * 商品id
     */
    private Long id;

    /**
     * 商品预期价格
     */
    @NotNull(message = "预期报价不能为空")
    private Integer price;

    /**
     * 接受的降价
     */
    private Integer pricerRange;

    /**
     * 商品标题
     */
    @NotNull(message = "标题不能为空")
    private String title;
    /**
     * 商品介绍
     */
    private String introduce;
    /**
     * 发布者id
     */
    private Long userId;

    /**
     * 商品图片路径的数组
     * 因为sql数据类型的原因该属性每个图片的路径以;结尾
     */
    private String fileUrl;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     *最近修改时间
     */
    private Date mtime;

    /**
     * 添加url
     * @param url url地址
     */
    public void addFileUrl(String url){
        this.fileUrl += url+";";
    }

    /**
     * 添加url
     * @param urls url地址数组
     */
    public void addFileUrl(ArrayList<String> urls){
        for(String url:urls){
            this.fileUrl += url+";";
        }
    }

    @Override
    public String toString() {
        String result = "GoodsEntity{" +
            "id="+id;
        Field[] fields = getClass().getDeclaredFields();
        try {
            for (int i = 1; i < fields.length; i++) {
                result += "," + fields[i].getName() + "=" + fields[i].get(this);
            }
        }catch (IllegalAccessException e){
            throw  new IllegalAccessError("GoodsEntity反射调用错误");
        }
        result+="}";
        return result;
    }
}
