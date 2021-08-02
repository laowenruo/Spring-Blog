<br>
预览地址：

前台：http://mashiro.org.cn

后台：http://admin.mashiro.org.cn 账号:Visitor密码:123456
<br>

## 技术栈

### 后端

- 核心框架：[Spring Boot](https://github.com/spring-projects/spring-boot)
- 安全框架：[Spring Security](https://github.com/spring-projects/spring-security)
- Token 认证：[jjwt](https://github.com/jwtk/jjwt)
- 持久层框架：[MyBatis](https://github.com/mybatis/spring-boot-starter)
- 分页插件：[PageHelper](https://github.com/pagehelper/Mybatis-PageHelper)
- NoSQL缓存：[Redis](https://github.com/redis/redis)
- Markdown 转 HTML：[commonmark-java](https://github.com/commonmark/commonmark-java)
- 离线 IP 地址库：[ip2region](https://github.com/lionsoul2014/ip2region)
- 定时任务：[quartz](https://github.com/quartz-scheduler/quartz)
- UserAgent 解析：[yauaa](https://github.com/nielsbasjes/yauaa)

邮件模板参考自[Typecho-CommentToMail-Template](https://github.com/MisakaTAT/Typecho-CommentToMail-Template)

### 前端

- 核心框架：Vue2.x、Vue Router、Vuex
- UI框架：Element-ui、Vuetify

前端技术主要在于运用。

## 数据库设计

### 数据表

- tb_blog (博客管理表)
- tb_blog_tags (博客与标签关联表)
- tb_comment (评论表)
- tb_tag (标签表)
- tb_category (类型表)
- tb_user (用户表，权限分为管理员权限和普通游客权限)
- tb_city_visitor (城市独立访问量表)
- tb_exception_log (异常日志表)
- tb_login_log (登录日志表)
- tb_operation_log (操作日志表)
- tb_schedule_job (定时任务表)
- tb_schedule_job_log (定时任务日志表)
- tb_site_setting (站点设置表)
- tb_visit_log (访问日志表)
- tb_visit_record (访问量记录表)
- tb_visitor (访客表)

## 快速开始

1. 创建 MySQL 数据库`blog_dev`，并执行`/blog-api/blog_dev.sql`初始化表数据
2. 修改配置信息`blog-api/src/main/resources/application-dev.properties`
3. 安装 Redis 并启动
4. 启动后端服务
5. 分别在`blog-cms`和`blog-view`目录下执行`npm install`安装依赖
6. 分别在`blog-cms`和`blog-view`目录下执行`npm run serve`启动前后台页面

从学习java开始，就一直想做一个属于自己的博客，并且可以发布到网上。现在基本上已经实现了自己
想要的功能，但是也有很多不好的地方。项目起初是在B站看教学视频做的，做到一大半的时候，发现所用
的技术不太符合目前的潮流，所以决定重构这个项目，并且尽量遵守阿里巴巴的开发规范。在重构项目结构的
过程中参考了很多大佬的博客，非常感谢大佬们开源自己的项目。
<br>
之后会对项目持续维护，并且适应新的技术。目前项目的权限管理还是比较简陋，网络安全考虑的也不多。
TODO more