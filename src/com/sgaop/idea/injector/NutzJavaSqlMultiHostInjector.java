package com.sgaop.idea.injector;

import com.intellij.lang.Language;
import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.psi.*;
import com.sgaop.idea.codeinsight.NutzCons;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/1
 */
public class NutzJavaSqlMultiHostInjector implements MultiHostInjector {

    static final Language SQL_LANGUAGE = Language.findLanguageByID("SQL");

    @Override
    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement psiElement) {
        if (psiElement.getParent() != null && psiElement.getParent().getParent() != null) {
            PsiElement parent = psiElement.getParent().getParent().getParent();
            if (parent != null && parent instanceof PsiAnnotation) {
                String qualifiedName = ((PsiAnnotation) parent).getQualifiedName();
                if (NutzCons.SQLS.contains(qualifiedName)) {
                    registrar.startInjecting(SQL_LANGUAGE);
                    registrar.addPlace(null, null, (PsiLanguageInjectionHost) psiElement, ElementManipulators.getValueTextRange(psiElement));
                    registrar.doneInjecting();
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
