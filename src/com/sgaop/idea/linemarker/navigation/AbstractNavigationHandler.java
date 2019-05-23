package com.sgaop.idea.linemarker.navigation;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.ui.awt.RelativePoint;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/9/3
 */
public abstract class AbstractNavigationHandler implements GutterIconNavigationHandler {

    private final String title = "请选择";

    /**
     * 是否匹配跳转条件
     *
     * @param psiElement
     * @return
     */
    public abstract boolean canNavigate(PsiElement psiElement);

    /**
     * 检索符合的资源文件
     *
     * @param psiElement
     * @return
     */
    public abstract List<VirtualFile> findTemplteFileList(PsiElement psiElement);


    @Override
    public final void navigate(MouseEvent mouseEvent, PsiElement psiElement) {
        if (canNavigate(psiElement)) {
            final Project project = psiElement.getProject();
            final List<VirtualFile> fileList = findTemplteFileList(psiElement);
            if (fileList.size() == 1) {
                FileEditorManager.getInstance(project).openFile(fileList.get(0), true);
            } else if (fileList.size() > 1) {
                final List<VirtualFile> infos = new ArrayList<>(fileList);
                List<PsiElement> elements = new ArrayList<>();
                PsiManager psiManager = PsiManager.getInstance(psiElement.getProject());
                infos.forEach(virtualFile -> elements.add(psiManager.findFile(virtualFile).getNavigationElement()));
                NavigationUtil.getPsiElementPopup(elements.toArray(new PsiElement[0]), title).show(new RelativePoint(mouseEvent));
            } else {
                if (fileList == null || fileList.size() <= 0) {
                    JOptionPane.showMessageDialog(null, "没有找到这个资源文件，请检查！", "错误提示", JOptionPane.ERROR_MESSAGE, null);
                }
            }
        }

    }
}
