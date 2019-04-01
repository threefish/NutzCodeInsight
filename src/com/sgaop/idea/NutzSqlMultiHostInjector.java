package com.sgaop.idea;

import com.intellij.lang.Language;
import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/1
 */
public class NutzSqlMultiHostInjector implements MultiHostInjector {
    @Override
    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement psiElement) {
        if (psiElement.getParent() != null) {
            if (psiElement.getParent().getParent() != null) {
                PsiElement ann = psiElement.getParent().getParent().getParent();
                if (ann != null && ann instanceof PsiAnnotation) {
                    String qualName = ((PsiAnnotation) ann).getQualifiedName();
                    if ("org.nutz.dao.entity.annotation.SQL".equals(qualName)) {
                        registrar.startInjecting(Language.findLanguageByID("SQL"));
                        registrar.addPlace(null, null, (PsiLanguageInjectionHost) psiElement, ElementManipulators.getValueTextRange(psiElement));
                        registrar.doneInjecting();
                    }
                }
            }
        }
    }

    @NotNull
    @Override
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Collections.singletonList(PsiLiteralExpression.class);
    }
}
