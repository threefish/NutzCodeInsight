package com.sgaop.idea.linemarker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.sgaop.idea.linemarker.navigation.SqlsXmlNavigationHandler;
import com.sgaop.util.SqlsXmlUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/2
 */
public class JavaSqlsXmlLineMarkerProvider extends LineMarkerProviderDescriptor {

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
                        GutterIconRenderer.Alignment.LEFT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
