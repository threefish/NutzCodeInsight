package com.sgaop.idea.codeinsight.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/9/30
 */
public class PsiJavaInjectReference implements PsiReference {

    PsiElement javaElement;
    PsiElement propsElement;

    public PsiJavaInjectReference(PsiElement javaElement, PsiElement propsElement) {
        this.javaElement = javaElement;
        this.propsElement = propsElement;
    }

    @Override
    public PsiElement getElement() {
        return this.javaElement;
    }

    @Override
    public TextRange getRangeInElement() {
        String text = this.javaElement.getText();
        boolean match = text.startsWith("\"") && text.endsWith("\"");
        final int len = 2;
        if (match && text.length() > len) {
            return new TextRange(1, this.javaElement.getTextLength() - 1);
        }
        return new TextRange(0, this.javaElement.getTextLength());
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return propsElement;
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        return propsElement.getText();
    }

    @Override
    public PsiElement handleElementRename(String s) throws IncorrectOperationException {
        return javaElement;
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        return null;
    }

    @Override
    public boolean isReferenceTo(PsiElement psiElement) {
        return psiElement == resolve();
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    @Override
    public boolean isSoft() {
        return false;
    }
}
