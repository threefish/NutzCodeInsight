package com.sgaop.idea.codeinsight;

import com.intellij.codeHighlighting.Pass;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.sgaop.idea.codeinsight.navigation.NutzNavigationHandler;
import com.sgaop.idea.codeinsight.navigation.SqlsXmlNavigationHandler;
import com.sgaop.idea.codeinsight.util.NutzLineUtil;
import com.sgaop.idea.codeinsight.util.SqlsXmlLineUtil;
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
        if(SqlsXmlLineUtil.isSqlsXml(psiElement)){
            Icon icon = AllIcons.Javaee.Application_xml;
            return new LineMarkerInfo<>(psiElement, psiElement.getTextRange(), icon,
                    Pass.LINE_MARKERS, new FunctionTooltip(), new SqlsXmlNavigationHandler(),
                    GutterIconRenderer.Alignment.LEFT);
        }
        return null;
    }

    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> list, @NotNull Collection<LineMarkerInfo> collection) {

    }
}
