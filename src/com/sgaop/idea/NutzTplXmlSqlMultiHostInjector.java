package com.sgaop.idea;

import com.intellij.lang.Language;
import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import com.sgaop.util.DomUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/1
 */
public class NutzTplXmlSqlMultiHostInjector implements MultiHostInjector {

    static final Language SQL_LANGUAGE = Language.findLanguageByID("SQL");

    @Override
    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement psiElement) {
        if (DomUtils.isNutzSqlFile(psiElement.getContainingFile())) {
            if (psiElement instanceof XmlTag) {
                XmlTag tag = (XmlTag) psiElement;
                if ("sql".equals(tag.getName())) {
                    System.out.println(tag.getValue().getTrimmedText());
                    PsiElement element = getXmlText(psiElement.getChildren());
                    if (element != null) {
                        registrar.startInjecting(SQL_LANGUAGE);
                        registrar.addPlace(null, null, (PsiLanguageInjectionHost) element, ElementManipulators.getValueTextRange(element));
                        registrar.doneInjecting();
                    }
                }
            }
        }
    }

    private PsiElement getXmlText(PsiElement[] psiElements) {
        for (PsiElement psiElement : psiElements) {
            if (psiElement instanceof XmlText) {
                System.out.println(psiElement.getText());
                return psiElement;
            }
        }
        return null;
    }

    @NotNull
    @Override
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Collections.singletonList(XmlTag.class);
    }
}
