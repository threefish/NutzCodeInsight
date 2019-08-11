package com.sgaop.idea.actions;

import com.intellij.psi.PsiElement;
import com.intellij.util.xml.model.gotosymbol.GoToSymbolProvider;
import com.sgaop.idea.NutzCons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author huchuc@vip.qq.com
 * @date: 2019/8/11
 */
public class AtMappingNavigationItem extends GoToSymbolProvider.BaseNavigationItem {

    private String text;

    public AtMappingNavigationItem(@NotNull PsiElement psiElement, @NotNull String text) {
        super(psiElement, text, NutzCons.NUTZ);
        this.text = text;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof AtMappingNavigationItem) {
            return ((AtMappingNavigationItem) object).getText().equals(this.getText());
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public String getText() {
        return text;
    }
}
