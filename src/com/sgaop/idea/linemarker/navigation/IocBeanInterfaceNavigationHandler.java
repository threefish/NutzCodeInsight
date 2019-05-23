package com.sgaop.idea.linemarker.navigation;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.openapi.module.impl.scopes.ModuleWithDependenciesScope;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceList;
import com.intellij.psi.impl.java.stubs.index.JavaSuperClassNameOccurenceIndex;
import com.intellij.psi.impl.source.PsiTypeElementImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTypesUtil;
import com.intellij.ui.awt.RelativePoint;

import java.awt.event.MouseEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/2
 */
public class IocBeanInterfaceNavigationHandler implements GutterIconNavigationHandler {

    @Override
    public void navigate(MouseEvent mouseEvent, PsiElement psiElement) {
        GlobalSearchScope moduleScope = ((ModuleWithDependenciesScope) psiElement.getResolveScope()).getModule().getModuleScope();
        Project project = psiElement.getProject();
        PsiClass psiClass = PsiTypesUtil.getPsiClass(((PsiTypeElementImpl) psiElement).getType());
        String name = psiClass.getName();
        Collection<PsiReferenceList> psiReferenceListCollection = JavaSuperClassNameOccurenceIndex.getInstance().get(psiClass.getName(), project, moduleScope);
        List<PsiElement> elements = new ArrayList<>();
        for (PsiReferenceList psiReferenceList : psiReferenceListCollection) {
            elements.add(psiReferenceList.getContext());
        }
        NavigationUtil.getPsiElementPopup(elements.toArray(new PsiElement[0]), MessageFormat.format("请选择 {0} 的实现类", name)).show(new RelativePoint(mouseEvent));
    }
}
