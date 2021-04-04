package com.hniecs.mainserver.entity.market;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author 陈桢梁
 * @desc MarketUserEntity.java
 * @date 2021-03-13 09:17
 * @logs[0] 2021-03-13 09:17 陈桢梁 创建了MarketUserEntity.java文件
 */
@Data
public class MarketUserEntity {

    private Long id;

    private Long dataId;

    private Long hncsId;

    @NotNull
    @Pattern(regexp = "([a-z]|[A-Z]|[0-9]|[@._-])*",message = "用户名格式有误")
    private String name;

    @NotNull
    @Pattern(regexp = "([a-z]|[A-Z]|[0-9]|[@._-])*",message = "用户名格式有误")
    private String password;

    private Date mtime;

    private Date ctime;

}
