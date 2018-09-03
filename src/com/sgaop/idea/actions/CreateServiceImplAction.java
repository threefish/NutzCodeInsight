package com.sgaop.idea.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.util.PsiUtilBase;
import com.sgaop.idea.ProjectPluginConfig;
import com.sgaop.templte.ui.CreateServiceImplFram;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author 黄川 306955302@qq.com
 * @date: 2018/5/30
 * 描述此类：
 */
public class CreateServiceImplAction extends DumbAwareAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = this.getEditor(e.getDataContext());
        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        if (psiFile instanceof PsiJavaFileImpl) {
            this.actionPerformedImpl(project, editor);
        }
    }

    @Override
    public void update(AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = this.getEditor(event.getDataContext());
        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        if (psiFile instanceof PsiJavaFileImpl) {
            setEnabledInModalContext(true);
            getTemplatePresentation().setEnabledAndVisible(true);
        } else {
            setEnabledInModalContext(false);
            getTemplatePresentation().setEnabledAndVisible(false);
        }
    }


    @Nullable
    protected Editor getEditor(@NotNull DataContext dataContext) {
        return CommonDataKeys.EDITOR.getData(dataContext);
    }

    public void actionPerformedImpl(@NotNull Project project, Editor editor) {
        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        if (psiFile instanceof PsiJavaFileImpl) {
            PsiJavaFileImpl psiJavaFile = (PsiJavaFileImpl) psiFile;
            String packageName = psiJavaFile.getPackageStatement().getPackageName();
            String filename = psiJavaFile.getName().replace(".java", "");
            Application applicationManager = ApplicationManager.getApplication();
            CommandProcessor processor = CommandProcessor.getInstance();
            //当前所在文档
            Document document = editor.getDocument();
            ProjectPluginConfig pluginEditorInfo = new ProjectPluginConfig(applicationManager, processor, document, project, editor, psiFile);
            CreateServiceImplFram dialog = new CreateServiceImplFram(pluginEditorInfo, packageName, filename);
            dialog.pack();
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "不是java类！", "错误提示", JOptionPane.ERROR_MESSAGE, null);
        }
    }
}
