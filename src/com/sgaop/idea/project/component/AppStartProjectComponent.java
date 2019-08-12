package com.sgaop.idea.project.component;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.components.ProjectComponent;

import java.awt.*;

/**
 * @author huchuc@vip.qq.com
 * @date: 2019/5/24
 */
public class AppStartProjectComponent implements ProjectComponent {

    final static int THRESHOLD = 2;

    static int count = 0;

    @Override
    public void projectOpened() {
        if (count % THRESHOLD == 0) {
            Notification notification = new Notification("NutzCodeInsight", "致开发者", "你好，欢迎捐赠或给我的开源项目NutzFw加一颗星(star)进行鼓励。谢谢！NutzFw是Java开源企业级快速开发框架、后台管理系统，拥有完善的权限控制、代码生成器、自定义表单、动态数据库、灵活的工作流、手机APP客户端、支持前后端分离开发。", NotificationType.INFORMATION);
            notification.addAction(this.createDesktopNotification("捐赠作者", "https://gitee.com/threefish/NutzCodeInsight/#donation"));
            notification.addAction(this.createDesktopNotification("NutzFw后台管理系统", "https://gitee.com/threefish/NutzFw"));
            Notifications.Bus.notify(notification);
        }
        count++;
    }

    public NotificationAction createDesktopNotification(String buttonName, String url) {
        return NotificationAction.createSimple(buttonName, () -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI(url));
            } catch (Exception e1) {
            }
        });
    }

}
