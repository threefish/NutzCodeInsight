package io.github.threefish.idea.plugin.linemarker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import io.github.threefish.idea.plugin.linemarker.navigation.SqlsXmlNavigationHandler;
import io.github.threefish.idea.plugin.util.SqlsXmlUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/4/2
 */
public class JavaSqlsXmlLineMarkerProvider extends LineMarkerProviderDescriptor {

    private static final Logger LOG = Logger.getInstance(JavaSqlsXmlLineMarkerProvider.class);

    @Nullable("null means disabled")
    @Override
    public String getName() {
        return "SqlsXml navigate";
    }

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
        try {
            if (SqlsXmlUtil.isSqlsXml(psiElement)) {
                Icon icon = AllIcons.FileTypes.Xml;
                return new LineMarkerInfo<>(psiElement, psiElement.getTextRange(), icon,
                        new FunctionTooltip(), new SqlsXmlNavigationHandler(),
                        GutterIconRenderer.Alignment.LEFT, this::getName);
            }
        } catch (Exception e) {
            LOG.warn(e);
        }
        return null;
    }

}
