package com.sgaop.idea.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.sgaop.idea.codeinsight.util.SqlsXmlLineUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/2
 */
public class JavaSqlXmlAnnotator implements Annotator {

    static final String errMsg = "模版文件未找到";

    public static void showTopic(Project project, String title, String content, NotificationType type) {
        project.getMessageBus().syncPublisher(Notifications.TOPIC).notify(
                new Notification(Notifications.SYSTEM_MESSAGES_GROUP_ID,
                        title,
                        content,
                        type));
    }

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (SqlsXmlLineUtil.isSqlsXml(psiElement)) {
            List<VirtualFile> virtualFileList = SqlsXmlLineUtil.findTemplteFileList(psiElement);
            if (virtualFileList.size() == 0) {
                annotationHolder.createErrorAnnotation(psiElement.getTextRange(), errMsg);
                showTopic(psiElement.getProject(), errMsg, errMsg, NotificationType.ERROR);
            }
        }
    }
}
