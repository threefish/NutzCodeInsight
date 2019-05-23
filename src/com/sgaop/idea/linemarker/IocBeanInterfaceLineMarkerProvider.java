package com.sgaop.idea.linemarker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiTypeElement;
import com.intellij.psi.impl.source.PsiFieldImpl;
import com.intellij.psi.impl.source.PsiTypeElementImpl;
import com.sgaop.idea.linemarker.navigation.IocBeanInterfaceNavigationHandler;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

/**
 * @author huchuc@vip.qq.com
 * @date: 2019/5/23
 */
public class IocBeanInterfaceLineMarkerProvider extends LineMarkerProviderDescriptor {

    static final String QUALI_FIED_NAME = "org.nutz.ioc.loader.annotation.Inject";
    Icon icon = AllIcons.Javaee.InterceptorClass;

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
        PsiAnnotation psiAnnotation = this.getPsiAnnotation(psiElement);
        if (psiAnnotation != null) {
            PsiTypeElementImpl psiTypeElement = this.getPsiTypeElement(psiElement);
            return new LineMarkerInfo<>(psiTypeElement, psiTypeElement.getTextRange(), icon,
                    new FunctionTooltip("快速跳转至实现类"), new IocBeanInterfaceNavigationHandler(),
                    GutterIconRenderer.Alignment.LEFT);
        }
        return null;
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
            PsiAnnotation psiAnnotation = field.getAnnotation(QUALI_FIED_NAME);
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
