package com.sgaop.idea.gotosymbol;

import com.intellij.psi.PsiElement;
import com.intellij.util.xml.model.gotosymbol.GoToSymbolProvider;
import com.sgaop.idea.enums.NutzApiMethodType;
import com.sgaop.util.AtMappingIconUtil;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;

/**
 * @author huchuc@vip.qq.com
 * @date: 2019/8/11
 */
public class AtMappingNavigationItem extends GoToSymbolProvider.BaseNavigationItem {

    private final String text;

    private final NutzApiMethodType apiType;

    public AtMappingNavigationItem(PsiElement annotation, String topReq, String requestPath, NutzApiMethodType methodType) {
        super(annotation, MessageFormat.format("{0}{1}", topReq, requestPath), AtMappingIconUtil.getIcon(methodType));
        this.text = getPresentableText();
        this.apiType = methodType;
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

    public NutzApiMethodType getApiType() {
        return apiType;
    }
}
