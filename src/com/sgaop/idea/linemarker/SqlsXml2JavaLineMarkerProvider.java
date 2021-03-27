package com.sgaop.idea.linemarker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.sgaop.idea.linemarker.navigation.Sqls2XmlNavigationHandler;
import com.sgaop.util.SqlsXmlUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2020/5/30
 */
public class SqlsXml2JavaLineMarkerProvider implements LineMarkerProvider {

    private static final Logger LOG = Logger.getInstance(SqlsXml2JavaLineMarkerProvider.class);

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
        try {
            if (SqlsXmlUtil.isSqsXmlFile(psiElement)) {
                Icon icon = AllIcons.FileTypes.Java;
                return new LineMarkerInfo<PsiElement>(psiElement, psiElement.getTextRange(), icon,
                        new FunctionTooltip(), new Sqls2XmlNavigationHandler(),
                        GutterIconRenderer.Alignment.LEFT, () -> "sql2xml");
            }
        } catch (Exception e) {
            LOG.warn(e);
        }
        return null;
    }
}
