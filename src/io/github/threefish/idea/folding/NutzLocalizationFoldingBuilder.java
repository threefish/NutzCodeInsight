package io.github.threefish.idea.folding;

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
import com.sgaop.util.NutzLocalUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * <p>
 * 创建时间: 2018/1/22  17:55
 */
public class NutzLocalizationFoldingBuilder extends FoldingBuilderEx {


    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        Project project = root.getProject();
        String localizationPackage = NutzLocalUtil.getLocalizationPackage(project);
        if (null == localizationPackage) {
            return FoldingDescriptor.EMPTY;
        }
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        Collection<VirtualFile> propertiesFiles = FilenameIndex.getAllFilesByExt(project, "properties", GlobalSearchScope.projectScope(project));
        Collection<PsiLiteralExpression> literalExpressions = PsiTreeUtil.findChildrenOfType(root, PsiLiteralExpression.class);
        for (final PsiLiteralExpression literalExpression : literalExpressions) {
            if (!NutzLocalUtil.isLocal(literalExpression)) {
                continue;
            }
            String key = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
            if (key != null) {
                final List<String> properties = NutzLocalUtil.findProperties(project, propertiesFiles, localizationPackage, key);
                TextRange textRange = new TextRange(literalExpression.getTextRange().getStartOffset() + 1, literalExpression.getTextRange().getEndOffset() - 1);
                String value;
                if (properties.size() == 1) {
                    value = properties.get(0);
                } else if (properties.size() > 1) {
                    value = properties.get(0) + "[该键值存在多个配置文件中!]";
                } else {
                    value = "国际化信息中不存在[" + key + "],使用时可能产生异常,请检查！";
                }
                descriptors.add(new NutzLocalizationFoldingDescriptor(literalExpression.getNode(), textRange, value));
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
