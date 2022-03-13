package io.github.threefish.idea.linemarker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.source.PsiMethodImpl;
import io.github.threefish.idea.NutzCons;
import io.github.threefish.idea.linemarker.navigation.OkJsonUpdateNavigationHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/5/24
 */
public class OkJsonUpdateLineMarkerProvider implements LineMarkerProvider {

    static final String JSON_PREFIX = "json";
    private static final Logger LOG = Logger.getInstance(OkJsonUpdateLineMarkerProvider.class);

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
        try {
            PsiAnnotation psiAnnotation = this.getPsiAnnotation(psiElement);
            if (psiAnnotation != null) {
                PsiLiteralExpression literalExpression = (PsiLiteralExpression) psiAnnotation.findAttributeValue("value");
                String value = String.valueOf(literalExpression.getValue());
                if (value.startsWith(JSON_PREFIX)) {
                    return new LineMarkerInfo<PsiElement>(psiAnnotation, psiAnnotation.getTextRange(), NutzCons.NUTZ,
                            new FunctionTooltip("快速配置"),
                            new OkJsonUpdateNavigationHandler(value),
                            GutterIconRenderer.Alignment.LEFT, () -> "json");
                }
            }
        } catch (Exception e) {
            LOG.warn(e);
        }
        return null;
    }


    private PsiAnnotation getPsiAnnotation(@NotNull PsiElement psiElement) {
        if (psiElement instanceof PsiMethod) {
            PsiMethodImpl field = ((PsiMethodImpl) psiElement);
            PsiAnnotation psiAnnotation = field.getAnnotation(NutzCons.OK);
            return psiAnnotation;
        }
        return null;
    }


}
