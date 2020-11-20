package com.hniecs.mainserver.entity.user;

import lombok.Data;

/**
 * @desc     UserMarketEntity.java
 * @author  陈桢梁
 * @date    2020-11-20 16:37
 * @logs[0] 2020-11-20 16:37 陈桢梁 创建了UserMarketEntity.java文件
 */
@Data
public class UserMarketEntity {
    /**
     * id
     */
    private long id;

    /**
     * 身份证号
     */
    private String idNumber;

}
