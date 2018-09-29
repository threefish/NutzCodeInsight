package com.sgaop.idea.codeinsight.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.sgaop.idea.codeinsight.util.NutzInjectConfUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/5/31
 */
public class NutzInjectConfFoldingBuilder extends FoldingBuilderEx {

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean b) {
        Project project = root.getProject();
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        Collection<VirtualFile> propertiesFiles = FilenameIndex.getAllFilesByExt(project, "properties", GlobalSearchScope.projectScope(project));
        Collection<PsiLiteralExpression> literalExpressions = PsiTreeUtil.findChildrenOfType(root, PsiLiteralExpression.class);
        for (final PsiLiteralExpression literalExpression : literalExpressions) {
            if (!NutzInjectConfUtil.isInjectConf(literalExpression)) {
                continue;
            }
            String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
            String key = NutzInjectConfUtil.getKey(value);
            if (key != null) {
                final List<String> properties = NutzInjectConfUtil.findProperties(project, propertiesFiles, key);
                final TextRange textRange = new TextRange(literalExpression.getTextRange().getStartOffset() + 1, literalExpression.getTextRange().getEndOffset() - 1);
                String data;
                if (properties.size() == 1) {
                    data = properties.get(0);
                } else if (properties.size() > 1) {
                    data = "NutzCodeInsight:当前键值【" + key + "】在多个配置文件中存在！";
                } else {
                    data = "NutzCodeInsight:当前键值【" + key + "】在多个配置文件中未发现，使用时可能为Null，请注意检查！";
                }
                descriptors.add(new NutzLocalizationFoldingDescriptor(literalExpression.getNode(), textRange, data));
            }
        }
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode astNode) {
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode astNode) {
        return true;
    }


}
