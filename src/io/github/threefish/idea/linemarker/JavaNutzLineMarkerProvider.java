package io.github.threefish.idea.linemarker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import io.github.threefish.idea.linemarker.navigation.NutzNavigationHandler;
import io.github.threefish.idea.linemarker.vo.JavaNutzTemplateVO;
import com.sgaop.util.NutzLineUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/29  11:30
 */
public class JavaNutzLineMarkerProvider extends LineMarkerProviderDescriptor {

    private static final Logger LOG = Logger.getInstance(JavaNutzLineMarkerProvider.class);

    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement bindingElement) {
        try {
            if (NutzLineUtil.isAtOk(bindingElement)) {
                JavaNutzTemplateVO vo = NutzLineUtil.getTemplateFilePathAndName(bindingElement);
                Icon icon = NutzLineUtil.getTemplateIcon(vo.getFileExtension().split(";")[0]);
                return new LineMarkerInfo<PsiElement>(bindingElement, bindingElement.getTextRange(), icon,
                        new FunctionTooltip(), new NutzNavigationHandler(), GutterIconRenderer.Alignment.LEFT,
                        this::getName);
            }
        } catch (Exception e) {
            LOG.warn(e);
        }
        return null;
    }

    @Nullable
    @Override
    public String getName() {
        return "Nutz Ok navigate";
    }


}
