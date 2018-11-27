# NutzCodeInsight
- 1、支持NutzBoot项目快速搭建
- 2、在 Nutz Action 中点击 @Ok 前面的模版图标即可快速打开或切换至已经打开的模版文件
- 3、支持以HTML、JSP等格式文件作为模版的框架资源文件的快速定位（支持动态配置）
- 4、Navigate菜单中增加查找@At映射地址快捷方式
- 5、Nutz web环境中支持折叠显示国际化配置文件变量值（快捷键：Alt++或Alt+-）
- 6、Beetl模版中也支持国际化配置文件折叠(${i18n("login.sucess")}或者${i18n("login.sucess","参数1","参数N")} )（快捷键：Alt++或Alt+-）
- 7、Nutz 支持折叠显示java类中注入配置文件变量值 @Inject("java:$conf.get('attach.savePath')")（快捷键：Alt++或Alt+-）
- 8、支持实体类中快速创建接口与实现类（快捷键：Alt+insert）

>idea插件仓库[https://plugins.jetbrains.com/plugin/10311-nutzcodeinsight](https://plugins.jetbrains.com/plugin/10311-nutzcodeinsight "真实项目")


#### 具体功能滑动至底看GIF图

- 1 在 Nutz Action 中点击 @Ok 前面的模版图标即可快速打开或切换至已经打开的模版文件
```java
  //模式1  jsp模版（默认支持）
  @Ok("jsp:btl.demo.manager")
  //模式2  beetl模版 （默认支持）
  @Ok("btl:btl.demo.manager")
  @Ok("beetl:btl.demo.manager")
  //模式3 （适用于改造后得视图返回器，我自己使用的） 
  @Ok("btl:WEB-INF/btl/demo/manager.html")
```
- 2 支持以HTML、JSP等格式文件作为模版的框架资源文件的快速定位（支持动态配置）
```jsp
 <link rel="stylesheet" href="${base}/static/plugins/bootstrap/css/bootstrap.min.css?_=${productVersion}">
 <script src="${base}/static/plugins/jquery/jQuery-2.1.4.min.js"></script>
```
#### 4 持以HTML、JSP等格式文件作为页面模版得资源文件的快速定位(将光标移至 "login.sucess" 中任意位置 使用快捷键(展开：ctrl+ 收起：ctrl-))
```java
    MvcI18n.message("login.sucess");
    MvcI18n.messageOrDefault("login.sucess","登录成功");
    MvcI18n.messageOrDefaultFormat("login.sucess","{0}帐号登录{1}","test","失败");//test帐号登录失败
    Mvcs.getMessage(Mvcs.getReq(),"login.sucess");
```
### 添加自定义配置
 - File >> Settings >> NutzCodeInsight
 - File >> Settings >> Other Settings >> NutzCodeInsight

# 安装后效果

![NutzCodeInsight](image/NutzCodeInsight.gif)

![NutzCodeInsight](image/ReferenceContributor.gif)

