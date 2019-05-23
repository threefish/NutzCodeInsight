package com.sgaop.idea.linemarker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.module.impl.scopes.ModuleWithDependenciesScope;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaSuperClassNameOccurenceIndex;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.PsiFieldImpl;
import com.intellij.psi.impl.source.PsiTypeElementImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTypesUtil;
import com.sgaop.idea.linemarker.navigation.IocBeanInterfaceNavigationHandler;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author huchuc@vip.qq.com
 * @date: 2019/5/23
 */
public class IocBeanInterfaceLineMarkerProvider extends LineMarkerProviderDescriptor {

    static final String INJECT_QUALI_FIED_NAME = "org.nutz.ioc.loader.annotation.Inject";

    static final String IOCBEAN_QUALI_FIED_NAME = "org.nutz.ioc.loader.annotation.IocBean";


    Icon icon = AllIcons.Javaee.InterceptorClass;

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
        PsiAnnotation psiAnnotation = this.getPsiAnnotation(psiElement);
        if (psiAnnotation != null) {
            PsiTypeElementImpl psiTypeElement = this.getPsiTypeElement(psiElement);
            String name = getPsiClassName(psiTypeElement);
            List<PsiElement> list = getImplListElements(name, psiElement);
            if (list.size() > 0) {
                return new LineMarkerInfo<>(psiTypeElement, psiTypeElement.getTextRange(), icon,
                        new FunctionTooltip(MessageFormat.format("快速跳转至 {0} 的 @IocBean 实现类", name)),
                        new IocBeanInterfaceNavigationHandler(name, list),
                        GutterIconRenderer.Alignment.LEFT);
            }
        }
        return null;
    }


    private String getPsiClassName(@NotNull PsiElement psiElement) {
        PsiClass psiClass = PsiTypesUtil.getPsiClass(((PsiTypeElementImpl) psiElement).getType());
        return psiClass.getName();
    }

    /**
     * 取得实现类
     *
     * @param psiElement
     * @return
     */
    private List<PsiElement> getImplListElements(String name, PsiElement psiElement) {
        GlobalSearchScope moduleScope = ((ModuleWithDependenciesScope) psiElement.getResolveScope()).getModule().getModuleScope();
        Project project = psiElement.getProject();
        Collection<PsiReferenceList> psiReferenceListCollection = JavaSuperClassNameOccurenceIndex.getInstance().get(name, project, moduleScope);
        List<PsiElement> elements = new ArrayList<>();
        for (PsiReferenceList psiReferenceList : psiReferenceListCollection) {
            PsiClassImpl psiClassImpl = (PsiClassImpl) psiReferenceList.getContext();
            if (psiClassImpl.getAnnotation(IOCBEAN_QUALI_FIED_NAME) != null) {
                elements.add(psiClassImpl);
            }
        }
        return elements;
    }


    private PsiTypeElementImpl getPsiTypeElement(PsiElement psiElement) {
        PsiElement[] psiElements = psiElement.getChildren();
        for (PsiElement element : psiElements) {
            if (element instanceof PsiTypeElement) {
                return (PsiTypeElementImpl) element;
            }
        }
        return null;
    }

    private PsiAnnotation getPsiAnnotation(@NotNull PsiElement psiElement) {
        if (psiElement instanceof PsiField) {
            PsiFieldImpl field = ((PsiFieldImpl) psiElement);
            PsiAnnotation psiAnnotation = field.getAnnotation(INJECT_QUALI_FIED_NAME);
            if (psiAnnotation != null) {
                return psiAnnotation;
            }
        }
        return null;
    }

    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> elements, @NotNull Collection<LineMarkerInfo> result) {

    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @Nullable("null means disabled")
    @Override
    public String getName() {
        return "navigate to Implementation class";
    }
}
