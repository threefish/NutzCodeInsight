package com.sgaop.idea.linemarker.navigation;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.sgaop.idea.linemarker.navigation.runnable.DocumentReplace;

import java.awt.event.MouseEvent;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/5/25
 */
public class OkJsonUpdateNavigationHandler implements GutterIconNavigationHandler {

    private String value;

    public OkJsonUpdateNavigationHandler(String value) {
        this.value = value;
    }

    @Override
    public void navigate(MouseEvent mouseEvent, PsiElement psiElement) {
        System.out.println(value);
        Application applicationManager = ApplicationManager.getApplication();
        CommandProcessor processor = CommandProcessor.getInstance();
        Project project = psiElement.getProject();
        PsiFile psiFile = psiElement.getContainingFile();
        Document document = PsiDocumentManager.getInstance(project).getDocument(psiFile);
        int startOffset = psiElement.getTextRange().getStartOffset();
        int endOffset = psiElement.getTextRange().getEndOffset();
        applicationManager.runWriteAction(() -> processor.executeCommand(project, new DocumentReplace(document, startOffset, endOffset, "@Ok(\"json:{nullAsEmtry:true}\")"), "", document));
    }
}
