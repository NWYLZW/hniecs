package com.hniecs.mainserver.controller.goods;

import com.hniecs.mainserver.entity.market.GoodsEntity;
import com.hniecs.mainserver.exception.CommonExceptions;
import com.hniecs.mainserver.service.FileBaseService;
import com.hniecs.mainserver.service.market.GoodsBaseService;
import com.hniecs.mainserver.tool.api.CommonResult;
import com.hniecs.mainserver.tool.security.SessionTool;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * @author 陈桢梁
 * @desc GoodsBaseControl.java
 * @date 2020-11-21 12:25
 * @logs[0] 2020-11-21 12:25 陈桢梁 创建了GoodsBaseControl.java文件
 */
@RestController
@RequestMapping("/goods/base")
public class GoodsBaseControl {
    @Resource
    FileBaseService fileBaseService;
    @Resource
    GoodsBaseService goodsBaseService;
    /**
     * 添加商品
     * @param multipartFiles 图片集
     * @param goodsEntity 商品对象
     * @return
     */
    @PostMapping("/upload")
    public CommonResult upload(MultipartHttpServletRequest multipartFiles, GoodsEntity goodsEntity, Errors errors) {
        if (errors.hasErrors()) {
            String msg = "";
            for (ObjectError x : errors.getAllErrors()) {
                msg += x.getDefaultMessage() + "\n";
            }
            throw new RuntimeException("[500]{+" + msg + "}");
        }
        if (multipartFiles != null) {
            ArrayList<String> pathList = new ArrayList<>();
            String basePath = System.getProperty("user.dir") + "/workPlace/public/goods";
            String msg = fileBaseService.add(basePath, multipartFiles, pathList, SessionTool.curUser().getId(), true);
            String temp = "";
            if (!msg.equals("0")) {
                throw CommonExceptions.FILE_ERROR.exception;
            }
            for (String x : pathList) {
                temp += x + ";";
            }
            goodsEntity.setFileUrl(temp);
        } else {
            goodsEntity.setFileUrl(null);
        }
        goodsEntity.setUserId(SessionTool.curUser().getId());
        String msg = goodsBaseService.upload(goodsEntity);
        if (msg.equals("0")) {
            return CommonResult.success(null,"上传成功");
        }
        throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
    }
}
