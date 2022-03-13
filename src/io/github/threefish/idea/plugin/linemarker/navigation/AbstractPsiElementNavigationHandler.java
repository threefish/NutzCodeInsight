package io.github.threefish.idea.plugin.linemarker.navigation;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiUtil;
import com.intellij.ui.awt.RelativePoint;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2020/5/30
 */
public abstract class AbstractPsiElementNavigationHandler implements GutterIconNavigationHandler {

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
    public abstract List<PsiElement> findReferences(PsiElement psiElement);

    @Override
    public void navigate(MouseEvent mouseEvent, PsiElement psiElement) {
        if (canNavigate(psiElement)) {
            final Project project = psiElement.getProject();
            final List<PsiElement> psiElements = findReferences(psiElement);
            if (psiElements.size() == 1) {
                FileEditorManager.getInstance(project).openFile(PsiUtil.getVirtualFile(psiElements.get(0)), true);
            } else if (psiElements.size() > 1) {
                NavigationUtil.getPsiElementPopup(psiElements.toArray(new PsiElement[0]), title).show(new RelativePoint(mouseEvent));
            } else {
                if (Objects.isNull(psiElements) || psiElements.size() <= 0) {
                    Messages.showErrorDialog("没有找到这个调用的方法，请检查！", "错误提示");
                }
            }
        }
    }
}
