package com.sgaop.idea.codeinsight;

import com.intellij.codeHighlighting.Pass;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.Function;
import com.sgaop.idea.codeinsight.navigation.HtmlTemplateGutterIconNavigationHandler;
import com.sgaop.idea.codeinsight.util.BeetlHtmlLineUtil;
import com.sgaop.idea.codeinsight.util.HtmlTemplateLineUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * @date 2018/1/3  11:28
 */
public class HtmlTemplateLineMarkerProvider extends LineMarkerProviderDescriptor {

    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement bindingElement) {
        if (HtmlTemplateLineUtil.isRes(bindingElement)) {
            Icon icon = HtmlTemplateLineUtil.getTemplateIcon(bindingElement);
            return new LineMarkerInfo<>(bindingElement, bindingElement.getTextRange(), icon,
                    Pass.LINE_MARKERS, new FunctionTooltip(), new HtmlTemplateGutterIconNavigationHandler(),
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
