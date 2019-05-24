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

    @Override
    public void projectOpened() {
        Notification notification = new Notification("NutzCodeInsight", "致开发者", "你好，如果觉得NutzCodeInsight工具好用，开发效率提高了，欢迎捐赠，以资鼓励，我会增加更多强大的功能哦。", NotificationType.INFORMATION);
        notification.addAction(NotificationAction.createSimple("提点建议", () -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI("https://github.com/threefish/NutzCodeInsight/issues"));
            } catch (Exception e1) {
            }
        }));
        notification.addAction(NotificationAction.createSimple("我要捐赠", () -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI("https://github.com/threefish/NutzCodeInsight#donation"));
            } catch (Exception e1) {
            }
        }));
        Notifications.Bus.notify(notification);
    }

}
