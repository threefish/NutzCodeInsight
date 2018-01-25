package com.sgaop.idea.codeinsight.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlToken;
import com.sgaop.idea.codeinsight.util.NutzLocalUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2018/1/24  14:38
 * 描述此类：
 */
public class BeetlLocalizationFoldingBuilder extends FoldingBuilderEx {

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        Project project = root.getProject();
        String localizationPackage = NutzLocalUtil.getLocalizationPackage(project);
        if (null == localizationPackage) {
            return new FoldingDescriptor[0];
        }
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        Collection<VirtualFile> propertiesFiles = FilenameIndex.getAllFilesByExt(project, "properties", GlobalSearchScope.projectScope(project));
        Collection<XmlToken> xmlTokens = PsiTreeUtil.findChildrenOfType(root, XmlToken.class);
        for (final XmlToken xmlToken : xmlTokens) {
            Matcher m = NutzLocalUtil.PATTERN.matcher(xmlToken.getText());
            while (m.find()) {
                String value = m.group(1);
                String startQm = value.substring(0, 1);
                String key = value.substring(1, value.length());
                key = key.substring(0, key.indexOf(startQm));
                final List<String> properties = NutzLocalUtil.findProperties(project, propertiesFiles, localizationPackage, key);
                if (properties.size() == 1) {
                    int start = xmlToken.getTextRange().getStartOffset() + m.start() + 8;
                    int end = start + key.length();
                    descriptors.add(new NutzLocalizationFoldingDescriptor(xmlToken.getNode(), new TextRange(start, end), properties.get(0)));
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
