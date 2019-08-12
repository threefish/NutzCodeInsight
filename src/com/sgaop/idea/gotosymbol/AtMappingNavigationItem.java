package com.sgaop.idea.gotosymbol;

import com.intellij.psi.PsiElement;
import com.intellij.util.xml.model.gotosymbol.GoToSymbolProvider;
import com.sgaop.idea.NutzCons;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;

/**
 * @author huchuc@vip.qq.com
 * @date: 2019/8/11
 */
public class AtMappingNavigationItem extends GoToSymbolProvider.BaseNavigationItem {

    private String text;

    public AtMappingNavigationItem(PsiElement annotation, String topReq, String requestPath, String methodType) {
        super(annotation, MessageFormat.format("{0}{1} ({2})", topReq, requestPath, methodType.trim()), NutzCons.NUTZ);
        this.text = getPresentableText();
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
