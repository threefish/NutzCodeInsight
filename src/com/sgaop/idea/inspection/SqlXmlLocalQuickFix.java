package com.sgaop.idea.inspection;

import com.intellij.codeInspection.IntentionAndQuickFixAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/3
 */
public class SqlXmlLocalQuickFix extends IntentionAndQuickFixAction {
    @NotNull
    @Override
    public String getName() {
        return null;
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return null;
    }

    @Override
    public void applyFix(@NotNull Project project, PsiFile psiFile, @Nullable Editor editor) {

    }
}
