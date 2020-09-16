package com.hniecs.mainserver.controller.setting;

import com.hniecs.mainserver.tool.api.CommonResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @desc      主页配置c层 SettingHomeController.java
 * @author    yijie
 * @date      2020-09-16 01:34
 * @logs[0]   2020-09-16 01:34 yijie 创建了文件SettingHomeController.java
 */
public class SettingHomeController {
    /**
     * 轮播图格式
     *  "title":             "轮播图的标题",
     *  "message":           "轮播图的展示信息",
     *  "url":               "点击轮播图的标题与展示信息跳转到的页面",
     *  "backgroundImage":   "背景图url"
     * ------------------------------------------------------------------
     * 服务与网站的格式
     *  "title":    "服务应用图标下标题1",
     *  "icoUrl":   "服务应用的图标",
     *  "url":      "点击服务应用跳转到的页面",
     *  "message":  "鼠标在服务应用上方时展示的提示信息",
     *  is里面的成员全部使用bool值
     *  "is": {
     *      "build":        "是否在构建",
     *      "ban":          "是否被禁止跳转",
     *      "toNewPage":    "是否前往新页面"
     *  }
     * ------------------------------------------------------------------
     */
    /**
     * TODO 获取主页的轮播图列表
     */
    @GetMapping("/setting/home/carousels")
    public CommonResult getCarousels() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 添加一个主页的轮播图字典
     */
    @PostMapping("/setting/home/carousel")
    public CommonResult addCarousel() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 删除一个主页的轮播图字典
     */
    @DeleteMapping("/setting/home/carousel")
    public CommonResult deleteCarousel() {
        return CommonResult.notFound("接口未完成");
    }
    /**
     * TODO 修改一个主页的轮播图内容
     */
    @PutMapping("/setting/home/carousel")
    public CommonResult updateCarousel() {
        return CommonResult.notFound("接口未完成");
    }

    /**
     * TODO 参照上面对 服务与网站列表 进行增删改查
     * 服务与网站使用同一表，使用一个type作为区分
     */
    @GetMapping("/setting/home/services")
    public CommonResult getServices() {
        return CommonResult.notFound("接口未完成");
    }
}
