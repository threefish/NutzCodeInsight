package com.sgaop.idea.linemarker.navigation;

import com.google.gson.Gson;
import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.sgaop.idea.linemarker.navigation.runnable.DocumentReplace;
import com.sgaop.idea.linemarker.navigation.ui.JsonFormat;
import com.sgaop.idea.linemarker.navigation.ui.OkJsonFastEditor;

import java.awt.event.MouseEvent;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/5/25
 */
public class OkJsonUpdateNavigationHandler implements GutterIconNavigationHandler {

    static final String JSON_PREFIX = "json:";
    static final Gson GSON = new Gson();
    private final String value;


    public OkJsonUpdateNavigationHandler(String value) {
        this.value = value;
    }

    @Override
    public void navigate(MouseEvent mouseEvent, PsiElement psiElement) {
        JsonFormat jsonFormat = new JsonFormat();
        if (value.startsWith(JSON_PREFIX)) {
            try {
                jsonFormat = GSON.fromJson(value.substring(JSON_PREFIX.length()), JsonFormat.class);
            } catch (Exception e) {
                Messages.showErrorDialog("json格式化信息书写错误！", "错误提示");
            }
        }
        Application applicationManager = ApplicationManager.getApplication();
        CommandProcessor processor = CommandProcessor.getInstance();
        Project project = psiElement.getProject();
        PsiFile psiFile = psiElement.getContainingFile();
        Document document = PsiDocumentManager.getInstance(project).getDocument(psiFile);
        int startOffset = psiElement.getTextRange().getStartOffset();
        int endOffset = psiElement.getTextRange().getEndOffset();
        OkJsonFastEditor dialog = new OkJsonFastEditor(jsonFormat, (json) ->
                applicationManager.runWriteAction(() ->
                        processor.executeCommand(project, new DocumentReplace(document, startOffset, endOffset, json), "", document)
                )
        );
        dialog.pack();
        dialog.setVisible(true);
    }
}
