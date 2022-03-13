package io.github.threefish.idea.linemarker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import io.github.threefish.idea.linemarker.navigation.HtmlTemplateNavigationHandler;
import com.sgaop.util.HtmlTemplateLineUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2018/1/3  11:28
 */
public class HtmlTemplateLineMarkerProvider extends LineMarkerProviderDescriptor {

    private static final Logger LOG = Logger.getInstance(HtmlTemplateLineMarkerProvider.class);

    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement bindingElement) {
        try {
            if (HtmlTemplateLineUtil.isRes(bindingElement)) {
                return new LineMarkerInfo<>(bindingElement, bindingElement.getTextRange(), HtmlTemplateLineUtil.getTemplateIcon(bindingElement),
                        new FunctionTooltip(), new HtmlTemplateNavigationHandler(),
                        GutterIconRenderer.Alignment.LEFT,this::getName);
            }
        } catch (Exception e) {
            LOG.warn(e);
        }
        return null;
    }

    @Nullable
    @Override
    public String getName() {
        return "Template File navigate";
    }


}
