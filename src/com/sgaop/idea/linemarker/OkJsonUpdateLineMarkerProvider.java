package com.sgaop.idea.linemarker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.source.PsiMethodImpl;
import com.sgaop.idea.NutzCons;
import com.sgaop.idea.linemarker.navigation.OkJsonUpdateNavigationHandler;
import com.sgaop.util.IconsUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/5/24
 */
public class OkJsonUpdateLineMarkerProvider implements LineMarkerProvider {

    static final String JSON_PREFIX = "json";

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
        PsiAnnotation psiAnnotation = this.getPsiAnnotation(psiElement);
        if (psiAnnotation != null) {
            PsiLiteralExpression literalExpression = (PsiLiteralExpression) psiAnnotation.findAttributeValue("value");
            String value = String.valueOf(literalExpression.getValue());
            if (value.startsWith(JSON_PREFIX)) {
                return new LineMarkerInfo<>(psiAnnotation, psiAnnotation.getTextRange(), IconsUtil.NUTZ,
                        new FunctionTooltip("快速配置"),
                        new OkJsonUpdateNavigationHandler(value),
                        GutterIconRenderer.Alignment.LEFT);
            }
        }
        return null;
    }


    private PsiAnnotation getPsiAnnotation(@NotNull PsiElement psiElement) {
        if (psiElement instanceof PsiMethod) {
            PsiMethodImpl field = ((PsiMethodImpl) psiElement);
            PsiAnnotation psiAnnotation = field.getAnnotation(NutzCons.OK);
            if (psiAnnotation != null) {
                return psiAnnotation;
            }
        }
        return null;
    }


    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> list, @NotNull Collection<LineMarkerInfo> collection) {

    }
}
