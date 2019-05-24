package com.sgaop.idea.linemarker.navigation;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
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
        VirtualFile virtualFile = psiElement.getContainingFile().getVirtualFile();
        //下来再想想办法
        Editor[] editors = EditorFactory.getInstance().getAllEditors();
        Editor editor = null;
        sw:
        for (Editor edit : editors) {
            if (((EditorImpl) edit).getVirtualFile() == virtualFile) {
                editor = edit;
                break sw;
            }
        }
        //当前所在文档
        Document document = editor.getDocument();
        int startOffset = psiElement.getTextRange().getStartOffset();
        int endOffset = psiElement.getTextRange().getEndOffset();
        applicationManager.runWriteAction(() -> processor.executeCommand(project, new DocumentReplace(document, startOffset, endOffset, "@Ok(\"json:{nullAsEmtry:true}\")"), "", document));
    }
}
