package com.hniecs.mainserver.entity.market;

import lombok.Data;

import java.util.Date;

/**
 * @author 陈桢梁
 * @desc MarketUserDetailEntity.java
 * @date 2021-03-13 15:41
 * @logs[0] 2021-03-13 15:41 陈桢梁 创建了MarketUserDetailEntity.java文件
 */
@Data
public class MarketUserDetailEntity {

    private Long id;

    private Long headPortrait;
    /**
     * 个人简介
     */
    private String biography;

    private String wechat;

    private String qq;

    private String number;

    private Date ctime;

    private Date mtime;
}
