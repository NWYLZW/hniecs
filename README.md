# 湖南工程学院计算机协会主体后台程序

## 开发目标

* [ ] `Git Submodule|Git SubTree`集成前端项目至后端主项目，方便一次性部署

* [ ] `docker`容器技术实现半自动化部署

* [ ] `CI\CD`实现全自动化部署

* [ ] `k8s`实现负载均衡

## 入口(登陆、注册、about)

* 登陆界面目前无验证码校验直接登陆 后续增加验证码校验
* 注册需要用户填报的信息
  * 登陆用户名、密码
  * 邀请码
    - 由管理人员通过导出支付宝或微信收款记录文件提交至服务器
    - 服务器处理好，将其录入数据库，作为唯一的邀请识别码
  * 昵称、姓名、专业、班级、学号、qq、电话、注册使用的邀请码
    - 姓名 真名，需要录入学校数据库的
    - qq 必填，管理员由用户填写的qq拉人进会员群
* 关于界面
  * 协会的信息介绍
  * 协会的作用

## 网站功能
* home页面
  * 顶部轮播图
  * 服务网站，友情链接
  * 站内文章
  * 个人活跃信息
    - 获取当前年度的活跃信息
    - 最近活跃日志的一个记录
    - 活跃规则 登陆次数、发帖与求助、访问某些服务与友链、写博客
  * 站内消息 (消息中心的作用)
    - 网站公告信息，个人用户信息，好友用户信息
* resource页面
  * 顶部火热资源轮播信息
  * 根据分类与关键字获取资源列表
* mall页面
  * 二手物品贩卖平台
  * 使用站内的积分兑换奖品
    + 积分的类型与机制
      - 待完善机制
* code页面
  * 线上运行代码
  * ac平台
