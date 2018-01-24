package com.sgaop.idea.codeinsight;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.impl.source.tree.java.PsiAnnotationParamListImpl;
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
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2018/1/24  14:38
 * 描述此类：
 */
public class BeetlLocalizationFoldingBuilder extends FoldingBuilderEx {

    private String localizationPackage;
    /**
     * 是否初始化
     */
    private boolean isInit = false;


    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        Project project = root.getProject();
        if (!init(project)) {
            return new FoldingDescriptor[0];
        }
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        Collection<VirtualFile> propertiesFiles = FilenameIndex.getAllFilesByExt(project, "properties", GlobalSearchScope.projectScope(project));
        Collection<XmlToken> xmlTokens = PsiTreeUtil.findChildrenOfType(root, XmlToken.class);
        for (final XmlToken xmlToken : xmlTokens) {
            System.out.println("HTML:" + xmlToken.getText());
            String str = xmlToken.getText();
            String regEx = "\\$\\{i18n\\((.*?)\\)}";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            while (m.find()) {
                String value = m.group(1);
                String startQm = value.substring(0, 1);
                String key = value.substring(1, value.length());
                key = key.substring(0, key.indexOf(startQm));
                final List<String> properties = NutzLocalUtil.findProperties(project, propertiesFiles, localizationPackage, key);
                if (properties.size() == 1) {
                    int start = xmlToken.getTextRange().getStartOffset() + m.start()+8;
                    int end = start + key.length();
                    descriptors.add(new FoldingDescriptor(xmlToken.getNode(),
                            new TextRange(start, end)) {
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

    private boolean init(Project project) {
        if (!isInit) {
            Collection<PsiAnnotation> psiAnnotations = JavaAnnotationIndex.getInstance().get("Localization", project, GlobalSearchScope.projectScope(project));
            for (PsiAnnotation annotation : psiAnnotations) {
                if (!NutzCons.LOCALIZATION.equals(annotation.getQualifiedName())) {
                    continue;
                }
                PsiElement[] list = annotation.getChildren();
                for (PsiElement element : list) {
                    if (element instanceof PsiAnnotationParamListImpl) {
                        PsiNameValuePair[] valuePairs = ((PsiAnnotationParamListImpl) element).getAttributes();
                        for (PsiNameValuePair nv : valuePairs) {
                            if ("value".equals(nv.getName())) {
                                localizationPackage = nv.getLiteralValue();
                            }
                        }
                        for (PsiNameValuePair nv : valuePairs) {
                            if ("defaultLocalizationKey".equals(nv.getName())) {
                                String lang = nv.getLiteralValue();
                                lang = lang.replaceAll("-", "_");
                                localizationPackage = localizationPackage + lang;
                            }
                        }
                    }
                }
            }
        }
        if (localizationPackage == null) {
            return false;
        } else {
            isInit = true;
            return true;
        }
    }
}
