package com.sgaop.idea.codeinsight;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.sgaop.idea.codeinsight.util.NutzLocalUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2018/1/22  17:55
 * 描述此类：
 */
public class NutzLocalizationFoldingBuilder extends FoldingBuilderEx {
    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        FoldingGroup group = FoldingGroup.newGroup("NutzLocal");
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        Project project = root.getProject();
        Collection<VirtualFile> propertiesFiles = FilenameIndex.getAllFilesByExt(project, "properties", GlobalSearchScope.allScope(project));
        Collection<PsiLiteralExpression> literalExpressions = PsiTreeUtil.findChildrenOfType(root, PsiLiteralExpression.class);
        for (final PsiLiteralExpression literalExpression : literalExpressions) {
            String key = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
            if (key != null) {
                System.out.println(key);
                final List<String> properties = NutzLocalUtil.findProperties(project,propertiesFiles,key);
                if (properties.size() == 1) {
                    descriptors.add(new FoldingDescriptor(literalExpression.getNode(),
                            new TextRange(literalExpression.getTextRange().getStartOffset() + 1,
                                    literalExpression.getTextRange().getEndOffset() - 1),
                            group) {
                        @Nullable
                        @Override
                        public String getPlaceholderText() {
                            String valueOf = properties.get(0);
                            return valueOf == null ? "" : valueOf.replaceAll("\n", "\\n").replaceAll("\"", "\\\\\"");
                        }
                    });
                }
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
