package com.sgaop.idea.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import com.sgaop.util.DomUtils;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/1
 * SQL中进行代码提示
 */
public class SqlParamCompletionContributor extends CompletionContributor {
    @Override
    public void fillCompletionVariants(CompletionParameters parameters, final CompletionResultSet result) {
        if (parameters.getCompletionType() != CompletionType.BASIC) {
            return;
        }
        PsiElement position = parameters.getPosition();
        PsiFile topLevelFile = InjectedLanguageUtil.getTopLevelFile(position);
        if (DomUtils.isNutzSqlFile(topLevelFile)) {
            if (shouldAddElement(position.getContainingFile(), parameters.getOffset())) {
                System.out.println(topLevelFile);
                System.out.println(result);
                System.out.println(position);
            }
        }
    }

    private boolean shouldAddElement(PsiFile file, int offset) {
        String text = file.getText();
        for (int i = offset - 1; i > 0; i--) {
            char c = text.charAt(i);
            if (c == '{' && text.charAt(i - 1) == '#') return true;
        }
        return false;
    }
}
