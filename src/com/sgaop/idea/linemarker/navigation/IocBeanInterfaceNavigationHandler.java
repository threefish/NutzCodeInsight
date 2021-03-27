package com.sgaop.idea.linemarker.navigation;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.psi.PsiElement;
import com.intellij.ui.awt.RelativePoint;

import java.awt.event.MouseEvent;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/4/2
 */
public class IocBeanInterfaceNavigationHandler implements GutterIconNavigationHandler {


    String name;

    List<PsiElement> implListElements;

    public IocBeanInterfaceNavigationHandler(String name, List<PsiElement> implListElements) {
        this.name = name;
        this.implListElements = implListElements;
    }

    @Override
    public void navigate(MouseEvent mouseEvent, PsiElement psiElement) {
        if (implListElements.size() == 1) {
            NavigationUtil.activateFileWithPsiElement(implListElements.get(0), true);
        } else {
            NavigationUtil.getPsiElementPopup(implListElements.toArray(new PsiElement[0]), MessageFormat.format("请选择 {0} 的实现类", name)).show(new RelativePoint(mouseEvent));
        }
    }

}
