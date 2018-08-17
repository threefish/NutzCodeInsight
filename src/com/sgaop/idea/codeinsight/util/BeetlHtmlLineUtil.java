package com.sgaop.idea.codeinsight.util;

import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlToken;
import com.sgaop.idea.codeinsight.folding.NutzLocalizationFoldingDescriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/3/26

 */
public class BeetlHtmlLineUtil {

    /**
     * 默认beetl后缀为 html
     */
    private static final String BEETL_SUFFIX = "html";
    /**
     * beetl模版中匹配layout
     */
    private static final Pattern PATTERN_LAYOUT = Pattern.compile("layout\\((\"|\')(.*?)\\." + BEETL_SUFFIX + "(\"|\')");
    /**
     * beetl模版中匹配include得正则表达式
     */
    private static final Pattern PATTERN_INCLUDE = Pattern.compile("include\\((\"|\')(.*?)\\." + BEETL_SUFFIX + "(\"|\')\\)");

    public static List<FoldingDescriptor> showNutzLocalization(Project project, PsiElement root) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        String localizationPackage = NutzLocalUtil.getLocalizationPackage(project);
        if (null == localizationPackage) {
            return new ArrayList<>();
        }
        Collection<VirtualFile> propertiesFiles = FilenameIndex.getAllFilesByExt(project, "properties", GlobalSearchScope.projectScope(project));
        Collection<XmlToken> xmlTokens = PsiTreeUtil.findChildrenOfType(root, XmlToken.class);
        Collection<LeafPsiElement> LeafPsiElements = PsiTreeUtil.findChildrenOfType(root, LeafPsiElement.class);
        for (final LeafPsiElement leafPsiElement : LeafPsiElements) {
            if ("Language: JavaScript".equals(leafPsiElement.getLanguage().toString())) {
                String text = leafPsiElement.getText();
                Matcher m = NutzLocalUtil.PATTERN.matcher(text);
                descriptors.addAll(getFoldingDescriptor(m, text, project, propertiesFiles, localizationPackage, leafPsiElement));
            }
        }
        for (final XmlToken xmlToken : xmlTokens) {
            String text = xmlToken.getText();
            Matcher m = NutzLocalUtil.PATTERN.matcher(text);
            descriptors.addAll(getFoldingDescriptor(m, text, project, propertiesFiles, localizationPackage, xmlToken));
        }
        return descriptors;
    }

    private static List<FoldingDescriptor> getFoldingDescriptor(Matcher matcher, String text, Project project, Collection<VirtualFile> propertiesFiles, String localizationPackage, PsiElement psiElement) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        while (matcher.find()) {
            String key = text.substring(matcher.start() + 8, matcher.end() - 3);
            final List<String> properties = NutzLocalUtil.findProperties(project, propertiesFiles, localizationPackage, key);
            if (properties.size() == 1) {
                int start = psiElement.getTextRange().getStartOffset() + matcher.start() + 8;
                int end = start + key.length();
                descriptors.add(new NutzLocalizationFoldingDescriptor(psiElement.getNode(), new TextRange(start, end), properties.get(0)));
            } else if (properties.size() > 1) {
                int start = psiElement.getTextRange().getStartOffset() + matcher.start() + 8;
                int end = start + key.length();
                descriptors.add(new NutzLocalizationFoldingDescriptor(psiElement.getNode(), new TextRange(start, end), "NutzCodeInsight:当前键值【" + key + "】在国际化信息中存在重复KEY请检查！"));
            }
        }
        return descriptors;
    }


    public static List<String> showBeetlLayout(XmlToken xmlToken) {
        List<String> descriptors = new ArrayList<>();
        String text = xmlToken.getText();
        Matcher m = PATTERN_LAYOUT.matcher(text);
        while (m.find()) {
            String value = text.substring(8, m.end() - 1);
            descriptors.add(value);
        }
        return descriptors;
    }

    public static List<String> showBeetlInclude(XmlToken xmlToken) {
        List<String> descriptors = new ArrayList<>();
        String text = xmlToken.getText();
        Matcher m = PATTERN_INCLUDE.matcher(text);
        while (m.find()) {
            String value = text.substring(11, m.end() - 2);
            descriptors.add(value);
        }
        return descriptors;
    }

}
