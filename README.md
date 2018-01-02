# NutzCodeInsight
在 Nutz Action Module 中点击 @Ok 前面的jsp图标即可快速打开或切换至已经打开的jsp文件  
  
####  完成
    - 支持自定义模版配置
#### 兼容
```java
  //模式1  默认 jsp模式
  @Ok("jsp:btl.demo.manager")
  //模式2  beetl模版 需要在配置界面添加配置
  @Ok("btl:btl.demo.manager")
  //模式3 （适用于改造后得视图返回器，我自己使用的） 
  @Ok("btl:WEB-INF/btl/demo/manager.html")
```
# 示例
![NutzCodeInsight](image/NutzCodeInsight.gif)
