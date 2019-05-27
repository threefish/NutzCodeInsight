package com.sgaop.idea.linemarker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.sgaop.idea.linemarker.navigation.HtmlTemplateNavigationHandler;
import com.sgaop.util.HtmlTemplateLineUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2018/1/3  11:28
 */
public class HtmlTemplateLineMarkerProvider extends LineMarkerProviderDescriptor {

    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement bindingElement) {
        if (HtmlTemplateLineUtil.isRes(bindingElement)) {
            return new LineMarkerInfo<>(bindingElement, bindingElement.getTextRange(), HtmlTemplateLineUtil.getTemplateIcon(bindingElement),
                    new FunctionTooltip(), new HtmlTemplateNavigationHandler(),
                    GutterIconRenderer.Alignment.LEFT);
        }
        return null;
    }

    @Nullable
    @Override
    public String getName() {
        return "Template File navigate";
    }

    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> elements, @NotNull Collection<LineMarkerInfo> result) {
    }

}
