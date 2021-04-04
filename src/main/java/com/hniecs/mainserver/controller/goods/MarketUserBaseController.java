package com.hniecs.mainserver.controller.goods;

import com.hniecs.mainserver.annotation.method.NotNeedLogin;
import com.hniecs.mainserver.entity.market.MarketUserEntity;
import com.hniecs.mainserver.exception.CommonExceptions;
import com.hniecs.mainserver.service.market.MarketUserService;
import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author 陈桢梁
 * @desc MarketUserBaseController.java
 * @date 2021-03-13 09:14
 * @logs[0] 2021-03-13 09:14 陈桢梁 创建了MarketUserBaseController.java文件
 */
@RestController
@RequestMapping("/market/base")
public class MarketUserBaseController {

    @Resource
    MarketUserService marketUserService;

    @NotNeedLogin
    @PostMapping("/register")
    public CommonResult register(@Valid MarketUserEntity marketUserEntity, Errors errors) throws NoSuchMethodException {
        if (errors.hasErrors()){
            String errorMessage = "";
            for (ObjectError x : errors.getAllErrors()){
                errorMessage += x.getDefaultMessage()+",\n";
            }
            throw new RuntimeException("[500]{"+errorMessage+"}");
        }
        String msg = marketUserService.register(marketUserEntity);
        if(msg.equals("0")){
            return CommonResult.success(null,"注册成功");
        }
        throw CommonExceptions.INTERNAL_SERVER_ERROR.exception;
    }

}
