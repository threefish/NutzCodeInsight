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

    String key;
    PsiElement javaElement;
    PsiElement propsElement;
    TextRange textRange;

    public PsiJavaInjectReference(String key, PsiElement javaElement, PsiElement propsElement, TextRange textRange) {
        this.key = key;
        this.javaElement = javaElement;
        this.propsElement = propsElement;
        this.textRange = textRange;
    }

    @Override
    public PsiElement getElement() {
        return this.javaElement;
    }

    @Override
    public TextRange getRangeInElement() {
        String text = this.javaElement.getText();
        if ((text.startsWith("\"")) && (text.endsWith("\"")) && (text.length() > 2)) {
//            return new TextRange(17, this.javaElement.getTextLength() - 3);
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
