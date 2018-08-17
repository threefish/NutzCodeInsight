package com.sgaop.idea.codeinsight;

import com.intellij.codeHighlighting.Pass;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.sgaop.idea.codeinsight.navigation.NutzGutterIconNavigationHandler;
import com.sgaop.idea.codeinsight.util.NutzLineUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/29  11:30
 */
public class JavaNutzLineMarkerProvider extends LineMarkerProviderDescriptor {

    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement bindingElement) {
        if (NutzLineUtil.isAtOk(bindingElement)) {
            JavaNutzTemplateVO vo = NutzLineUtil.getTemplateFilePathAndName(bindingElement);
            Icon icon = NutzLineUtil.getTemplateIcon(vo.getFileExtension().split(";")[0]);
            return new LineMarkerInfo<>(bindingElement, bindingElement.getTextRange(), icon,
                    Pass.LINE_MARKERS, new FunctionTooltip(), new NutzGutterIconNavigationHandler(),
                    GutterIconRenderer.Alignment.LEFT);
        }
        return null;
    }

    @Nullable
    @Override
    public String getName() {
        return "Nutz Ok navigate";
    }

    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> elements, @NotNull Collection<LineMarkerInfo> result) {
    }

}
